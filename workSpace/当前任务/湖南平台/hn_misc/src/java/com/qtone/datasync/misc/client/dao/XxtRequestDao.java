package com.qtone.datasync.misc.client.dao;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import bsh.org.objectweb.asm.Type;

import com.qtone.datasync.bean.AreaBean;
import com.qtone.datasync.dao.QtoneDataSource;
import com.qtone.datasync.misc.Constraints;
import com.qtone.datasync.misc.client.ManagerThread;
import com.qtone.datasync.misc.client.RevertSyncTask;
import com.qtone.datasync.misc.client.bean.MiscRespBean;
import com.qtone.datasync.misc.util.RevertSyncContext;
import com.qtone.datasync.util.Context;
import com.qtone.datasync.util.UserInfoBeanQueue;
import com.qtone.datasync.xxt.server.bean.XxtRequestBean;

/**
 * 用于处理与反向同步操作相关的数据库操作
 * 
 * @author 杨腾飞 2009-6-4
 */
public class XxtRequestDao {
	private final Log log = LogFactory.getLog(XxtRequestDao.class);
	private QueryRunner run = null;
	private DataSource ds = null;

	public XxtRequestDao() {
		ds = new QtoneDataSource();
		run = new QueryRunner(ds);
	}

	/**
	 * 将数据的状态更新至正在同步
	 */
	public boolean addUserInfoToMiscOrderTab(final XxtRequestBean userInfo) {
		String sql = "insert into misc_order_relation(area_abb,customer_id,msg_type,";
		sql += "transaction_id,phone_fee,phone_use,action,salemodalid,sync_seq) values(?,?,?,?,?,?,?,?,?)";

		int ret = 0;
		boolean hasError = false;
		try {
			ret = run.update(sql, new Object[] { userInfo.getAreaAbb(),
					userInfo.getId(), userInfo.getMsgType(),
					userInfo.getTransactionId(), userInfo.getPhone(),
					userInfo.getPhone(), userInfo.getAction(),
					userInfo.getSpServiceId(), Context.SYNC_SEQ });
		} catch (SQLException e) {
			hasError = true;
			log.error("反向同步前将数据写入到[misc_order_relation]发生异常！", e);
		}

		if (ret == 1 && !hasError)
			return true;

		return false;
	}

	/**
	 * 反向同步失败，需要把数据状态更新至失败状态
	 * 
	 * @param bean
	 */
	public void changeStatusToFailure(final MiscRespBean bean) {
		if (log.isDebugEnabled()) {
			log.debug("修改数据状态为[同步失败]");
		}

		String sql = "{call misc_pkg.change_status_to_fail(?)}";

		Connection conn = null;
		CallableStatement cs = null;

		try {
			conn = ds.getConnection();
			cs = conn.prepareCall(sql);

			cs.setString(1, bean.getTransactionId());

			cs.execute();
		} catch (SQLException e) {
			log.error("数据状态更新失败！", e);
		} finally {
			DbUtils.closeQuietly(cs);
			DbUtils.closeQuietly(conn);
		}
	}

	/**
	 * 正向同步时，按地区查询用户信息
	 * 
	 * @param areaAbb
	 * @param rsh
	 */
	public void queryUserInfoByArea(final AreaBean areaBean) {
		int currPage = 1, pageSize = 100;//

		String qrySql = getQueryString(areaBean.getAbb());

		int idx = 0;
		try {
			do {
				if (log.isDebugEnabled())
					log.debug("当前正在处理[" + areaBean.getName() + "]地区第["
							+ currPage + "]页数据....");

				idx = (Integer) run.query(qrySql.toString(),
						getResultSetHandler(areaBean), new Object[] { 1,
								pageSize });

				currPage++;

				TimeUnit.SECONDS.sleep(5L);
			} while (idx > 0);
		} catch (SQLException e) {
			log.error(areaBean.getName() + "地区数据处理时出现异常" + qrySql, e);
		} catch (InterruptedException e) {
			log.error("查询线程在休眠过程中被中断" + areaBean.getName() + "地区的数据无法继续处理！");
		}

		log.info(areaBean.getName() + "地区数据处理完成！");
	}

