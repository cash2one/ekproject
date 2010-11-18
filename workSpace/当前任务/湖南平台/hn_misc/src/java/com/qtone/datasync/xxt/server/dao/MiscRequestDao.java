package com.qtone.datasync.xxt.server.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qtone.datasync.dao.QtoneDataSource;
import com.qtone.datasync.xxt.server.bean.MiscRequestBean;

/**
 * 处理Misc同步过来的数据
 * 
 * @author 杨腾飞 2009-05-27
 */
public class MiscRequestDao {
	private final Log log = LogFactory.getLog(MiscRequestDao.class);

	private DataSource ds = null;

	public MiscRequestDao() {
		ds = new QtoneDataSource();
	}

	/**
	 * 保存从Misc同步过来的数据
	 * 
	 * @param bean
	 * @return 返回空表示操作成功
	 */
	public String save(MiscRequestBean bean) {
		String sql = "{call misc_pkg.handle_misc_order(?,?,?,?,?,?,?,?,?,?)}";
		Connection con = null;
		CallableStatement cs = null;

		String retCode = "";

		try {
			con = ds.getConnection();
			cs = con.prepareCall(sql);

			int idx = 1;
			cs.setString(idx++, bean.getMsgType());
			cs.setString(idx++, bean.getTransactionID());
			cs.setString(idx++, bean.getPhoneFee());
			cs.setString(idx++, bean.getPhoneUse());
			cs.setInt(idx++, bean.getAction());
			cs.setInt(idx++, bean.getActionReason());
			cs.setInt(idx++, bean.getAccessMode());
			cs.setString(idx++, bean.getFeatureStr());
			cs.setString(idx++, bean.getSpServiceId());

			cs.registerOutParameter(idx, Types.VARCHAR);
			
			cs.execute();

			retCode = cs.getString(idx);
		} catch (SQLException e) {
			log.error(e);
			
			retCode = "1";//处理失败
		} finally {
			DbUtils.closeQuietly(cs);
			DbUtils.closeQuietly(con);
		}

		return retCode;
	}
	
	/**
	 * 判断业务是否存在
	 * @param spServiceId
	 * @return
	 */
	public boolean busExists(String spServiceId) {
		String sql="select count(*) from tranpackage_define where salemodalid =?";
		Connection con = null;
		PreparedStatement stm=null;
		ResultSet rs=null;
		int row=0;
		try{
			con=ds.getConnection();
			stm=con.prepareStatement(sql);
			stm.setString(1,spServiceId);
			rs=stm.executeQuery();
			if(rs.next()){
				row=rs.getInt(1);
			}
		}catch(Exception e){
			log.error(sql,e);
		}finally{
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(stm);
			DbUtils.closeQuietly(con);
		}
		return row==0?false:true;
	}
	
	
	/**
	 * 通过号码获得所有的abb，family_id
	 * @param phoneUse
	 * @param spServiceId
	 * @return
	 */
	public List<String> getAbbByPhoneService(String phoneUse, String spServiceId) {
		List<String> abb_fam=new ArrayList<String>();
		String sql="select family_id,abb from family_phone where phone =?";
		Connection con = null;
		PreparedStatement stm=null;
		ResultSet rs=null;
		try{
			con=ds.getConnection();
			stm=con.prepareStatement(sql);
			stm.setString(1,phoneUse);
			rs=stm.executeQuery();
			while(rs.next()){
				abb_fam.add(rs.getString("abb")+"_"+rs.getString("family_id"));
			}
		}catch(Exception e){
			log.error(sql,e);
		}finally{
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(stm);
			DbUtils.closeQuietly(con);
		}
		return abb_fam;
	}
	
	
	/**
	 * 平台尚为有正向同步号码资料，需要代理商补全资料，暂时插入
	 * @param bean
	 */
	public void insertMiscOrder(MiscRequestBean bean) {
		String sql="insert into misc_order_relation(msg_type,transaction_id,phone_fee,phone_use,action,action_reason,salemodalid) " +
				"values(?,?,?,?,?,?,?)";
		Connection con = null;
		PreparedStatement stm=null;
		try{
			con=ds.getConnection();
			stm=con.prepareStatement(sql);
			stm.setString(1,bean.getMsgType());
			stm.setString(2,bean.getTransactionID());
			stm.setString(3,bean.getPhoneFee());
			stm.setString(4,bean.getPhoneUse());
			stm.setInt(5,bean.getAction());
			stm.setInt(6,bean.getActionReason());
			stm.setString(7,bean.getSpServiceId());
			stm.executeUpdate();
			
		}catch(Exception e){
			log.error(sql,e);
		}finally{
			DbUtils.closeQuietly(stm);
			DbUtils.closeQuietly(con);
		}
	}

	
	/**
	 * 判断是否存在订购关系
	 * @param abb_familyId 
	 * @param bean 
	 * @return
	 */
	public boolean isExistsOrder(MiscRequestBean bean, String abb_familyId) {
		String abb=abb_familyId.split("_")[0];
		String family_id=abb_familyId.split("_")[1];
		String sql="select count(*) from "+abb+"_tranpackage_customer where phone=? and family_id=? and xxt_SALEMODALID=?";
		Connection con = null;
		PreparedStatement stm=null;
		ResultSet rs=null;
		int row=0;
		try{
			con=ds.getConnection();
			stm=con.prepareStatement(sql);
			stm.setString(1,bean.getPhoneUse());
			stm.setString(2,family_id);
			stm.setString(3,bean.getSpServiceId());
			rs=stm.executeQuery();
			if(rs.next()){
				row=rs.getInt(1);
			}
		}catch(Exception e){
			log.error(sql,e);
		}finally{
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(stm);
			DbUtils.closeQuietly(con);
		}
		return row==0?false:true;
	}
	
