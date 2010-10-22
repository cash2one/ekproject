package cn.qtone.xxt.csop.dao.impl;

import java.util.List;

import cn.qtone.xxt.csop.business.TransactionType;
import cn.qtone.xxt.csop.dao.inter.ResultRow;
import cn.qtone.xxt.csop.util.CsopLog;
import cn.qtone.xxt.csop.webservices.bean.RequestParams;

/**
 *6.4.1.1 ҵ���������ѯ�ӿڣ�B005_01��
 * 
 * 
 * ����ҵ�����(�������ҵ��ֿ�����)�� �ײ͵���(�ѻ���ҵ���װΪ�ײͣ��Զ����ײ͵���ʽ���Ʒ���)�� ADC�Ե����(��Ӫ�������ķ�ʽ���Ʒ���)
 * 
 * @author linhansheng
 */
public class TransCustomerQueryDao extends AbstractTransDao {

	/**
	 * �ӿڶ���ķ������ݸ�ʽ���ֶΣ� ҵ������ ҵ��˿� ҵ�����ݼ�� �ʷѣ�Ԫ�� �Ʒ�����  
	 * ��ͨ��ʽ   ����ʱ��    ҵ��ʹ��״̬  �۷�ʱ��   Ӫ��������Ϣ
	 */
	public List<ResultRow> query(RequestParams reqParams) {
		StringBuffer querySql = new StringBuffer();
		String phone = reqParams.getTelNo();
		String beginTime = reqParams.getBeginDate();
		String endTime = reqParams.getEndDate();

		List<String> serviceAreas = phoneServiceInAreas(phone);
		if (serviceAreas == null) {
			CsopLog.info("�����ڸ��û���" + phone + "����ҵ������Ϣ!");
			return null;
		}
		return null;
	}

	// �ײͲ�ѯ �������
	void packageTransaction(String areaAbb, String phone, String beginDate,
			String endDate) {

		
	}

	// ����ҵ�� �������
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
