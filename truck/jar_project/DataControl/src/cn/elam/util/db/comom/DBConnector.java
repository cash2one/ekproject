package cn.elam.util.db.comom;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.ConnectionPoolDataSource;

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
		return dbpool.getConnection();
	}

	
	public static void report(String poolName) throws Exception{
		ComboPooledDataSource dbpool = PoolManager.getPoolManager().getDBPool(poolName);
		ConnectionPoolDataSource ds = dbpool.getConnectionPoolDataSource();
		PooledDataSource pds = (PooledDataSource) ds; 
		System.err.println("num_connections: " + pds.getNumConnectionsDefaultUser());   
		System.err.println("num_busy_connections: " + pds.getNumBusyConnectionsDefaultUser()); System.err.println("num_idle_connections: " + pds.getNumIdleConnectionsDefaultUser()); System.err.println(); 
	}
	
	
	/**
	 * 
	 * 方法：
	 * 
	 * @return
	 *  
	 *    Add By Ethan Lam  At 2011-9-24
	 */
	public static Map<String,Object> dbStatus(){
	      Map<String,Object> d = null;
	      return d;
		
	}
	
	
}
