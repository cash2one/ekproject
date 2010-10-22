package cn.qtone.xxt.csop.dao.impl;

import java.util.List;

import cn.qtone.xxt.csop.business.TransactionType;
import cn.qtone.xxt.csop.dao.inter.ResultRow;
import cn.qtone.xxt.csop.util.CsopLog;
import cn.qtone.xxt.csop.webservices.bean.RequestParams;

/**
 *6.4.1.1 业务定制情况查询接口（B005_01）
 * 
 * 
 * 基本业务地区(四项基本业务分开定制)、 套餐地区(把基本业务封装为套餐，以定制套餐的形式定制服务)、 ADC试点地区(以营销方案的方式定制服务)
 * 
 * @author linhansheng
 */
public class TransCustomerQueryDao extends AbstractTransDao {

	/**
	 * 接口定义的返回数据格式（字段） 业务名称 业务端口 业务内容简介 资费（元） 计费类型  
	 * 开通方式   订购时间    业务使用状态  扣费时间   营销关联信息
	 */
	public List<ResultRow> query(RequestParams reqParams) {
		StringBuffer querySql = new StringBuffer();
		String phone = reqParams.getTelNo();
		String beginTime = reqParams.getBeginDate();
		String endTime = reqParams.getEndDate();

		List<String> serviceAreas = phoneServiceInAreas(phone);
		if (serviceAreas == null) {
			CsopLog.info("不存在该用户【" + phone + "】的业务定制信息!");
			return null;
		}
		return null;
	}

	// 套餐查询 定制情况
	void packageTransaction(String areaAbb, String phone, String beginDate,
			String endDate) {

		
	}

	// 基本业务 定制情况
	String baseTransaction(String areaAbb, String phone, String beginDate,
			String endDate) {
		StringBuffer baseView = new StringBuffer();
		for (TransactionType type : TransactionType.values()) {
			baseView.append("select fv.id,fv.phone,fv.stu_sequence,'"
					+ type.cname() + "' transaction ,'" + type.code()
					+ "' transaction_id," + type.field() + " is_open ");
			baseView.append(" from "+areaAbb+"_xj_family_view fv where phone ='"
					+ phone + "' ");
			baseView.append(" union ");
		}
		baseView.delete(baseView.length()-"  union ".length(), baseView.length());
	    
		return baseView.toString();	
	}

	public static void main(String...srt){
		TransCustomerQueryDao test = new TransCustomerQueryDao();
	    System.out.println(test.baseTransaction("zs","13422215791", null, null));	
	}
	
}
