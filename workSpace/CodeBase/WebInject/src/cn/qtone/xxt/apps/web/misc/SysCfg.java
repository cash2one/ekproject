package cn.qtone.xxt.apps.web.misc;

import org.apache.struts.util.GenericDataSource;
import org.dom4j.Document;
import org.dom4j.Element;

import cn.elam.util.common.Checker;
import cn.elam.util.common.Trans;
import cn.elam.util.file.xml.XmlHandler;

/**
 * 
 * 应用基本配置
 * 
 * @author Ethan.Lam 2011-2-17
 * 
 */
public class SysCfg {

	public static String fromAddress; // 发件人
	public static String toAddress; // 收件人
	public static String stmpHost; // 发信箱
	public static String userName; //
	public static String password;
	public static String auth; // is ssl 验证登录
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
		email_title = element != null ? element.getTextTrim() : "校讯通投诉处理邮件提醒";
	}

	public static void main(String... str) {
		System.out.println(SysCfg.fromAddress);
	}

}
