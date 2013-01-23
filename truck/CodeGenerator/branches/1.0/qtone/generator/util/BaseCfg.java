package qtone.generator.util;

import org.apache.log4j.xml.DOMConfigurator;
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
public class BaseCfg {

	public static String APP_CONTEXT = "code";

//	public static String CFG_PATH="D:/codegen/";
	
	public static String CFG_PATH="E:\\elam\\my_jar_project\\CodeGenerator\\WebRoot\\";
//	public static String CFG_PATH="D:\\tomcat6x\\webapps\\ROOT\\";

	
//	public static String CFG_PATH="D:/Workspaces/google/ekproject/truck/CodeGenerator/WebRoot/";
	
	public static String AREA_ABB="daoAbb";
	
	public static String WWW="http://zsqtsm.vicp.net:43980/";
	
//	public static String WWW="http://218.240.43.237:3366/";
	
	
	public static String WWW_LOCALHOST="http://192.168.4.39:8888/";
 

	public static String basePackageName = "newm.";
	
	static {
		DOMConfigurator.configure("E:/elam/my_jar_project/CodeGenerator/branches/1.0/log4j.xml");
		
	}
     
}