	/**
	 * 反向同步时，用户信息处理
	 * 
	 * @param areaBean
	 * @return
	 */
	private ResultSetHandler getResultSetHandler(final AreaBean areaBean) {
		ResultSetHandler rsHandler = new ResultSetHandler() {
			public Object handle(ResultSet rs) throws SQLException {
				if (rs == null)
					return 0;

				int idx = 0;
				XxtRequestBean bean = null;

				boolean isInterrupted = false;
				while (rs.next()) {
					bean = new XxtRequestBean();
					
					//添加对CP的支持
					if(areaBean == null){
						bean.setIsCp(1);
						bean.setAreaAbb(null);
					}else{
						bean.setIsCp(0);
						bean.setAreaAbb(areaBean.getAbb());					
					}					
					
					bean.setId(rs.getLong("id"));
					bean.setTransactionId(Long.toString(new Date().getTime()));
					bean.setPhone(rs.getString("phone"));
					bean.setSpServiceId(rs.getString("xxt_salemodalid"));
					bean.setFamily_id(rs.getInt("family_id"));

					int del = rs.getInt("del");
					int action = del == 0 ? 1 : 2;
					bean.setAction(action);
					bean.setMsgType(action == 1 ? Constraints.MSG_TYPE_SUBSCRIBE
									: Constraints.MSG_TYPE_UNSUBSCRIBE);

					idx++;

					try {
						RevertSyncTask task = new RevertSyncTask(new URL(
								RevertSyncContext.MISC_SERVER_ADDR), bean);
						// UserInfoBeanQueue.addUserInfo(bean);
						UserInfoBeanQueue.incrementProducedBeanCount();

						ManagerThread.threadPool.submit(task);
						/*
						 * 向线程池提交的任务如果被中断，将不会进行其它处理。那么下次扫描时还会将该同步单元 重新读取并进行同步。
						 */
					} catch (InterruptedException e) {
						isInterrupted = true;
						log.error("向线程池中提交任务时被中断", e);
					} catch (MalformedURLException e) {
						log.error("反向同步->MISC服务器地址格式不正确", e);
					}

					// 更新该数据的状态为正在同步
					if (!isInterrupted) {
						changeStatusToInProcess(areaBean, bean);
						isInterrupted = false;
					}
				}

				return idx;
			}
		};

		return rsHandler;
	}

	/**
	 * 反向同时，获取到用户信息之后，将用户状态更新至正在同步
	 * 
	 * @param areaBean
	 * @param reqBean
	 */
	public void changeStatusToInProcess(final AreaBean areaBean,
			final XxtRequestBean reqBean) {
		String updateSql ;
		if(areaBean != null)
			updateSql = getUpdateString(areaBean.getAbb());
		else
			updateSql = "update cp_tran_customer set boss_status = 'SYNCING' where id = ? ";
		try {
			run.update(updateSql, new Object[] { reqBean.getId() });
		} catch (SQLException e) {
			log.error(e);
		}
	}

	private String getQueryString(String areaAbb) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from (");
		sql.append("select family_id,id,phone,xxt_salemodalid,del,");
		sql.append(" row_number() over(order by id) rn from ");
		sql.append(areaAbb).append("_tranpackage_customer a ");
		sql.append(" where boss_salemodalid is not null and (adcdeal_suc is null or adcdeal_suc = 1) and del = 1 "); // 反向同步只处理需要取消的数据
		sql.append(" ) where rn between ? and ? ");

		String ret = sql.toString();
		if (log.isDebugEnabled())
			log.debug(ret);

		return ret;
	}

	/**
	 * 数据更新SqL
	 * 
	 * @param abb
	 * @return
	 */
	private String getUpdateString(String abb) {
		StringBuilder sql = new StringBuilder();
		sql.append("update ").append(abb).append("_tranpackage_customer ");
		sql.append(" set adcdeal_date = sysdate,adcdeal_result='正在同步',adcdeal_suc = 3 ");
		sql.append(" where id = ? ");

		return sql.toString();
	}

	/**
	 * 检测同一手机号码是还有与该业务之间的订购关系
	 * 
	 * @param userInfo
	 * @return
	 */
	public int countByPhoneAndTran(final XxtRequestBean userInfo) {
		String sql = "{call misc_pkg.count_by_phone_and_tran(?,?,?,?)}";
		
		Connection conn = null;
		CallableStatement cs = null;
		
		int result = 0;
		try {
			conn = ds.getConnection();
			cs = conn.prepareCall(sql);
			
			int idx = 1;
			cs.setString(idx++, userInfo.getPhone());
			cs.setString(idx++, userInfo.getSpServiceId());
			cs.setInt(idx++, userInfo.getFamily_id());
			cs.registerOutParameter(idx++, Type.INT);
			
			cs.execute();
			result = cs.getInt(4);			
		} catch (SQLException e) {
			log.error("同步程序在些处理无法判断该号码是否还有其它的订购关系，无法继续进行同步！程序马上退出！",e);
			
			System.exit(-1);
		}finally{
			try {
				if(cs != null)
					cs.close();
				
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				log.error("无法正常关闭数据库连接！", e);
			}
		}

		return result;
	}

