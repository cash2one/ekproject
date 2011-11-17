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
 * ҳ�����ݷ�������
 * @author Ethan.Lam  
 * @createTime 2011-11-16
 *
 */
public interface IParserHandler {

 
	
	  /**
	   * ����ҳ������
	   * @param preUrl
	   * @param urlConnectin
	   * @throws Exception
	   */
	  public void analyzeHTML(String preUrl,HttpURLConnection urlConnectin) throws Exception;
	  
	  
	  
	  /**
	   * 
	   * ����Ŀ�� �����ӽڵ�
	   * @param parser
	   * @param preUrl
	   * @throws Exception
	   */
	  public void dealLinkNodes(Parser parser,String preUrl) throws Exception;
	  
	  
	  
	  
       
	  
	  
	  
}
