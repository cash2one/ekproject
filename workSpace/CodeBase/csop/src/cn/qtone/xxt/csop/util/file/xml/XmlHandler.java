package cn.qtone.xxt.csop.util.file.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlHandler {

	public static Document loadXML(String filePath) {
		SAXReader loader = new SAXReader();
		try {
			return loader.read(filePath);
		} catch (DocumentException e) {
	    		
			String currentJarPath = XmlHandler.class.getProtectionDomain().getCodeSource().getLocation().getFile();
			JarFile currentJar;
			try {
				currentJar = new JarFile(currentJarPath);
				JarEntry dbEntry = currentJar.getJarEntry(filePath); 
				InputStream in = currentJar.getInputStream(dbEntry);	
				return loader.read(in);
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
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
	
	public static Document createDocument(String content){
		try {
			return DocumentHelper.parseText(content);
		} catch (Exception e) {
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
