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

	public static String DB_POOL_NAME = "zjxxt";
	public static String COMPLAINT_CREATE_USERID = "1"; 
	
	public static String EMAIL_FROM_ADDRESS; // 发件人
	public static String EMAIL_TO_ADDRESS; // 收件人
	public static String EMAIL_STMP_HOST; // 发信箱
	public static String EMAIL_USER_NAME; //
	public static String EMAIL_USER_PWD;
	public static String EMAIL_AUTH; // is ssl 验证登录
	public static String EMAIL_TITLE_SET;
	
	static {
			Document doc = XmlHandler.loadXML("configs/sysCfg.xml");
			Element element = XmlHandler.getElement(doc, "email/fromAddress");
			EMAIL_FROM_ADDRESS = element.getTextTrim();
			element = XmlHandler.getElement(doc, "email/toAddress");
			EMAIL_TO_ADDRESS = element.getTextTrim();
			element = XmlHandler.getElement(doc, "email/stmpHost");
			EMAIL_STMP_HOST = element.getTextTrim();
			element = XmlHandler.getElement(doc, "email/userName");
			EMAIL_USER_NAME = element.getTextTrim();
			element = XmlHandler.getElement(doc, "email/password");
			EMAIL_USER_PWD = element.getTextTrim();
			element = XmlHandler.getElement(doc, "email/auth");
			EMAIL_AUTH = element != null ? element.getTextTrim() : "false";
			element = XmlHandler.getElement(doc, "email/email_title");
			EMAIL_TITLE_SET = element != null ? element.getTextTrim() : "校讯通投诉处理邮件提醒";
			element = XmlHandler.getElement(doc, "DBPool");
			DB_POOL_NAME = element != null ? element.getTextTrim() : "zjxxt";
			element = XmlHandler.getElement(doc, "Complaint_CreaterId");
			COMPLAINT_CREATE_USERID = element != null ? element.getTextTrim() : "1";
	}

	public static void main(String... str) {
		System.out.println(SysCfg.EMAIL_FROM_ADDRESS);
	}

}
