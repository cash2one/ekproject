package cn.elam.util.db.inter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.elam.util.db.comom.DBConnector;
import cn.elam.util.db.comom.PageModel;
import cn.elam.util.db.impl.BaseDao;

public class TestQuery {

	private PageModel page;
	private String DB_CONNECTION = "";

	public TestQuery(String POOL_NAME) {
		this.DB_CONNECTION = POOL_NAME;
	}

	/**
	 * 分页查询
	 * 
	 * @param queryAction
	 * @param sql
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List list(QueryAction<? extends DataModel> queryAction, String sql,
			int currentPage, int pageSize) throws Exception {
		Connection db = null;
		BaseDao dao = null;
		List<DataModel> dataList = new ArrayList<DataModel>();
		DataModel model = null;
		ResultSet rs = null;
		try {
			db = DBConnector.getConnection(DB_CONNECTION);
			dao = new BaseDao(db);
			rs = dao.queryByPage(sql, currentPage, pageSize);
			while (rs != null && rs.next()) {
				model = queryAction.wrapperItem(rs);
				if (model != null)
					dataList.add(model);
			}
			return dataList;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			dao.close();
		}
		return dataList;
	}

	
	/**
	 * 
	 * @param queryAction
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public DataModel getItem(QueryAction<? extends DataModel> queryAction, String sql) throws Exception {
		Connection db = null;
		BaseDao dao = null;
		ResultSet rs = null;
		try {
			db = DBConnector.getConnection(DB_CONNECTION);
			dao = new BaseDao(db);
			rs = dao.query(sql);
			if(rs != null && rs.next())
			   return queryAction.wrapperItem(rs);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			rs.close();
			dao.close();
		}
		
	}

	/**
	 * 
	 * @param strs
	 * @throws Exception
	 */

	public static void main(String... strs) throws Exception {
		TestQuery query = new TestQuery("");

		List<TModel> list = query.list(new QueryAction<TModel>() {
			public TModel wrapperItem(ResultSet rs) {
				// TODO Auto-generated method stub
				return null;
			}
		}, "sql", 10, 100);

	}

}

class TModel implements DataModel {

}
