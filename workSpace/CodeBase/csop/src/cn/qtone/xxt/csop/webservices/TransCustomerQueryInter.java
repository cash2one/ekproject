package cn.qtone.xxt.csop.webservices;

import cn.qtone.xxt.csop.services.impl.TransCustomerQueryService;
import cn.qtone.xxt.csop.util.CsopLog;
import cn.qtone.xxt.csop.webservices.bean.ServiceResponse;
import cn.qtone.xxt.csop.webservices.bean.TransCustomerQueryParams;
import cn.qtone.xxt.csop.webservices.util.RequestParamsWrapper;

/**
 * 校讯通--业务定制情况查询
 * @author linhansheng
 * 
 */
public class TransCustomerQueryInter {

	/**
	 * 服务接口 请求参数需要解释 对应的 xml文件。
	 * @param xml
	 * @return
	 */
	public String query(String xml) {
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
			requestParams = wrapper.formParams(xml,TransCustomerQueryParams.class);
			if(requestParams==null){
				return  "服务异常，解释请求报文失败，    对象为空！"; 
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
   
    
	//测试接口
	public String queryTest(String phone, String beginDate, String endDate) {
		CsopLog.debug("接收到 [业务定制情况查询] 服务请求......");
		TransCustomerQueryService service = new TransCustomerQueryService();
		TransCustomerQueryParams requestParams = new TransCustomerQueryParams();
		requestParams.setBeginDate(beginDate);
		requestParams.setEndDate(endDate);
		requestParams.setTelNo(phone);
		return service.query(requestParams).toString();
	}

	public static void main(String...srt){
		System.out.println("test");
	}
	
}
