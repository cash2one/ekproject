package cn.qtone.xxt.logonmock;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import cn.qtone.xxt.base.dao.comom.BaseDao;
import cn.qtone.xxt.base.dao.comom.DBConnector;
import cn.qtone.xxt.logonmock.util.KetangLoginEncoder;

public class LogonMain {

	public static void main(String... srg) {

		
		
	}
	
	
	/**
	 * 找出需要登录的用户，把数据插入到对应的中间表中
	 */
	public void perpare(){
		BaseDao dao = null;
		StringBuffer create_record_sql =new StringBuffer();
		try {
			dao = new BaseDao(Config.POOL_NAME);
			boolean operation = dao.execute(" delete from "+Config.TEMP_TABLE);
			System.out.println("1、已成功清空中间表的记录！");
			create_record_sql.append(" insert into ").append(Config.TEMP_TABLE);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dao.close();
		}
	}
  
	
	
	public int count(String sql){
		BaseDao dao = null;
		ResultSet rs = null;
		try {
			dao = new BaseDao(Config.POOL_NAME);
			rs = dao.query(" select count(*) from ( " + sql + " ) a");
			int records = 0;
			while (rs != null && rs.next()) {
				records = rs.getInt(1);
			}
			return records;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
			}
			dao.close();
		}
	}

}
