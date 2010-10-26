package cn.qtone.xxt.csop.webservices;

import cn.qtone.xxt.csop.business.ServciceResponseParams;
import cn.qtone.xxt.csop.business.ServiceAgreement;
import cn.qtone.xxt.csop.services.impl.TransBooklogService;
import cn.qtone.xxt.csop.services.impl.TransUseStateServcie;
import cn.qtone.xxt.csop.util.CsopLog;
import cn.qtone.xxt.csop.webservices.bean.ServiceResponse;
import cn.qtone.xxt.csop.webservices.bean.TransBooklogParams;
import cn.qtone.xxt.csop.webservices.bean.TransUseStateParams;
import cn.qtone.xxt.csop.webservices.util.RequestParamsWrapper;

/**
 * 校讯通--业务使用记录查询
 * @author linhansheng
 * 
 */
public class TransUseStateInter {

	/**
	 * 服务接口 请求参数需要解释 对应的 xml文件。
	 * @param xml
	 * @return
	 */
	public String query(String xmlReqest) {
		CsopLog.debug("接收到 [业务使用记录查询] 服务请求......");
		TransUseStateParams requestParams = null;
		ServiceResponse reponse= null;
		RequestParamsWrapper<TransUseStateParams> wrapper = null;
		TransUseStateServcie service = null;
		try {
			wrapper = new RequestParamsWrapper<TransUseStateParams>();
			if (wrapper == null) {
				CsopLog.error("解释请求参数出错！");
			}
			requestParams = wrapper.formParams(xmlReqest,TransUseStateParams.class);
			if(requestParams==null){
				return  ServciceResponseParams.SV0M95.description(); 
			}
			service = new TransUseStateServcie();
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
		CsopLog.debug("接收到 [业务使用记录查询] 服务请求......");
		TransUseStateServcie service = new TransUseStateServcie();
		TransUseStateParams requestParams = new TransUseStateParams();
		
		requestParams.setPlatform(ServiceAgreement.TRANSCATION_USESTATE.platform());
		requestParams.setSysCode(ServiceAgreement.TRANSCATION_USESTATE.sysCode());
		requestParams.setBusiCode(ServiceAgreement.TRANSCATION_USESTATE.busiCode());
		requestParams.setVersion(ServiceAgreement.TRANSCATION_USESTATE.version());
		
		requestParams.setBeginDate(beginDate);
		requestParams.setEndDate(endDate);
		requestParams.setTelNo(phone);
		return service.query(requestParams).toString();
	}
	
}
