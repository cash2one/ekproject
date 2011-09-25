package cn.elam.util.db.comom;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.PooledDataSource;

/**
 * 数据库连接接口控制器
 * @author Ethan.Lam   2011-2-26
 *
 */
public class DBConnector {
  
	/**
	 * 返回一个可用的连接
	 * @param poolName   数据源名称
	 * @return
	 * @throws DaoException
	 * @throws SQLException
	 */
	public static Connection getConnection(String poolName)
			throws DaoException, SQLException {
		ComboPooledDataSource dbpool = PoolManager.getPoolManager().getDBPool(poolName);
		try {
			report(dbpool);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbpool.getConnection();
	}


	public static void report(ComboPooledDataSource pds) throws Exception{
		System.out.println("num_connections: "+ pds.getNumConnections());
		System.out.println("NumConnectionsDefaultUser: "+ pds.getNumConnectionsDefaultUser());
		System.out.println("NumFailedCheckinsDefaultUser: "+ pds.getNumFailedCheckinsDefaultUser());
		System.out.println("num_busy_connections: " + pds.getNumBusyConnectionsDefaultUser());
		System.out.println("num_idle_connections: " + pds.getNumIdleConnectionsDefaultUser());
	}
	
	
 
	
	
}
