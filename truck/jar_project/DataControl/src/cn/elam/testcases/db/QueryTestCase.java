package cn.elam.testcases.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import cn.elam.util.db.comom.DBConnector;
import cn.elam.util.db.comom.DaoException;
import cn.elam.util.db.impl.BaseDao;


public class QueryTestCase {

	public static void main(String... srt) {
		try {
//			query();
//			queryByPage();
			showTable("xxt","select table_name from user_tables where table_name like '%CH%' ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void query() throws DaoException, SQLException {
		Connection conn = DBConnector.getConnection("xxt");
		BaseDao dao = new BaseDao(conn);
		ResultSet rs = dao.query(" select count(*) from CP_ADMIN ");
		while (rs.next())
			System.out.println(" "
					+ rs.getInt(1));
		dao.close();
	}

	
	public static void queryByPage() throws DaoException, SQLException {
		Connection conn = DBConnector.getConnection("xxt");
		BaseDao dao = new BaseDao(conn);
		String sql = " select * from CP_ADMIN ";
		ResultSet rs = dao.queryByPage(sql, 1, 40);
		while (rs.next())
			System.out.println(rs.getInt(2)+" : "+rs.getString(3));
		dao.close();
	}
	
	public static void showTable(String pool,String sql) throws DaoException, SQLException{
         //select * from user_tables where table_name like '%STUDENT%'		
		
		Connection conn = DBConnector.getConnection(pool);
		BaseDao dao = new BaseDao(conn);
//		String sql = " select * from user_tables where table_name like '%CHILD%' ";
		ResultSet rs = dao.queryByPage(sql, 1, 40);
		if(rs==null)
			return;
		ResultSetMetaData md = rs.getMetaData();
		int columns = md.getColumnCount();
		for(int index =1;index<=columns;index++){
		   System.out.print(md.getColumnName(index)+"\t");	
		}
		System.out.println();
		while (rs.next()){
			for(int index =1;index<=columns;index++){
			   System.out.print(rs.getString(index)+" \t ");
			}
			System.out.println();
		}
	    dao.close();
	}

}
