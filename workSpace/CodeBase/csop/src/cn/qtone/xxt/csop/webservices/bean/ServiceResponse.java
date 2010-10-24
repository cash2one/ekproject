package cn.qtone.xxt.csop.webservices.bean;

/**
 * 
 * ����Ӧ�� ��װ�� �����װ��Ӧ��ʽ��XML
 * 
 * @author linhansheng
 * 
 */
public class ServiceResponse {

	static String XML_HEADER = "<?xml version=1.0 encoding=UTF-8?>";
	static String ROOT_ELEMENT = "Response";

	String retcode; // 000 �ɹ�
	String retmsg; // ������Ϣ
	String result; // Ӧ������

	public String getRetcode() {
		return retcode;
	}

	public void setRetcode(String retcode) {
		this.retcode = retcode;
	}

	public String getRetmsg() {
		return retmsg;
	}

	public void setRetmsg(String retmsg) {
		this.retmsg = retmsg;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * xml ��װ
	 */
	public String toString() {
		StringBuffer xml = new StringBuffer(XML_HEADER);
		xml.append("<" + ROOT_ELEMENT + ">");
		xml.append("<retcode>").append(getRetcode()).append("</retcode>");
		xml.append("<Retmsg>").append(getRetmsg()).append("</Retmsg>");
		xml.append("<Result>").append(getResult()).append("</Result>");
		xml.append("</" + ROOT_ELEMENT + ">");
		return xml.toString();
	}

}
