package cn.qtone.xxt.csop.dao.comom;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.struts.util.GenericDataSource;

import cn.qtone.xxt.csop.util.CsopLog;

public class DBConnector {
  
	public static Connection getConnection(String poolName)
			throws DaoException, SQLException {
		GenericDataSource dbpool = PoolManager.getPoolManager().getDBPool(poolName);
		Connection _conn = dbpool.getConnection();
		while(_conn!=null&&_conn.isClosed()){
        	_conn = null;
        	_conn = dbpool.getConnection();
            CsopLog.debug("DBConnector Connection 为空。");
		}    
		return _conn;
	}

}
