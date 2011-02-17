package cn.qtone.xxt.apps.web.misc;

import org.apache.struts.util.GenericDataSource;
import org.dom4j.Document;
import org.dom4j.Element;

import cn.elam.util.common.Checker;
import cn.elam.util.common.Trans;
import cn.elam.util.file.xml.XmlHandler;

/**
 * 
 * Ӧ�û�������
 * 
 * @author Ethan.Lam 2011-2-17
 * 
 */
public class SysCfg {

	public static String fromAddress; // ������
	public static String toAddress; // �ռ���
	public static String stmpHost; // ������
	public static String userName; //
	public static String password;
	public static String auth; // is ssl ��֤��¼
	public static String email_title;
	
	static {
		Document doc = XmlHandler.loadXML("configs/sysCfg.xml");
		Element element = XmlHandler.getElement(doc, "email/fromAddress");
		fromAddress = element.getTextTrim();
		element = XmlHandler.getElement(doc, "email/toAddress");
		toAddress = element.getTextTrim();
		element = XmlHandler.getElement(doc, "email/stmpHost");
		stmpHost = element.getTextTrim();
		element = XmlHandler.getElement(doc, "email/userName");
		userName = element.getTextTrim();
		element = XmlHandler.getElement(doc, "email/password");
		password = element.getTextTrim();
		element = XmlHandler.getElement(doc, "email/auth");
		auth = element != null ? element.getTextTrim() : "false";
		element = XmlHandler.getElement(doc, "email/email_title");
		email_title = element != null ? element.getTextTrim() : "УѶͨͶ�ߴ����ʼ�����";
	}

	public static void main(String... str) {
		System.out.println(SysCfg.fromAddress);
	}

}
