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

public class ParserHandler {

	
	  private void analyzeHTML(HttpURLConnection urlConnectin,String encode) throws Exception{
		   Parser parser = new Parser(urlConnectin);
	        parser.setEncoding(encode);
	        NodeFilter filter = new AndFilter(new TagNameFilter("a"),new HasAttributeFilter("class","TDcenter"));
	        NodeList nodeList = parser.parse(filter);
	        NodeIterator it = nodeList.elements();
	        while(it.hasMoreNodes()){
	            Node node = it.nextNode();
	        }
	    }
	  
	  
}
