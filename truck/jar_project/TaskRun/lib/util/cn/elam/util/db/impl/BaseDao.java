package cn.elam.util.db.impl;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import cn.elam.util.db.comom.DBType;
import cn.elam.util.db.comom.DaoException;
import cn.elam.util.db.comom.PageModel;
import cn.elam.util.db.inter.DataControl;

public class BaseDao implements DataControl {

	protected Connection conn;
	private PreparedStatement pstmt = null;
	private Statement stmt = null;
	private PageModel page;

	public BaseDao(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean execute(String sql) throws DaoException {
		try {
			stmt = conn.createStatement();
			return stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			clear();
		}
		return false;
	}

	@Override
	public boolean executeBatch(List<String> sqls) throws DaoException {
		try {
			stmt = conn.createStatement();
			for (String sql : sqls) {
				stmt.addBatch(sql);

			}
			stmt.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			clear();
		}
		return true;
	}

	@Override
	public ResultSet query(String sql) throws DaoException {
		try {
			stmt = conn.createStatement();
			return stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			clear();
		}
		return null;
	}

	@Override
	public boolean update(String sql) throws DaoException {
		try {
			stmt = conn.createStatement();
			return stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			clear();
		}
		return false;
	}

	@Override
	public void preparedExeDB(String sql) throws DaoException {
		try {
			pstmt = conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean excPreparedDB() throws DaoException {
		try {
			return pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			clear();
		}
		return false;
	}

	@Override
	public ResultSet queryPreparedDB() throws DaoException {
		try {
			return pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			clear();
		}
		return null;
	}

	/**
	 * ���ò���
	 * 
	 * @param parameterIndex
	 * @param x
	 * @throws SQLException
	 */
	public void setString(int parameterIndex, String x) throws SQLException {
		pstmt.setString(parameterIndex, x);
	}

	/**
	 * ����Object���͵Ĳ���
	 * 
	 * @param parameterIndex
	 * @param paramValue
	 * @throws SQLException
	 * 
	 * @author guyang 2005-12-02
	 */
	public void setObject(int parameterIndex, Object paramValue)
			throws SQLException {
		pstmt.setObject(parameterIndex, paramValue);
	}

	/**
	 * ����byte���͵Ĳ���
	 * 
	 * @param parameterIndex
	 * @param paramValue
	 * @throws SQLException
	 * 
	 * @author guyang
	 */
	public void setByte(int parameterIndex, byte paramValue)
			throws SQLException {
		pstmt.setByte(parameterIndex, paramValue);
	}

	/**
	 * ����short���͵Ĳ���
	 * 
	 * @param parameterIndex
	 * @param paramValue
	 * @throws SQLException
	 * 
	 * @author guyang
	 */
	public void setShort(int parameterIndex, short paramValue)
			throws SQLException {
		pstmt.setShort(parameterIndex, paramValue);
	}

	public void setInt(int parameterIndex, int x) throws SQLException {
		pstmt.setInt(parameterIndex, x);
	}

	public void setClob(int parameterIndex, Clob x) throws SQLException {
		pstmt.setClob(parameterIndex, x);
	}

	public void setFloat(int parameterIndex, float x) throws SQLException {
		pstmt.setFloat(parameterIndex, x);
	}

	public void setDate(int parameterIndex, Date x) throws SQLException {
		pstmt.setDate(parameterIndex, x);
	}

	public void setDouble(int parameterIndex, double x) throws SQLException {
		pstmt.setDouble(parameterIndex, x);
	}

	public void setLong(int parameterIndex, long x) throws SQLException {
		pstmt.setLong(parameterIndex, x);
	}

	void clear() {
		stmt = null;
		pstmt = null;
	}

	public void close() {
		try {
			if (pstmt != null)
				pstmt.close();
			pstmt = null;
			if (stmt != null)
				stmt.close();
			stmt = null;
			if (this.conn != null)
				this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conn = null;
	}

	@Override
	public PageModel getPage() throws DaoException {
		return page;
	}

	@Override
	public ResultSet queryByPage(String sql, int pageIndex, int pagePerNum)
			throws DaoException {
		ResultSet rs = null;
		try {
			int allRecords = 0;
			stmt = conn.createStatement();

			rs = stmt.executeQuery(" select count(*) from (" + sql + ") ");
			while (rs != null && rs.next()) {
				allRecords = rs.getInt(1);
			}
			if (allRecords <= 0)
				return null;

			rs = null;
			this.page = null;
			this.page = new PageModel(pageIndex, pagePerNum, allRecords);
			return stmt.executeQuery(page.getSqlString(sql, getDBType(conn)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	DBType getDBType(Connection conn) {
		DatabaseMetaData dbmd;
		try {
			dbmd = conn.getMetaData();
			conn = null;
			String dataBaseType = dbmd.getDatabaseProductName();
			dbmd = null;
			if (dataBaseType.toLowerCase().indexOf("oracle") >= 0)
				return DBType.ORACLE;
			else if (dataBaseType.toLowerCase().indexOf("mysql") >= 0)
				return DBType.MYSQL;
			else if (dataBaseType.toLowerCase().indexOf("sql server") >= 0)
				return DBType.SQLSERVER;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return DBType.UNKNOWN;
	}

}