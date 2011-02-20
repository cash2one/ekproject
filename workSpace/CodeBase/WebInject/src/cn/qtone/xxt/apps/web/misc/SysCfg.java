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

	public static String DB_POOL_NAME = "zjxxt";
	public static String COMPLAINT_CREATE_USERID = "1"; 
	
	public static String EMAIL_FROM_ADDRESS; // ������
	public static String EMAIL_TO_ADDRESS; // �ռ���
	public static String EMAIL_STMP_HOST; // ������
	public static String EMAIL_USER_NAME; //
	public static String EMAIL_USER_PWD;
	public static String EMAIL_AUTH; // is ssl ��֤��¼
	public static String EMAIL_TITLE_SET;
	
	public static String MISC_USER_NAME; //MISC ϵͳ�û���Ϣ
	public static String MISC_USER_PWD;
	
	
	public static int FETCH_DATA_SIZE=300;  //����ȡ������
	public static String DATAS_STORE_TABLE_NAME ="YW_COMPLAINT";
	
	
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
			EMAIL_AUTH = element!= null&&!Checker.isNull( element.getTextTrim())? element.getTextTrim() : "false";
			element = XmlHandler.getElement(doc, "email/email_title");
			EMAIL_TITLE_SET = element!= null&&!Checker.isNull( element.getTextTrim())? element.getTextTrim() : "УѶͨͶ�ߴ����ʼ�����";
			element = XmlHandler.getElement(doc, "DBPool");
			DB_POOL_NAME = element!= null&&!Checker.isNull( element.getTextTrim())? element.getTextTrim() : "zjxxt";
			element = XmlHandler.getElement(doc, "Complaint_CreaterId");
			COMPLAINT_CREATE_USERID = element!= null&&!Checker.isNull( element.getTextTrim())? element.getTextTrim() : "1";
			element = XmlHandler.getElement(doc, "fetch-datas-set/store_table");
			DATAS_STORE_TABLE_NAME = element!= null&&!Checker.isNull( element.getTextTrim())? element.getTextTrim() : "YW_COMPLAINT";
			
			element = XmlHandler.getElement(doc, "fetch-datas-set/username");
			MISC_USER_NAME = element!= null&&!Checker.isNull( element.getTextTrim())? element.getTextTrim() : "qtone";
			
			element = XmlHandler.getElement(doc, "fetch-datas-set/password");
			MISC_USER_PWD = element!= null&&!Checker.isNull( element.getTextTrim())? element.getTextTrim() : "qtone2010";
			
			try{
				element = XmlHandler.getElement(doc, "fetch-datas-set/page-size");
				FETCH_DATA_SIZE = element!= null&&!Checker.isNull( element.getTextTrim())? Integer.parseInt(element.getTextTrim()): 300;
			}catch(Exception e){
				FETCH_DATA_SIZE = 300;
			}
	}

	public static void main(String... str) {
		System.out.println(SysCfg.EMAIL_FROM_ADDRESS);
	}

}
