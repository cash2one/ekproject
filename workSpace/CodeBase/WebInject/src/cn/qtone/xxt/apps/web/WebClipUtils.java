package cn.qtone.xxt.apps.web;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
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

import cn.qtone.xxt.apps.web.misc.ComplaintItem;

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
		
		 String LOGON_SITE = "http://admin.zj.monternet.com:8080/sp/index.jsp";  
	     int LOGON_PORT = 8080;
		 String loginReq = "http://admin.zj.monternet.com:8080/sp/SPLogin";
		 String UserAgent = "Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)";
		 String hrefUrl ="/sp/index.jsp";
			
		 HttpClient client = HttpClientUtil.newHttpClient(LOGON_SITE,LOGON_PORT);
		 PostMethod post = HttpClientUtil.newPostMethod(loginReq,UserAgent,hrefUrl,new String[][]{{"selectAccount","SPPREREG"},{"USER","qtone"},{"PASSWORD","qtone2010"}});
		
		 client.executeMethod(post);  
		 Cookie[] cookies  = HttpClientUtil.addCookies(client);
		 post.releaseConnection();
			
		 String dataUrl="http://admin.zj.monternet.com:8080/sp/indict/queryIndictICD.jsp?subsId=&userName=&status=1&queryTime=0&fromDate=2010-02-01&toDate=2011-02-15&pageNum=1&currentPageNo=1&pageSize=100&navigatePage_toPageSize=100&navigatePage_toPageNum=1";
		 GetMethod get = HttpClientUtil.newGetMethod(dataUrl,cookies);
		 client.executeMethod(get);
		 String targetPage = get.getResponseBodyAsString();
		 
		 HtmlCleaner cleaner = new HtmlCleaner();
		 TagNode node = cleaner.clean(targetPage);
		 Object[] trs = node.getElementsByName("tr", true); 
		 
		 if(trs.length!=0){
			 TagNode trNode = null;
			 for(Object tr:trs){
					trNode = (TagNode) tr;
				    if("11".equals(trNode.getAttributeByName("height"))){
//				    	 for(TagNode td : trNode.getChildTags())
//		                    System.out.print(td.getText().toString()+",");
//				    	 System.out.println();
				    	TagNode[] tdNodes = trNode.getElementsByName("td",true);
				    	 for(TagNode td : tdNodes)
			                    System.out.print(td.getText().toString()+"-,");
				    	 System.out.println();
				    }
				   
			 }
		 }
		 
		 
		 CleanerProperties props = cleaner.getProperties(); 
         props.setUseCdataForScriptAndStyle(true); 
         props.setRecognizeUnicodeChars(true); 
         props.setUseEmptyElementTags(true); 
         props.setAdvancedXmlEscape(true); 
         props.setTranslateSpecialEntities(true); 
         props.setBooleanAttributeValues("empty"); 
         new PrettyXmlSerializer(props).writeXmlToFile(node, "d:test.xml","utf-8"); 
		 
		 
	}
}
