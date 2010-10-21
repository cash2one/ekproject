package cn.qtone.xxt.csop.webservices;

import cn.qtone.xxt.csop.webservices.bean.ServiceResponse;
import cn.qtone.xxt.csop.webservices.bean.TransCustomerQueryParams;
import cn.qtone.xxt.csop.webservices.util.RequestParamsWrapper;

/**
 * УѶͨ--ҵ���������ѯ
 * @author linhansheng
 * 
 */
public class TransCustomerQueryService {

	/**
	 * ����ӿ� ���������Ҫ���� ��Ӧ�� xml�ļ���
	 * @param xml
	 * @return
	 */
	public String query(String xml) {
		System.out.println("���յ� [ҵ���������ѯ] ��������......");
		TransCustomerQueryParams requestParams = null;
		ServiceResponse reponse= null;
		RequestParamsWrapper<TransCustomerQueryParams> wrapper = null;
		try {
			wrapper = new RequestParamsWrapper<TransCustomerQueryParams>();
			if (wrapper == null) {
				System.out.println("���������������");
			}
			requestParams = wrapper.formParams(xml,TransCustomerQueryParams.class);
			if(requestParams==null){
				return  "�����쳣������������ʧ�ܣ�����Ϊ�գ�"; 
			}
			
			reponse = query(requestParams.getTelNo(),requestParams.getBeginDate(),requestParams.getEndDate());
			if(reponse!=null){
			   return reponse.toXML();
			}else
			   return "service suc,but result is null";
		} catch (Exception e) {
			   e.printStackTrace();
		       return "serice unsuc,maybe have errors";
		}finally{
			wrapper = null;
			requestParams = null;
		}
	}
   
	
	/**
	 * @param phone
	 * @param startDate
	 * @param endDate
	 * @return
	 * 
	 */
	private ServiceResponse query(String phone, String startDate, String endDate) {
		return new ServiceResponse();
	}

}
