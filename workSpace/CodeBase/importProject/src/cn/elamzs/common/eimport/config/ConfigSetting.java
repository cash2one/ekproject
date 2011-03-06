package cn.elamzs.common.eimport.config;

import java.io.File;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.Element;

import cn.elam.util.common.Checker;
import cn.elam.util.file.xml.XmlHandler;

/**
 * 应用配置
 * 
 * @author Ethan.Lam 2011-2-14
 * 
 */
public class ConfigSetting {

	// 导入任务完成后生成的 结果文档目录
	public static String DIR_IMPORT_RESULT = "d:/importfile/result/";

	// 导入任务的源 结果文档目录
	public static String DIR_IMPORT_SRC = "d:/importfile/src/";

	// 导入任务对应的模版管理目录
	public static String DIR_IMPORT_TEMPLATE = "d:/importfile/template/";

	// 数据源
	public static String PERSIST_POOL_NAME = "impDB";

	static {
		try {
			
			Document doc = XmlHandler.loadXML(DOMConfigurator.configureXml);

			Element element = XmlHandler.getElement(doc, "DBPool");
			PERSIST_POOL_NAME = element != null
					&& !Checker.isNull(element.getTextTrim()) ? element
					.getTextTrim() : "impDB";
			;

			element = XmlHandler.getElement(doc, "DIR_IMPORT_SRC");
			DIR_IMPORT_SRC = element != null
					&& !Checker.isNull(element.getTextTrim()) ? element
					.getTextTrim() : "d:/importfile/src/";
			;

			element = XmlHandler.getElement(doc, "DIR_IMPORT_RESULT");
			DIR_IMPORT_RESULT = element != null
					&& !Checker.isNull(element.getTextTrim()) ? element
					.getTextTrim() : "d:/importfile/result/";
			;

			element = XmlHandler.getElement(doc, "DIR_IMPORT_TEMPLATE");
			DIR_IMPORT_TEMPLATE = element != null
					&& !Checker.isNull(element.getTextTrim()) ? element
					.getTextTrim() : "d:/importfile/template/";
			;

			File _dir = new File(DIR_IMPORT_RESULT);
			if (!_dir.exists())
				_dir.mkdirs();

			_dir = new File(DIR_IMPORT_SRC);
			if (!_dir.exists())
				_dir.mkdirs();

			_dir = new File(DIR_IMPORT_TEMPLATE);
			if (!_dir.exists())
				_dir.mkdirs();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