	/**
	 * 删除订购关系
	 * @param bean
	 * @param object
	 */
	public void DeleteCustomer(MiscRequestBean bean, String abb_familyId) {
		String abb=abb_familyId.split("_")[0];
		String family_id=abb_familyId.split("_")[1];
		String sql="delete from "+abb+"_tranpackage_customer where phone=? and family_id=? and xxt_SALEMODALID=?";
		Connection con = null;
		PreparedStatement stm=null;
		try{
			con=ds.getConnection();
			stm=con.prepareStatement(sql);
			stm.setString(1,bean.getPhoneUse());
			stm.setString(2,family_id);
			stm.setString(3,bean.getSpServiceId());
			stm.executeUpdate();
		}catch(Exception e){
			log.error(sql, e);
		}finally{
			DbUtils.closeQuietly(stm);
			DbUtils.closeQuietly(con);
		}
	}
	
	/**
	 * 插入订购关系
	 * @param bean
	 * @param object
	 */
	public void insertCustomer(MiscRequestBean bean, String abb_familyId) {
		String abb=abb_familyId.split("_")[0];
		String family_id=abb_familyId.split("_")[1];
		String sql="insert into "+abb+"_tranpackage_customer(family_id,phone,boss_salemodalid,xxt_salemodalid,start_date,del,adcdeal_date,adcdeal_result,adcdeal_suc) " +
				"values(?,?,?,?,sysdate,0,sysdate,'成功','1')";
		Connection con = null;
		PreparedStatement stm=null;
		try{
			con=ds.getConnection();
			stm=con.prepareStatement(sql);
			stm.setString(1,family_id);
			stm.setString(2,bean.getPhoneUse());
			stm.setString(3,bean.getSpServiceId());
			stm.setString(4,bean.getSpServiceId());
			stm.executeUpdate();
		}catch(Exception e){
			log.error(sql, e);
		}finally{
			DbUtils.closeQuietly(stm);
			DbUtils.closeQuietly(con);
		}
	}

	
	/**
	 * 更新以后订购关系
	 * @param bean
	 * @param string
	 */
	public void updateCustomerByCustomerId(MiscRequestBean bean, String abb_familyId) {
		String abb=abb_familyId.split("_")[0];
		String family_id=abb_familyId.split("_")[1];
		String sql="update  "+abb+"_tranpackage_customer set boss_salemodalid=?,del=0,adcdeal_date=sysdate,adcdeal_result='成功',adcdeal_suc='1') " +
				" where  customer_id = ? ";
		Connection con = null;
		PreparedStatement stm=null;
		try{
			con=ds.getConnection();
			stm=con.prepareStatement(sql);
			stm.setString(1,bean.getSpServiceId());
			stm.setString(2,family_id);
			stm.executeUpdate();
		}catch(Exception e){
			log.error(sql, e);
		}finally{
			DbUtils.closeQuietly(stm);
			DbUtils.closeQuietly(con);
		}
	}
	
	
	/**
	 * 判断是否反向同步信息
	 * @param transactionID
	 * @return
	 */
	public String revertMsg(String transactionID) {
		String sql="select area_abb,customer_id from misc_order_relation where transaction_id=? and sync_seq is not null";
		Connection con = null;
		PreparedStatement stm=null;
		ResultSet rs=null;
		String abb_fam="";
		try{
			con=ds.getConnection();
			stm=con.prepareStatement(sql);
			stm.setString(1,transactionID);
			rs=stm.executeQuery();
			if(rs.next()){
				abb_fam=rs.getString("area_abb")+"_"+rs.getString("customer_id");
			}
		}catch(Exception e){
			log.error(sql, e);
		}finally{
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(stm);
			DbUtils.closeQuietly(con);
		}
		return abb_fam;
	}

	/**
	 * @param bean
	 * @param string
	 */
	public void updateCustomerByFamilyId(MiscRequestBean bean, String abb_familyId) {
		String abb=abb_familyId.split("_")[0];
		String family_id=abb_familyId.split("_")[1];
		String sql="update  "+abb+"_tranpackage_customer set boss_salemodalid=?,del=0,adcdeal_date=sysdate,adcdeal_result='成功',adcdeal_suc='1') " +
				" where phone=? and family_id=? and xxt_SALEMODALID=? ";
		Connection con = null;
		PreparedStatement stm=null;
		try{
			con=ds.getConnection();
			stm=con.prepareStatement(sql);
			stm.setString(1,bean.getSpServiceId());
			stm.setString(2,bean.getPhoneUse());
			stm.setString(3,family_id);
			stm.setString(4,bean.getSpServiceId());
			stm.executeUpdate();
		}catch(Exception e){
			log.error(sql, e);
		}finally{
			DbUtils.closeQuietly(stm);
			DbUtils.closeQuietly(con);
		}
	}
	
}
