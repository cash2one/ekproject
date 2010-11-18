package com.qtone.datasync.local.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qtone.datasync.misc.Constraints;
import com.qtone.datasync.util.UserInfoBeanQueue;
import com.qtone.datasync.xxt.server.bean.XxtRequestBean;

/**
 * @author ���ڷ� 2009-7-6
 * 
 */
public class ScannerDao {
	private Log log = LogFactory.getLog(getClass());
	
	private final String areaAbb ;
	private final QueryRunner run;

	private ResultSetHandler rsh = null;
	
	public ScannerDao(final DataSource ds,String areaAbb) {
		this.areaAbb = areaAbb;
		
		run = new QueryRunner(ds);
		
		rsh = new ResultSetHandler(){

			public Object handle(ResultSet rs) throws SQLException {
				int idx = 0;
				XxtRequestBean bean = null;

				boolean isInterrupted = false;
				while (rs.next()) {
					bean = new XxtRequestBean();
					bean.setAreaAbb(ScannerDao.this.areaAbb);
					bean.setId(rs.getLong("id"));
					bean.setTransactionId(Long.toString(System.nanoTime())
							+ Long.toString(rs.getLong("id")));
					bean.setPhone(rs.getString("phone"));
					bean.setSpServiceId(rs.getString("xxt_salemodalid"));

					int del = rs.getInt("del");
					int action = del == 0 ? 1 : 2;
					bean.setAction(action);
					bean.setMsgType(action == 1 ? Constraints.MSG_TYPE_SUBSCRIBE
									: Constraints.MSG_TYPE_UNSUBSCRIBE);

					idx++;
					
					// TODO:��ʱ���ʵ����Ϣ�����ȥ�����أ�
					try {
						UserInfoBeanQueue.addUserInfo(bean);
						UserInfoBeanQueue.incrementProducedBeanCount();
					} catch (InterruptedException e) {
						isInterrupted = true;
						log.error("�������[UserInfoBeanQueue]���Bean��Ϣʱ���ж�", e);
					}

					// ���¸����ݵ�״̬Ϊ����ͬ��
					if (!isInterrupted) {
						changeStatusToInProcess(bean);
						isInterrupted = false;
					}
				}

				return idx;
			}
			
		};
	}

	/**
	 * ����ͬʱ����ȡ���û���Ϣ֮�󣬽��û�״̬����������ͬ��
	 * 
	 * @param areaBean
	 * @param reqBean
	 */
	public void changeStatusToInProcess(final XxtRequestBean reqBean) {
		final String updateSql = createUpdateSql();
		try {
			run.update(updateSql, new Object[] { reqBean.getId() });
		} catch (SQLException e) {
			log.error(e);
		}
	}
	
	/**
	 * ɨ��һ�����������ݣ��������ݷ��뻺����
	 * 
	 * @param areaAbb
	 * @return
	 */
	public boolean scanData() {
		boolean succ = false;
		String sql = createQuerySql();

		try {
			run.query(sql, rsh);
			succ = true;
		} catch (SQLException e) {
			log.error(e);
			succ = false;
		}
		
		return succ;
	}

	/**
	 * �����ѯ���
	 * 
	 * @param areaAbb
	 * @return
	 */
	private String createQuerySql() {
		StringBuilder sql = new StringBuilder();
		//sql.append("select * from (");
		sql.append("select id,phone,xxt_salemodalid,del from ");
		//sql.append(" ,row_number() over(order by id) rn from ");
		sql.append(areaAbb).append("_tranpackage_customer a ");
		sql.append(" where adcdeal_suc is null or adcdeal_suc = 2 ");
		//sql.append(" ) where rn between ? and ? ");

		String ret = sql.toString();
		if (log.isDebugEnabled())
			log.debug(ret);
		
		return ret;
	}
	
	public String createUpdateSql(){
		StringBuilder sql = new StringBuilder();
		sql.append("update ").append(areaAbb).append("_tranpackage_customer ");
		sql.append(" set adcdeal_date = sysdate,adcdeal_result='����ͬ��',adcdeal_suc = 3 ");
		sql.append(" where id = ? ");

		return sql.toString();
	}
}
