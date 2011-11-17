package cn.elam.reptilerobot.core.inter;

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
import org.htmlparser.util.ParserException;

import cn.elam.reptilerobot.utils.LoggerUtil;

/**
 * 
 * 页面内容分析工具
 * @author Ethan.Lam  
 * @createTime 2011-11-16
 *
 */
public interface IParserHandler {

 
	
	  /**
	   * 分析页面数据
	   * @param preUrl
	   * @param urlConnectin
	   * @throws Exception
	   */
	  public void analyzeHTML(String preUrl,HttpURLConnection urlConnectin) throws Exception;
	  
	  
	  
	  /**
	   * 
	   * 处理目标 超链接节点
	   * @param parser
	   * @param preUrl
	   * @throws Exception
	   */
	  public void dealLinkNodes(Parser parser,String preUrl) throws Exception;
	  
	  
	  
	  
       
	  
	  
	  
}
