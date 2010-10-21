package cn.qtone.xxt.csop.webservices.bean;

/**
 * 
 * 服务应答 封装类 负责封装对应格式的XML
 * 
 * @author linhansheng
 * 
 */
public class ServiceResponse {

	static String XML_HEADER ="<?xml version=1.0 encoding=UTF-8?>";
	
	public String toXML() {
		return XML_HEADER+"<content>请求服务成功服务!</content>";
	}
	
	
}
