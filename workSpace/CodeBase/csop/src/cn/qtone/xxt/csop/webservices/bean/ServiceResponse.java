package cn.qtone.xxt.csop.webservices.bean;

/**
 * 
 * ����Ӧ�� ��װ�� �����װ��Ӧ��ʽ��XML
 * 
 * @author linhansheng
 * 
 */
public class ServiceResponse {

	static String XML_HEADER ="<?xml version=1.0 encoding=UTF-8?>";
	
	public String toXML() {
		return XML_HEADER+"<content>�������ɹ�����!</content>";
	}
	
	
}
