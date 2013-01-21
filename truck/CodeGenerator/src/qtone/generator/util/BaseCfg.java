package qtone.generator.util;

import org.apache.log4j.xml.DOMConfigurator;
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
public class BaseCfg {

	public static String APP_CONTEXT = "code";

//	public static String CFG_PATH="E:/elam/my_jar_project/CodeGenerator/WebRoot/";
	
//	public static String CFG_PATH="D:/Workspaces/google/ekproject/truck/CodeGenerator/WebRoot/";
	
	public static String CFG_PATH="/home/zjxxt/codeGenService/webRoot/";
	
	public static String CFG_MODEL_TYPE = "codeTemplators"; 
	
	public static String AREA_ABB="daoAbb";
	
//	public static String WWW="http://zsqtsm.vicp.net:43980/";
	
	public static String WWW="http://61.142.114.242:8888/";
	
	public static String WWW_LOCALHOST="http://61.142.114.242:8888/";
 

	public static String basePackageName = "qtone.xxt.";
	
	static {
//		DOMConfigurator.configure("D:/Workspaces/google/ekproject/truck/CodeGenerator/src/log4j.xml");
		DOMConfigurator.configure("/home/zjxxt/codeGenService/webRoot/classes/log4j.xml");
	}
     
}
