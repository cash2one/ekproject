package cn.elam.reptilerobot.core;

import java.net.HttpURLConnection;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;

import cn.elam.reptilerobot.utils.LoggerUtil;

/**
 * 
 * 页面内容分析工具
 * @author Ethan.Lam  
 * @createTime 2011-11-16
 *
 */
public class ParserHandler {

 
	  public void analyzeHTML(String preUrl,HttpURLConnection urlConnectin,String encode) throws Exception{
		   Parser parser = new Parser(urlConnectin);
	        parser.setEncoding(encode);
	        NodeFilter filter = new AndFilter(new TagNameFilter("a"),new HasAttributeFilter("target","_blank"));
	        NodeList nodeList = parser.parse(filter);
	        LoggerUtil.info("ParserHandler","爬虫得到的 <a> NodeList："+(nodeList!=null?nodeList.size():0));
	        NodeIterator it = nodeList.elements();
	        while(it.hasMoreNodes()){
	            Node node = it.nextNode();
	            if(node instanceof LinkTag){
	            	 if(((LinkTag)node).getLink().indexOf("blog")>=0){
	            	    LoggerUtil.info("ParserHandler  ",((LinkTag)node).getLink(),((LinkTag)node).getLinkText());
	            	    CrawlQueue.getQueueManager().newNode(((LinkTag)node).getLinkText(),((LinkTag)node).getLink(),preUrl);
	            	 }
	            }
	        }
	    }
	  
	  
}
