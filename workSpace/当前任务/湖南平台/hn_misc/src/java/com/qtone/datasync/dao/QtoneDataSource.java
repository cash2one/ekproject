package com.qtone.datasync.dao;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;
import org.logicalcobwebs.proxool.configuration.PropertyConfigurator;

import cn.qtone.mm7.common.mail.MailUtils;

import com.qtone.datasync.util.Context;
import com.qtone.datasync.util.DbStatisticsListener;
import com.qtone.datasync.util.FileUtil;

public class QtoneDataSource implements DataSource {
	private static final Log log = LogFactory.getLog(QtoneDataSource.class);
	static {
		try {
			PropertyConfigurator.configure(FileUtil.getFilePath("proxool.properties"));
			log.debug("数据库连接池初始完成！");

			log.debug("注册侦听器");
			DbStatisticsListener hnxxtStat = new DbStatisticsListener();
			ProxoolFacade.addStatisticsListener(Context.DB_ALIAS, hnxxtStat);
		} catch (ProxoolException e) {
			log.error("数据库连接池初始化异常！", e);
			
			MailUtils.sendMail("数据库异常", "数据库连接池无法正常初始化，程序无法正常运行！！！");
			throw new RuntimeException();
		}
	}

	private PrintWriter logWriter = null;

	public Connection getConnection() throws SQLException {
		Connection con = null;
		try {
			synchronized (this) {
				con = DriverManager
						.getConnection("proxool." + Context.DB_ALIAS);
			}
			log.debug("获取一个数据库连接.");
		} catch (SQLException e) {
			log.error("获取数据库连接时产生异常", e);
			
			MailUtils.sendMail("数据库异常", "无法获取数据库连接，DB可能挂了。赶紧处理啊！！！");
		}

		return con;
	}

	public Connection getConnection(String username, String password)
			throws SQLException {
		return getConnection();
	}

	public PrintWriter getLogWriter() throws SQLException {
		return this.logWriter;
	}

	public int getLoginTimeout() throws SQLException {
		return 0;
	}

	public void setLogWriter(PrintWriter out) throws SQLException {
		this.logWriter = out;
	}

	public void setLoginTimeout(int seconds) throws SQLException {

	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}


	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}
}
