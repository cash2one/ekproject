package qtone.generator.util;

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

	public static String DB_POOL_NAME = "zjxxt";
	public static String COMPLAINT_CREATE_USERID = "1";

	static {
		
		Document doc = XmlHandler.loadXML("configs/sysCfg.xml");
		Element element = XmlHandler.getElement(doc, "APP_DEBUG");
	}
   
	public void test(){
		Document doc = XmlHandler.loadXML("configs/sysCfg.xml");
		Element element = XmlHandler.getElement(doc, "APP_DEBUG");
	}

}