	/**
	 * 本地取消用户业务关系
	 * 
	 * @param userInfo
	 */
	public void cancelOrderLocal(final XxtRequestBean userInfo) {
		StringBuilder sql = new StringBuilder();
		try {
			if(1==userInfo.getIsCp()) {
				//CP反向取消(手机书院取消收费业务对应的免费业务)免费业务日志
				sql.delete(0, sql.length());
				sql.append("insert into cp_tran_customer_log(customer_id,transaction_id,operate_type) ");
				sql.append("select e.customer_id,e.transaction_id,0 from cp_tran_customer a,cp_transaction b,cp_customer c,cp_transaction d,cp_tran_customer e ");
				sql.append("where a.transaction_id=b.id and a.customer_id=c.id and a.id=? and b.id<>d.id and b.cp_id=d.cp_id and b.no is not null and d.no is not null ");
				sql.append("and b.no=d.no and e.transaction_id=d.id and e.customer_id=c.id and e.del=0");
				run.update(sql.toString(),new Object[]{userInfo.getId()});
				
				//CP反向取消(手机书院取消收费业务对应的免费业务)免费业务
				sql.delete(0, sql.length());
				sql.append("update cp_tran_customer t set del=1 where exists(select 1 from cp_tran_customer a,cp_transaction b,cp_customer c,cp_transaction d ");
				sql.append("where a.transaction_id=b.id and a.customer_id=c.id and a.id=? and b.id<>d.id and b.cp_id=d.cp_id and b.no is not null and d.no is not null ");
				sql.append("and b.no=d.no and t.transaction_id=d.id and t.customer_id=c.id)");
				run.update(sql.toString(),new Object[]{userInfo.getId()});
				
				//CP反向取消该收费业务
				sql.delete(0, sql.length());
				sql.append("delete from cp_tran_customer where id = ?");
				run.update(sql.toString(),new Object[]{userInfo.getId()});
			} else if (0 == userInfo.getIsCp()) {
				sql.append(" delete from ").append(userInfo.getAreaAbb()).append("_tranpackage_customer where id = ? ");
				run.update(sql.toString(),new Object[]{userInfo.getId()});
			}
		} catch (SQLException e) {
			log.error(e);
		}
	}

	/**
	 * 查询CP业务中需要处理的反向退订信息
	 */
	public void queryCpUserInfo() {
		StringBuilder sql = new StringBuilder();
		sql.append(" select * from (select ");
		sql.append(" tran_cus_rel.id,customer.phone,tran.code xxt_salemodalid,customer.id family_id,");
		sql.append(" tran_cus_rel.del,row_number() over(order by operate_date) rn from ");
		sql.append(" cp_customer customer,cp_tran_customer tran_cus_rel,cp_transaction tran ");
		sql.append(" where customer.id = tran_cus_rel.customer_id ");
		sql.append(" and tran_cus_rel.transaction_id = tran.id ");
		sql.append(" and tran_cus_rel.del = 1 and tran_cus_rel.boss_status = 'SYNC_SUCC') ");
		sql.append(" where rn between ? and ? ");

		int currPage = 1, pageSize = 100;//

		int idx = 0;
		try {
			do {
				if (log.isDebugEnabled())
					log.debug("当前正在处理CP数据的第[" + currPage + "]页数据....");

				idx = (Integer) run.query(sql.toString(),
						getResultSetHandler(null), new Object[] { 1,
								pageSize });

				currPage++;

				TimeUnit.SECONDS.sleep(5L);
			} while (idx > 0);
		} catch (SQLException e) {
			log.error("CP业务数据处理时出现异常" + sql.toString(), e);
		} catch (InterruptedException e) {
			log.error("查询线程在休眠过程中被中断CP业务的数据无法继续处理！");
		}

		log.info("CP业务数据处理完成！");
	
	}
}
