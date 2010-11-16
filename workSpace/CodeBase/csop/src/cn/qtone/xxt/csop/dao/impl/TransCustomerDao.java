package cn.qtone.xxt.csop.dao.impl;

import java.util.ArrayList;
import java.util.List;

import cn.qtone.xxt.csop.business.TransactionType;
import cn.qtone.xxt.csop.dao.comom.BaseDao;
import cn.qtone.xxt.csop.dao.comom.DBConnector;
import cn.qtone.xxt.csop.dao.model.TransCustomerRow;
import cn.qtone.xxt.csop.inter.AbstractTransDao;
import cn.qtone.xxt.csop.util.Checker;
import cn.qtone.xxt.csop.util.CsopLog;
import cn.qtone.xxt.csop.webservices.bean.TransCustomerParams;

/**
 *6.4.1.2 业务受理（B005_01）
 * 
 * @author linhansheng
 */
public class TransCustomerDao extends AbstractTransDao {

	static String POOL_NAME = "xxt";
	
	public String customer(TransCustomerParams requsetParams) {
		String dealType = requsetParams.getDoFlag();
		
		int doFlag = ("开通".equals(dealType)||"1".equals(dealType) ? 1 :(("取消".equals(dealType)||"0".equals(dealType)?0:-1)));
		if(doFlag==-1)
		  return "服务请求失败，无法区分业务受理类型。";
		
		String phone = requsetParams.getTelNo();
		List<String> areas = phoneServiceInAreas(phone);
		String transactionName = requsetParams.getBusiFlag();
		
		if(areas==null||areas.size()==0)
		  return "没有该关于该手机用户的业务订购信息";
		
		boolean dealStatu = doFlag == 0 ? cancle(phone, areas, transactionName) : book(
				phone, areas, transactionName);
		
		
		return dealStatu ? "操作成功" : "操作失败";
	}

