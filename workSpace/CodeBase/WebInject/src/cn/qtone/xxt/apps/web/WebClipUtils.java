package cn.qtone.xxt.apps.web;

import org.htmlcleaner.*;

import java.net.*;
import java.io.*;
import java.util.*;

import org.jdom.*; //import org.jdom.output.*;  
import org.jdom.contrib.helpers.XPathHelper;
import org.jdom.filter.Filter;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

public class WebClipUtils {

	public static Document getDocumentByURL(String url, String charset)
			throws MalformedURLException, IOException {
		HtmlCleaner htmlCleaner = new HtmlCleaner();
		CleanerProperties props = htmlCleaner.getProperties();
		TagNode node = htmlCleaner.clean(new URL(url), charset);
		JDomSerializer jdomSerializer = new JDomSerializer(props, true);
		Document doc = jdomSerializer.createJDom(node);
		return doc;
	}

	public static List<Element> getElementsByTagName(Document doc,
			String tagName) {
		List<Element> eleList = new ArrayList<Element>();
		buildList(doc.getRootElement(), tagName, eleList);
		return eleList;
	}

	private static void buildList(Element rootEle, String tagName,
			List<Element> eleList) {
		if (rootEle.getName().equals(tagName)) {
			eleList.add(rootEle);
		}
		List list = rootEle.getChildren();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Element ele = (Element) iter.next();
			buildList(ele, tagName, eleList);
		}
	}

	public static void printElement(Element ele) throws IOException {
		XMLOutputter outputer = new XMLOutputter();
		Format format = outputer.getFormat();
		format.setEncoding("GB2312");
		outputer.setFormat(format);
		outputer.output(ele, System.out);
	}

	
	public static void main(String[] args) throws Exception {
		HtmlCleaner htmlCleaner = new HtmlCleaner();

		CleanerProperties props = htmlCleaner.getProperties();

		//TagNode node = htmlCleaner.clean(new URL("http://www.baidu.com"));
		TagNode node = htmlCleaner.clean(new URL("http://www.huanqiu.com"),
				"UTF-8");
		
		
//		 XmlSerializer xmlSerializer = new PrettyXmlSerializer(props);
//		 StringWriter writer = new StringWriter();
//		 xmlSerializer.writeXml(node, writer, "GB2312");
//		 System.out.println(writer.toString());


		JDomSerializer jdomSerializer = new JDomSerializer(props, true);
		Document doc = jdomSerializer.createJDom(node);

		Element rootEle = doc.getRootElement();

		System.out.println(XPathHelper.getPathString(rootEle));
		final String tagName = "div";
		List list = getElementsByTagName(doc, "div");
		System.out.println(list.size());
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			Element ele = (Element) iter.next();
			System.out.println();
			System.out.println("*****************************************");
			System.out.println(XPathHelper.getPathString(ele));
			System.out.println("*****************************************");
			printElement(ele);
		}

	}
}
