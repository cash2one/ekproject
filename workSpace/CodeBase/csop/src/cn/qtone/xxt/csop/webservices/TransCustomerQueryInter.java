package cn.qtone.xxt.csop.webservices;

import cn.qtone.xxt.csop.services.impl.TransCustomerQueryService;
import cn.qtone.xxt.csop.util.CsopLog;
import cn.qtone.xxt.csop.webservices.bean.ServiceResponse;
import cn.qtone.xxt.csop.webservices.bean.TransCustomerQueryParams;
import cn.qtone.xxt.csop.webservices.util.RequestParamsWrapper;

/**
 * УѶͨ--ҵ���������ѯ
 * @author linhansheng
 * 
 */
public class TransCustomerQueryInter {

	/**
	 * ����ӿ� ���������Ҫ���� ��Ӧ�� xml�ļ���
	 * @param xml
	 * @return
	 */
	public String query(String xml) {
		CsopLog.debug("���յ� [ҵ���������ѯ] ��������......");
		TransCustomerQueryParams requestParams = null;
		ServiceResponse reponse= null;
		RequestParamsWrapper<TransCustomerQueryParams> wrapper = null;
		TransCustomerQueryService service = null;
		try {
			wrapper = new RequestParamsWrapper<TransCustomerQueryParams>();
			if (wrapper == null) {
				CsopLog.error("���������������");
			}
			requestParams = wrapper.formParams(xml,TransCustomerQueryParams.class);
			if(requestParams==null){
				return  "�����쳣������������ʧ�ܣ�    ����Ϊ�գ�"; 
			}
			service = new TransCustomerQueryService();
			reponse = service.query(requestParams);
			if(reponse!=null){
			   return reponse.toString();
			}else
			   return "service suc,but result is null";
		} catch (Exception e) {
			   e.printStackTrace();
		       return "serice unsuc,maybe have errors";
		}finally{
			wrapper = null;
			requestParams = null;
			service = null;
		}
	}
   
    
	//���Խӿ�
	private String queryTest(String phone, String beginDate, String endDate) {
		CsopLog.debug("���յ� [ҵ���������ѯ] ��������......");
		TransCustomerQueryService service = new TransCustomerQueryService();
		TransCustomerQueryParams requestParams = new TransCustomerQueryParams();
		requestParams.setBeginDate(beginDate);
		requestParams.setEndDate(endDate);
		requestParams.setTelNo(phone);
		return service.query(requestParams).toString();
	}

}
