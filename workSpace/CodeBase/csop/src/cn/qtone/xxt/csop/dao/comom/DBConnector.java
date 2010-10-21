package cn.qtone.xxt.csop.dao.comom;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.struts.util.GenericDataSource;

public class DBConnector {
  
	public static Connection getConnection(String poolName)
			throws DaoException, SQLException {
		GenericDataSource dbpool = PoolManager.getPoolManager().getDBPool(poolName);
		return dbpool.getConnection();
	}

}
