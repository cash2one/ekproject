package cn.elam.reptilerobot.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.ParagraphTag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import cn.elam.reptilerobot.core.inter.IFilter;
import cn.elam.reptilerobot.core.inter.IParserHandler;
import cn.elam.reptilerobot.utils.LoggerUtil;

/**
 * 
 * 页面内容分析工具
 * @author Ethan.Lam  
 * @createTime 2011-11-16
 *
 */
public class ParserHandler implements IParserHandler {

 
	   IFilter filterHandler = new FilterImp();
	
	  /**
	   * 分析页面数据
	   * @param preUrl
	   * @param urlConnectin
	   * @param encode
	   * @throws Exception
	   */
	  public void analyzeHTML(String preUrl,HttpURLConnection urlConnectin) throws Exception{
		    StringBuffer inputHTML = new StringBuffer();
		    InputStream is = urlConnectin.getInputStream();  
            BufferedReader br = new BufferedReader(new InputStreamReader(is));  
            String readLine = null;  
            while((readLine =br.readLine()) != null){  
            	inputHTML.append(readLine);  
            }  
            is.close();  
            br.close();  
            urlConnectin.disconnect();
//            LoggerUtil.info("ParserHandler-fetchHtmlContent","爬虫得带到的 页面内容"+inputHTML);
		    Parser parser = new Parser();
		    parser.setInputHTML(inputHTML.toString());
	        dealLinkNodes(parser,preUrl);
	        parser.setInputHTML(inputHTML.toString());
	        fetchHtmlContent(parser);
	  }
	  
	  
	  
	  /**
	   * 
	   * 处理目标 超链接节点
	   * @param parser
	   * @param preUrl
	   * @throws Exception
	   */
	  public void dealLinkNodes(Parser parser,String preUrl){
		   try{
			   NodeFilter filter = new AndFilter(new TagNameFilter("a"),new HasAttributeFilter("target","_blank"));
		        NodeList nodeList = parser.parse(filter);
		        LoggerUtil.info("ParserHandler","爬虫得到的 <a> NodeList："+(nodeList!=null?nodeList.size():0));
		        NodeIterator it = nodeList.elements();
		        while(it.hasMoreNodes()){
		            Node node = it.nextNode();
		            if(node instanceof LinkTag){
		            	 if(!filterHandler.isLinkTagFilter(((LinkTag)node))){
		            	    LoggerUtil.debug("ParserHandler  ",((LinkTag)node).getLink(),((LinkTag)node).getLinkText());
		            	    CrawlQueue.getQueueManager().newNode(((LinkTag)node).getLinkText(),((LinkTag)node).getLink(),preUrl);
		            	 }
		            }
		        }
		   }catch(Exception e){
			   
		   }
	  }
	  
	  
	  
	  public void fetchHtmlContent(Parser parser) throws ParserException{
		    NodeFilter filter = new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class","blkContainerSblkCon"));
	        NodeList nodeList = parser.parse(filter);
	        NodeIterator it = nodeList.elements();
	        Div div = null;
	        while(it.hasMoreNodes()){
	        	div  = (Div)it.nextNode();
	        	NodeList nl = div.getChildren();
	        	if(nl==null)
	        		continue;
	        	NodeIterator sub = nl.elements();
	        	while(sub.hasMoreNodes()){
	        		 Node t = sub.nextNode();
	        		 if(t instanceof ParagraphTag)
	        		    LoggerUtil.info("fetchHtmlContent  ",((ParagraphTag) t).getStringText());
	        	}
	        }
		  
	  }
	  
	  
}
