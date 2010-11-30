package cn.qtone.xxt.logonmock;

import org.dom4j.Document;
import org.dom4j.Element;

import cn.qtone.xxt.base.util.file.xml.XmlHandler;

public class Config {

	public static final String CONFIG_FILE = "configs/App_Config.xml";
	public static String POOL_NAME = "xxt";
	final static String PARAMSFORAMT_ATTRUBUTE = "paramsFormat";
	final static String REQUERST_URL_ERROR_FLAG ="req_error_flag";
	public static int RECORDS_BATCH_LOADNUM = 100;
	public static int POST_THREAD_NUM = 2;
	public static String TEMP_TABLE = "synlesson_logon_temp";
	public static boolean ISDEUG = true;

	
	// public static String SYN_LESSON_URL_HT =
	// "http://ketang.ldcstudy.com/login/synlogin.form?info=";
	// public static String SYN_LESSON_URL_KL =
	// "http://gd.91118.com/Interface/logincheck.aspx?gd=Yes&username=";
	// public static String SYN_LESSON_URL_ZX =
	// "http://family.chinaeduonline.cn/login/synlogin.form?info=";
	// public static String SYN_LESSON_URL_WW =
	// "http://wawa.chinaeduonline.cn/login/synlogin.form?info=";

	public static String SYN_LESSON_URL_HT = "http://ketang.ldcstudy.com/login/synlogin.form?info=";
	public static String SYN_LESSON_URL_HT_PARAMSFORMAT = "";
	public static String SYN_LESSON_URL_HT_REQ_ERROR ="";
	public static String SYN_LESSON_URL_KL = "http://gd.91118.com/Interface/logincheck.aspx?gd=Yes&username=";
	public static String SYN_LESSON_URL_KL_PARAMSFORMAT = "";
	public static String SYN_LESSON_URL_KL_REQ_ERROR ="";
	public static String SYN_LESSON_URL_ZX = "http://family.chinaeduonline.cn/login/synlogin.form?info=";
	public static String SYN_LESSON_URL_ZX_PARAMSFORMAT = "";
	public static String SYN_LESSON_URL_ZX_REQ_ERROR ="";
	public static String SYN_LESSON_URL_WW = "http://wawa.chinaeduonline.cn/login/synlogin.form?info=";
	public static String SYN_LESSON_URL_WW_PARAMSFORMAT = "";
	public static String SYN_LESSON_URL_WW_REQ_ERROR ="";

	
	static {
			Document doc = XmlHandler.loadXML(CONFIG_FILE);
			
			Element element = XmlHandler.getElement(doc, "Logon-Module");
			if (!isNull(element)){
				RECORDS_BATCH_LOADNUM = Integer.parseInt(element.attributeValue("records_batch_loadnum"));
				POST_THREAD_NUM = Integer.parseInt(element.attributeValue("post_threads"));
			}
	
			element = XmlHandler.getElement(doc, "Logon-Module/TEMP-USER-TABLE");
				if (!isNull(element)){
					TEMP_TABLE = element.getTextTrim();
		    }
			
			element = XmlHandler.getElement(doc, "Logon-Module/SYN_LESSON_URL_HT");
			if (!isNull(element)) {
				SYN_LESSON_URL_HT = element.getTextTrim();
				SYN_LESSON_URL_HT_PARAMSFORMAT = element
						.attributeValue(PARAMSFORAMT_ATTRUBUTE).trim();
				SYN_LESSON_URL_HT_REQ_ERROR = element
				.attributeValue(REQUERST_URL_ERROR_FLAG).trim();
			}
			
			element = XmlHandler.getElement(doc, "Logon-Module/SYN_LESSON_URL_KL");
			if (!isNull(element)) {
				SYN_LESSON_URL_KL = element.getTextTrim();
				SYN_LESSON_URL_KL_PARAMSFORMAT = element
						.attributeValue(PARAMSFORAMT_ATTRUBUTE).trim();
				SYN_LESSON_URL_KL_REQ_ERROR = element
				.attributeValue(REQUERST_URL_ERROR_FLAG).trim();
			}
			
			element = XmlHandler.getElement(doc, "Logon-Module/SYN_LESSON_URL_ZX");
			if (!isNull(element)) {
				SYN_LESSON_URL_ZX = element.getTextTrim();
				SYN_LESSON_URL_ZX_PARAMSFORMAT = element
						.attributeValue(PARAMSFORAMT_ATTRUBUTE).trim();
				SYN_LESSON_URL_ZX_REQ_ERROR = element
				.attributeValue(REQUERST_URL_ERROR_FLAG).trim();
			}
			
			element = XmlHandler.getElement(doc, "Logon-Module/SYN_LESSON_URL_WW");
			if (!isNull(element)) {
				SYN_LESSON_URL_WW = element.getTextTrim();
				SYN_LESSON_URL_WW_PARAMSFORMAT = element
						.attributeValue(PARAMSFORAMT_ATTRUBUTE).trim();
				SYN_LESSON_URL_WW_REQ_ERROR = element
				.attributeValue(REQUERST_URL_ERROR_FLAG).trim();
			}
			
			doc = null;
	}

	public static boolean isNull(Object object) {
		boolean isNull = object == null ? true : false;
		object = null;
		return isNull;
	}

}
