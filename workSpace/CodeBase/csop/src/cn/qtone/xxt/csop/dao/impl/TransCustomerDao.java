package cn.qtone.xxt.csop.dao.impl;

import java.util.ArrayList;
import java.util.List;

import cn.qtone.xxt.csop.business.TransactionType;
import cn.qtone.xxt.csop.dao.comom.BaseDao;
import cn.qtone.xxt.csop.dao.comom.DBConnector;
import cn.qtone.xxt.csop.dao.model.TransCustomerRow;
import cn.qtone.xxt.csop.inter.AbstractTransDao;
import cn.qtone.xxt.csop.util.CsopLog;
import cn.qtone.xxt.csop.webservices.bean.TransCustomerParams;

/**
 *6.4.1.1 业务受理（B005_01）
 * 
 * @author linhansheng
 */
public class TransCustomerDao extends AbstractTransDao<TransCustomerParams,TransCustomerRow> {

	static String POOL_NAME = "xxt";

	public String customer(TransCustomerParams requsetParams) {
		String dealType = requsetParams.getDoFlag();
		int doFlag = ("开通".equals(dealType) ? 1 : 0);
		String phone = requsetParams.getTelNo();
		List<String> areas = phoneServiceInAreas(phone);
		if(areas==null||areas.size()==0)
		  return "没有该关于该手机用户的业务订购信息";
		boolean dealStatu = doFlag == 0 ? cancle(phone, areas, null) : book(
				phone, areas, null);
		return dealStatu ? "操作成功" : "操作失败";
	}

	/**
	 * 
	 * @param cname
	 * @return
	 */
	int returnTransCodeByCname(String cname) {
		for (TransactionType type : TransactionType.values()) {
			if (type.cname().equals(cname))
				return type.code();
		}
		return -1;
	}
  
	/**
	 * 添加操作日志
	 */
	String addLog(String areaAbb,String phone,int transCode,boolean isOpen) {
		
		return "";
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
		int transCode = returnTransCodeByCname(transactionName);
		if (transCode == -1) {
			CsopLog.debug("业务取消失败，找不对对应的业务对应码[ " + transactionName + "]");
			return false;
		}
		for (String areaAbb : areaAbbs) {
			sqls.add(cancleTransactionSql(areaAbb, transCode, phone));
			sqls.add(addLog(areaAbb,phone,transCode,false));
		}

		if (sqls.size() <= 0)
			return false;
		BaseDao db = null;
		try {
			db = new BaseDao(DBConnector.getConnection(POOL_NAME));
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
	 * 取消业务SQL
	 * @param areaAbb
	 * @param transCode
	 * @param phone
	 */
	String cancleTransactionSql(String areaAbb, int transCode, String phone) {
		StringBuffer mainSql = new StringBuffer();
		String table = areaAbb + "_xj_family_view ";
		mainSql.append(" update ").append(table);
		mainSql.append(" set ").append(
				TransactionType.values()[transCode].familyField()).append("=")
				.append(0);
		mainSql.append(" where id in( select id from ").append(areaAbb).append(
				"_xj_family ");
		mainSql.append(" where (phone='").append(phone).append(
				"' or KF_PHONE='").append(phone).append("')");
		CsopLog.debug(" 业务取消Sql:" + mainSql);
		return mainSql.toString();
	}

	boolean book(String phone, List<String> areaAbbs,String transactionName) {

		return false;
	}

	@Override
	public List<TransCustomerRow> query(TransCustomerParams requsetParams) {
		
		return null;
	}

}
