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
		getDbPoolStatus(poolName,dbpool);
		return dbpool.getConnection();
	}

    /**
     * 
     * 方法：获取当前数据池状态
     * 
     * @param pds
     * @throws Exception
     *  
     *    Add By Ethan Lam  At 2011-9-25
     */
	 static String getDbPoolStatus(String poolName,ComboPooledDataSource pds){
		StringBuffer msg = new StringBuffer();
		try {
			msg.append(" num_connections: "+ pds.getNumConnections());
			msg.append(" NumConnectionsDefaultUser: "+ pds.getNumConnectionsDefaultUser());
			msg.append(" NumFailedCheckinsDefaultUser: "+ pds.getNumFailedCheckinsDefaultUser());
			msg.append(" num_busy_connections: " + pds.getNumBusyConnectionsDefaultUser());
			msg.append(" num_idle_connections: " + pds.getNumIdleConnectionsDefaultUser());
			System.out.println("DBPool【"+poolName+"】 Current_Status: "+msg);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String m = msg.toString();
		return m;
	}
	
	
	/**
	 * 
	 * 方法：方法：获取当前数据池状态
	 * 
	 * @param poolName
	 * @return
	 *  
	 *    Add By Ethan Lam  At 2011-9-25
	 */
	public static String getDbPoolStatusByPoolName(String poolName){
			try {
				ComboPooledDataSource dbpool = PoolManager.getPoolManager().getDBPool(poolName);
				return getDbPoolStatus(poolName,dbpool);
			} catch (DaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "【DBConnector】:NO DBPool Report Status.....please Check PoolName is or not named by  "+poolName;
			}
			
    }
 
	
	
}
