package cn.qtone.xxt.csop.webservices;

import cn.qtone.xxt.csop.business.ServciceResponseParams;
import cn.qtone.xxt.csop.business.ServiceAgreement;
import cn.qtone.xxt.csop.services.impl.TransCustomerQueryService;
import cn.qtone.xxt.csop.services.impl.TransCustomerService;
import cn.qtone.xxt.csop.util.CsopLog;
import cn.qtone.xxt.csop.webservices.bean.ServiceResponse;
import cn.qtone.xxt.csop.webservices.bean.TransCustomerParams;
import cn.qtone.xxt.csop.webservices.bean.TransCustomerQueryParams;
import cn.qtone.xxt.csop.webservices.util.RequestParamsWrapper;

/**
 * 校讯通--业务定制
 *  查询
 *  开通
 *  取消
 * @author linhansheng
 * 
 */
public class TransCustomerInter {

	/**
	 * 服务接口 请求参数需要解释 对应的 xml文件。
	 * @param xml
	 * @return
	 */
	public String query(String xmlReqest) {
		CsopLog.debug("接收到 [业务定制情况查询] 服务请求......");
		TransCustomerQueryParams requestParams = null;
		ServiceResponse reponse= null;
		RequestParamsWrapper<TransCustomerQueryParams> wrapper = null;
		TransCustomerQueryService service = null;
		try {
			wrapper = new RequestParamsWrapper<TransCustomerQueryParams>();
			if (wrapper == null) {
				CsopLog.error("解释请求参数出错！");
			}
			requestParams = wrapper.formParams(xmlReqest,TransCustomerQueryParams.class);
			if(requestParams==null){
				return  ServciceResponseParams.SV0M95.description(); 
			}
			service = new TransCustomerQueryService();
			reponse = service.query(requestParams);
			if(reponse!=null){
			   return reponse.toString();
			}else
			   return ServciceResponseParams.SUC.description();
		} catch (Exception e) {
			   e.printStackTrace();
		       return ServciceResponseParams.SV0MMM.description();
		}finally{
			wrapper = null;
			requestParams = null;
			service = null;
		}
	}
   
    
	//测试接口
	public String queryTest(String phone, String beginDate, String endDate) {
		CsopLog.debug("接收到 [业务定制情况查询] 服务请求......");
		TransCustomerQueryService service = new TransCustomerQueryService();
		TransCustomerQueryParams requestParams = new TransCustomerQueryParams();
		
		requestParams.setPlatform(ServiceAgreement.TRANSCATION_CUSTOMER_QUERY.platform());
		requestParams.setSysCode(ServiceAgreement.TRANSCATION_CUSTOMER_QUERY.sysCode());
		requestParams.setBusiCode(ServiceAgreement.TRANSCATION_CUSTOMER_QUERY.busiCode());
		requestParams.setVersion(ServiceAgreement.TRANSCATION_CUSTOMER_QUERY.version());
		
		requestParams.setBeginDate(beginDate);
		requestParams.setEndDate(endDate);
		requestParams.setTelNo(phone);
		return service.query(requestParams).toString();
	}
	
	
	/**
	 * 业务办理接口
	 * @param xmlReqest
	 * @return
	 */
	public String customer(String xmlReqest) {
		CsopLog.debug("接收到 [业务受理] 服务请求......");
		TransCustomerParams requestParams = null;
		ServiceResponse reponse= null;
		RequestParamsWrapper<TransCustomerParams> wrapper = null;
		TransCustomerService service = null;
		try {
			wrapper = new RequestParamsWrapper<TransCustomerParams>();
			if (wrapper == null) {
				CsopLog.error("解释请求参数出错！");
			}
			requestParams = wrapper.formParams(xmlReqest,TransCustomerParams.class);
			if(requestParams==null){
				return  ServciceResponseParams.SV0M95.description(); 
			}
			service = new TransCustomerService();
			reponse = service.customer(requestParams);
			if(reponse!=null){
			   return reponse.toString();
			}else
			   return ServciceResponseParams.SUC.description();
		} catch (Exception e) {
			   e.printStackTrace();
		       return ServciceResponseParams.SV0MMM.description();
		}finally{
			wrapper = null;
			requestParams = null;
			service = null;
		}
	}
	
	
	/**
	 * 测试接口
	 * @param phone
	 * @param busiFlag
	 * @param doFlag
	 * @return
	 */
	public String customerTest(String phone,String busiFlag,String doFlag) {
		CsopLog.debug("接收到 [业务受理] 服务请求......");
		TransCustomerService service = new TransCustomerService();
		TransCustomerParams requestParams = new TransCustomerParams();
		
		requestParams.setPlatform(ServiceAgreement.TRANSCATION_CUSTOMER_BOOK.platform());
		requestParams.setSysCode(ServiceAgreement.TRANSCATION_CUSTOMER_BOOK.sysCode());
		requestParams.setBusiCode(ServiceAgreement.TRANSCATION_CUSTOMER_BOOK.busiCode());
		requestParams.setVersion(ServiceAgreement.TRANSCATION_CUSTOMER_BOOK.version());
		
		requestParams.setTelNo(phone);
		requestParams.setDoFlag(doFlag);
		requestParams.setBusiFlag(busiFlag);
		return service.customer(requestParams).toString();
	}
	

}
