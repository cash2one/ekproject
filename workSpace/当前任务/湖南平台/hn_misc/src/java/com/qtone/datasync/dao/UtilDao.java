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
 * 辅助DAO类，用于从数据库中获取一些实用信息
 * 
 * @author 杨腾飞 2009-6-8
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
	 * 获取除了调试地区以外的所有地区信息
	 * 
	 * @return
	 */
	public List<AreaBean> getAreaList() {
		// TODO:测试过程中，只对调试地区的数据进行处理
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
			log.error("获取地区信息是出现异常", e);
		}

		return areaList;
	}

	/**
	 * 清理用户信息:由于同步程序的异常终止，可能导致部分用户的业务状态处于 不一致的状态，如"正在同步"状态下的部分数据可能已经丢失等。
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
			log.error("数据清理失败！", e);
			
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