	/**
	 * 
	 * @param cname
	 * @return
	 */
	int returnTransCodeByCname(String name) {
		for (TransactionType type : TransactionType.values()) {
			if (type.cname().equals(name)||type.ename().equals(name))
				return type.code();
		}
		return -1;
	}
  
	
	/**
	 * 执行退订操作
	 * @param phone
	 * @param areaAbbs
	 * @param transactionName
	 * @return
	 */
	boolean cancle(String phone, List<String> areaAbbs, String transactionName) {
		List<String> sqls = new ArrayList<String>();
		int transCode = 0;
		if (transCode == -1) {
			CsopLog.debug("业务取消失败，找不对对应的业务对应码[ " + transactionName + "]");
			return false;
		}
		
		String[] tempSqls = null;
		for (String areaAbb : areaAbbs) {
			
			if(!area.isPackageArea(areaAbb)){
				transCode = returnTransCodeByCname(transactionName);
				tempSqls = cancleBaseTransactionSql(areaAbb, transCode, phone);
			}else{
				//要获取指定的套餐ID
				tempSqls = canclePackageTransactionSql(areaAbb, transCode, phone);
			}
		
		}

		if (sqls.size() <= 0)
			return false;
		BaseDao db = null;
		try {
			db = new BaseDao(POOL_NAME,DBConnector.getConnection(POOL_NAME));
			db.executeBatch(sqls);
			return true;
		} catch (Exception e) {
			CsopLog.error("业务办理退订执行Sqls出错!");
			int index = 1;
			for (String sql : sqls)
				CsopLog.debug("出错SQL [" + index + "] :" + sql);
			return false;
		} finally {
			db.close();
			sqls.clear();
			sqls = null;
		}
	}

	
	/**
	 * 
	 * 取消基本业务SQL
	 * @param areaAbb
	 * @param transCode
	 * @param phone
	 */
	String[] cancleBaseTransactionSql(String areaAbb, int transCode, String phone) {
		String operator ="CSOP Admin ";
		String operatorReason ="CSOP 接口测试";
		int bookType = 0;
		
		String[] updateSqls = new String[2];
		StringBuffer mainSql = new StringBuffer();
		String table = areaAbb + "_xj_family ";
		mainSql.append(" update ").append(table);
		mainSql.append(" set ").append(TransactionType.values()[transCode-1].familyField()).append("=0");
		mainSql.append(" where phone='").append(phone).append("'");
		CsopLog.debug("基本业务取消Sql:" + mainSql);
		updateSqls[0] = mainSql.toString();
		
		  
		//补上日志操作记录
	    mainSql.delete(0, mainSql.length());
	    mainSql.append(" insert into ").append(areaAbb).append("_transaction_log ");
		mainSql.append("(family_id,stu_sequence,transaction,open,open_date,operator,phone,reason,book_type) ");
		mainSql.append(" select fa.id,FA.STU_SEQUENCE,"+transCode+",0,sysdate,'"+operator+"','"+phone+"','"+operatorReason+"',"+bookType+" from zs_xj_family fa ");
		mainSql.append(" where phone='").append(phone);
		updateSqls[1] = mainSql.toString();
		CsopLog.debug("基本业务取消操作日志Sql:" + mainSql);
		mainSql = null;
		return  updateSqls;
	}

	
	/**
	 * 注意 根据一种情况，如果该家长有两个孩子，因此
	 * 接口定义中 如果只指定 家长电话，就可能会引起此情况
	 * 
	 * 取消套餐业务SQL
	 * @param areaAbb   地区
	 * @param packageId 套餐ID
	 * @param phone  家长电话
	 */
	String[] canclePackageTransactionSql(String areaAbb, int packageId, String phone) {
		String[] updateSqls = new String[3];
		StringBuffer mainSql = new StringBuffer();
		String operator ="CSOP Admin ";
		String operatorReason ="CSOP 接口测试";
		int bookType = 0;
		
		
		//更新家庭表中 套餐对应的基本业务
		mainSql.append(" update  ").append(areaAbb).append("_xj_family a ");
		mainSql.append(" set (is_dxx,is_kaoqin,is_qin_qing_tel,is_liuyanban )= ");
		mainSql.append(" (select relate_dxx,relate_kq,relate_qqdh,relate_lyb from preferential_package where id ="+packageId+" ) ");
		mainSql.append(" where a.phone = '").append(phone).append("'");
		CsopLog.debug("套餐业务取消Sql-1:" + mainSql);
		
		//更新 套餐订购关系表
		mainSql.delete(0, mainSql.length());
		mainSql.append(" update ").append(areaAbb).append("_preferential_packager a ");
		mainSql.append(" set del=0 where pp_id=").append(packageId);
		mainSql.append(" and del =1 and  ");
		mainSql.append(" exists (select id from "+areaAbb+"_xj_family b where b.phone='"+phone+"' and a.f_id = b.id  ) ");
		CsopLog.debug("套餐业务取消Sql-2:" + mainSql);
		updateSqls[1] = mainSql.toString();
		
		
		//补上日志操作记录
		 mainSql.delete(0, mainSql.length());
		 mainSql.append(" insert into ").append(areaAbb).append("_transaction_log ");
	     mainSql.append("(family_id,stu_sequence,package_id,open,open_date,operator,phone,reason,book_type) ");
	     mainSql.append(" select fa.id,FA.STU_SEQUENCE,"+packageId+",0,sysdate,'"+operator+"','"+phone+"','"+operatorReason+"',"+bookType+" from zs_xj_family fa ");
		 mainSql.append(" where phone='").append(phone);
		 updateSqls[2] = mainSql.toString();   		
		 CsopLog.debug("套餐业务取消操作日志Sql:" + mainSql);
		 
		 mainSql = null;
		return updateSqls;
	}
	
	
	boolean book(String phone, List<String> areaAbbs,String transactionName) {

		return false;
	}


}
