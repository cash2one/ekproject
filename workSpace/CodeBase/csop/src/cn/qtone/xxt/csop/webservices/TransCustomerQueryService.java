package cn.qtone.xxt.csop.webservices;

import cn.qtone.xxt.csop.webservices.bean.ServiceResponse;
import cn.qtone.xxt.csop.webservices.bean.TransCustomerQueryParams;
import cn.qtone.xxt.csop.webservices.util.RequestParamsWrapper;

/**
 * 校讯通--业务定制情况查询
 * @author linhansheng
 * 
 */
public class TransCustomerQueryService {

	/**
	 * 服务接口 请求参数需要解释 对应的 xml文件。
	 * @param xml
	 * @return
	 */
	public String query(String xml) {
		System.out.println("接收到 [业务定制情况查询] 服务请求......");
		TransCustomerQueryParams requestParams = null;
		ServiceResponse reponse= null;
		RequestParamsWrapper<TransCustomerQueryParams> wrapper = null;
		try {
			wrapper = new RequestParamsWrapper<TransCustomerQueryParams>();
			if (wrapper == null) {
				System.out.println("解释请求参数出错！");
			}
			requestParams = wrapper.formParams(xml,TransCustomerQueryParams.class);
			if(requestParams==null){
				return  "服务异常，解释请求报文失败，对象为空！"; 
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
