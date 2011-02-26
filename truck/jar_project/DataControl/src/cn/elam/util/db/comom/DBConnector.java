package cn.elam.util.db.comom;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.struts.util.GenericDataSource;

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
		GenericDataSource dbpool = PoolManager.getPoolManager().getDBPool(poolName);
		return dbpool.getConnection();
	}

}
