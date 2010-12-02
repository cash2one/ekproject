package cn.qtone.xxt.logonmock;

import org.apache.log4j.xml.DOMConfigurator;
import org.dom4j.Document;
import org.dom4j.Element;

import cn.qtone.xxt.base.util.Checker;
import cn.qtone.xxt.base.util.file.xml.XmlHandler;

public class Config {

	public static String POOL_NAME = "xxt";
	final static  String CONFIG_FILE ="configs/App_Config.xml";
	final static  String CONFIG_FILE_LOG4J ="configs/log4j.xml";
	final  static String PARAMSFORAMT_ATTRUBUTE = "paramsFormat";
	final  static String REQUERST_URL_ERROR_FLAG ="req_error_flag";
	public static int RECORDS_BATCH_LOADNUM = 100;
	public static int POST_THREAD_NUM = 2;
	public static String TEMP_TABLE = "synlesson_logon_temp";
	public static boolean ISDEUG = false;
	public static boolean URL_FORMATE_STATE = false;
	public static String TARGET_AREA = "CS";
	public static boolean IS_CALL_PROCEDURE = false;
	public static int THREAD_CHECK_TIMER = 30;
	
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
	public static boolean HT_IS_RUN = false;
	
	public static String SYN_LESSON_URL_KL = "http://gd.91118.com/Interface/logincheck.aspx?gd=Yes&username=";
	public static String SYN_LESSON_URL_KL_PARAMSFORMAT = "";
	public static String SYN_LESSON_URL_KL_REQ_ERROR ="";
	public static boolean KL_IS_RUN = false;
	
	public static String SYN_LESSON_URL_ZX = "http://family.chinaeduonline.cn/login/synlogin.form?info=";
	public static String SYN_LESSON_URL_ZX_PARAMSFORMAT = "";
	public static String SYN_LESSON_URL_ZX_REQ_ERROR ="";
	public static boolean ZX_IS_RUN = false;
	
	public static String SYN_LESSON_URL_WW = "http://wawa.chinaeduonline.cn/login/synlogin.form?info=";
	public static String SYN_LESSON_URL_WW_PARAMSFORMAT = "";
	public static String SYN_LESSON_URL_WW_REQ_ERROR ="";
	public static boolean WW_IS_RUN = false;
	
	static {                 
			Document doc = XmlHandler.loadXML(CONFIG_FILE);
			DOMConfigurator.configure(CONFIG_FILE_LOG4J);
			
			Element element = XmlHandler.getElement(doc, "Logon-Module");
			if (!isNull(element)){
				RECORDS_BATCH_LOADNUM = Integer.parseInt(element.attributeValue("records_batch_loadnum"));
				POST_THREAD_NUM = Integer.parseInt(element.attributeValue("post_threads"));
				if(!Checker.isNull(element.attributeValue("debug"))&&"true".equals(element.attributeValue("debug").toLowerCase()))
				   ISDEUG = true; 
				if(!Checker.isNull(element.attributeValue("paramsFormat"))&&"true".equals(element.attributeValue("paramsFormat").toLowerCase()))
					URL_FORMATE_STATE = true; 
				
				if(!Checker.isNull(element.attributeValue("check_timer")))
					THREAD_CHECK_TIMER = Integer.parseInt(element.attributeValue("check_timer"));; 
			}
	
			element = XmlHandler.getElement(doc, "Logon-Module/IS_CALL_PROCEDURE");
			if (!isNull(element)){
				if(!Checker.isNull(element.getTextTrim())&&"true".equals(element.getTextTrim().toLowerCase()))
					IS_CALL_PROCEDURE = true; 
	        }
			
			element = XmlHandler.getElement(doc, "Logon-Module/TARGER-AREA");
			if (!isNull(element)){
				TARGET_AREA = element.getTextTrim();
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
				
				if(!Checker.isNull(element.attributeValue("run"))&&"true".equals(element.attributeValue("run").toLowerCase()))
					HT_IS_RUN = true; 
			}
			
			element = XmlHandler.getElement(doc, "Logon-Module/SYN_LESSON_URL_KL");
			if (!isNull(element)) {
				SYN_LESSON_URL_KL = element.getTextTrim();
				SYN_LESSON_URL_KL_PARAMSFORMAT = element
						.attributeValue(PARAMSFORAMT_ATTRUBUTE).trim();
				SYN_LESSON_URL_KL_REQ_ERROR = element
				.attributeValue(REQUERST_URL_ERROR_FLAG).trim();
				
				if(!Checker.isNull(element.attributeValue("run"))&&"true".equals(element.attributeValue("run").toLowerCase()))
					KL_IS_RUN = true; 
			}
			
			element = XmlHandler.getElement(doc, "Logon-Module/SYN_LESSON_URL_ZX");
			if (!isNull(element)) {
				SYN_LESSON_URL_ZX = element.getTextTrim();
				SYN_LESSON_URL_ZX_PARAMSFORMAT = element
						.attributeValue(PARAMSFORAMT_ATTRUBUTE).trim();
				SYN_LESSON_URL_ZX_REQ_ERROR = element
				.attributeValue(REQUERST_URL_ERROR_FLAG).trim();
				
				if(!Checker.isNull(element.attributeValue("run"))&&"true".equals(element.attributeValue("run").toLowerCase()))
					ZX_IS_RUN = true; 
			}
			
			element = XmlHandler.getElement(doc, "Logon-Module/SYN_LESSON_URL_WW");
			if (!isNull(element)) {
				SYN_LESSON_URL_WW = element.getTextTrim();
				SYN_LESSON_URL_WW_PARAMSFORMAT = element
						.attributeValue(PARAMSFORAMT_ATTRUBUTE).trim();
				SYN_LESSON_URL_WW_REQ_ERROR = element
				.attributeValue(REQUERST_URL_ERROR_FLAG).trim();
				
				if(!Checker.isNull(element.attributeValue("run"))&&"true".equals(element.attributeValue("run").toLowerCase()))
					WW_IS_RUN = true; 
			}
			
			doc = null;
	}

	public static boolean isNull(Object object) {
		boolean isNull = object == null ? true : false;
		object = null;
		return isNull;
	}

}
