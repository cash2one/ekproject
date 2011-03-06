package cn.elamzs.common.eimport.config;

import java.io.File;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.Element;

import cn.elam.util.common.Checker;
import cn.elam.util.file.xml.XmlHandler;

/**
 * Ӧ������
 * 
 * @author Ethan.Lam 2011-2-14
 * 
 */
public class ConfigSetting {

	// ����������ɺ����ɵ� ����ĵ�Ŀ¼
	public static String DIR_IMPORT_RESULT = "d:/importfile/result/";

	// ���������Դ ����ĵ�Ŀ¼
	public static String DIR_IMPORT_SRC = "d:/importfile/src/";

	// ���������Ӧ��ģ�����Ŀ¼
	public static String DIR_IMPORT_TEMPLATE = "d:/importfile/template/";

	// ����Դ
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
