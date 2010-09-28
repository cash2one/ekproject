package cn.elam.util.file.xml;

import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlHandler {

	public static Document loadXML(String filePath) {
		SAXReader loader = new SAXReader();
		try {
			return loader.read(filePath);
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Document loadXML(InputStream file) {
		SAXReader loader = new SAXReader();
		try {
			return loader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<Element> getElements(Document doc, String nodePath) {
		if (doc == null)
			return null;
		Element root = doc.getRootElement();
		if (root != null)
			return root.selectNodes(nodePath);
		else
			return null;
	}
	
	public static  Element getElement(Document doc, String nodePath) {
		if (doc == null)
			return null;
		Element root = doc.getRootElement();
		if (root != null)
			return (Element) root.selectSingleNode(nodePath);
		else
			return null;
	}

}
