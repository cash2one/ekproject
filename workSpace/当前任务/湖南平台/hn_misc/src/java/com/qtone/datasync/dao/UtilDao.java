package com.qtone.datasync.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qtone.datasync.bean.AreaBean;

/**
 * ����DAO�࣬���ڴ����ݿ��л�ȡһЩʵ����Ϣ
 * 
 * @author ���ڷ� 2009-6-8
 */
public class UtilDao {
	private Log log = LogFactory.getLog(UtilDao.class);

	private QueryRunner run = null;
	private DataSource source = null;

	public UtilDao() {
		source = new QtoneDataSource();
		run = new QueryRunner(source);
	}

	/**
	 * ��ȡ���˵��Ե�����������е�����Ϣ
	 * 
	 * @return
	 */
	public List<AreaBean> getAreaList() {
		// TODO:���Թ����У�ֻ�Ե��Ե��������ݽ��д���
		String sql = "select * from area where abb <> 'ts' ";

		final List<AreaBean> areaList = new ArrayList<AreaBean>();
		try {
			run.query(sql, new ResultSetHandler() {

				public Object handle(ResultSet rs) throws SQLException {
					if (rs == null)
						return null;

					AreaBean bean = null;
					while (rs.next()) {
						bean = new AreaBean();
						bean.setAbb(rs.getString("abb"));
						bean.setId(rs.getInt("id"));
						bean.setName(rs.getString("name"));

						areaList.add(bean);
					}

					return null;
				}

			});
		} catch (SQLException e) {
			log.error("��ȡ������Ϣ�ǳ����쳣", e);
		}

		return areaList;
	}

	/**
	 * �����û���Ϣ:����ͬ��������쳣��ֹ�����ܵ��²����û���ҵ��״̬���� ��һ�µ�״̬����"����ͬ��"״̬�µĲ������ݿ����Ѿ���ʧ�ȡ�
	 * 
	 * @return
	 */
	public boolean cleanCustomerInfo() {
		int ret = -1;

		Connection con = null;
		CallableStatement cs = null;

		try {
			con = source.getConnection();
			cs = con.prepareCall("{call misc_pkg.init_revert_order(?)}");

			cs.registerOutParameter(1, Types.INTEGER);
			cs.execute();

			ret = cs.getInt(1);
		} catch (SQLException e) {
			log.error("��������ʧ�ܣ�", e);
			
			return false;
		} finally {
			DbUtils.closeQuietly(cs);
			DbUtils.closeQuietly(con);
		}

		if (ret == 0)
			return true;
		else
			return false;
	}
}
