package cn.qtone.xxt.csop.webservices.util;

import cn.qtone.xxt.csop.webservices.bean.RequestParams;
import cn.qtone.xxt.csop.webservices.bean.TransCustomerQueryParams;

public class ReadRequestXml {

	public RequestParams formParams(String xml, int type) {
		if (type == 0)
			return transCustomerQueryParams(xml);
		return null;
	}

	private RequestParams transCustomerQueryParams(String xml) {
		
		return null;
	}
	
	

}
