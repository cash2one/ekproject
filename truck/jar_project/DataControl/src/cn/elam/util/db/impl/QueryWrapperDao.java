package cn.elam.util.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.elam.util.db.comom.DBConnector;
import cn.elam.util.db.comom.PageModel;
import cn.elam.util.db.inter.DataModel;
import cn.elam.util.db.inter.QueryAction;

/**
 * 
 * 简单的查询接口方法
 * 
 * @author Ethan.Lam 2011-2-24
 * 
 */
public class QueryWrapperDao {

	private PageModel page;
	private String DB_CONNECTION = "";

	public QueryWrapperDao(String POOL_NAME) {
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
	 * @param queryAction
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public DataModel getItem(QueryAction<? extends DataModel> queryAction,
			String sql) throws Exception {
		Connection db = null;
		BaseDao dao = null;
		ResultSet rs = null;
		try {
			db = DBConnector.getConnection(DB_CONNECTION);
			dao = new BaseDao(db);
			rs = dao.query(sql);
			if (rs != null && rs.next())
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
	 * 当前分页信息
	 * @return
	 */
	public PageModel getPageModel() {
		return page;
	}

	
	
	/**
	 * 
	 * @param strs
	 * @throws Exception
	 */

	public static void main(String... strs) throws Exception {
		QueryWrapperDao query = new QueryWrapperDao("xxt");

		List<Area> list = query.list(new QueryAction<Area>() {
			public Area wrapperItem(ResultSet rs) throws Exception {
				// TODO Auto-generated method stub
				Area a = new Area();
				a.setAbb(rs.getString("abb"));
				return a;
			}
		}, "select abb from area",3, 10);

		for(Area a : list){
			System.out.println(a.getAbb());
		}
		
	}

}

class Area implements DataModel {
    
	private String abb;

	public String getAbb() {
		return abb;
	}

	public void setAbb(String abb) {
		this.abb = abb;
	}
	
}