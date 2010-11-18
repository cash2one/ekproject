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
			log.debug("���ݿ����ӳس�ʼ��ɣ�");

			log.debug("ע��������");
			DbStatisticsListener hnxxtStat = new DbStatisticsListener();
			ProxoolFacade.addStatisticsListener(Context.DB_ALIAS, hnxxtStat);
		} catch (ProxoolException e) {
			log.error("���ݿ����ӳس�ʼ���쳣��", e);
			
			MailUtils.sendMail("���ݿ��쳣", "���ݿ����ӳ��޷�������ʼ���������޷��������У�����");
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
			log.debug("��ȡһ�����ݿ�����.");
		} catch (SQLException e) {
			log.error("��ȡ���ݿ�����ʱ�����쳣", e);
			
			MailUtils.sendMail("���ݿ��쳣", "�޷���ȡ���ݿ����ӣ�DB���ܹ��ˡ��Ͻ�����������");
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
