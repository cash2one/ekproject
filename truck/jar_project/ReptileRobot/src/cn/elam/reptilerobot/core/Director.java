package cn.elam.reptilerobot.core;

import cn.elam.reptilerobot.base.Node;

/**
 * 
 * @author Ethan.Lam  
 * @createTime 2011-11-16
 *
 */
public class Director {

	
	  public Director(){
		  
	  } 
	
	  public void start(){
		  
		  
	  }
	
	  
	  public static void main(String...args){
		  
		  CrawlQueue.getQueueManager().newNode("ÐÂÀË", "http://www.sina.com.cn", null);
		  Crawl crawl = new Crawl("Tester");
          crawl.start();		  
		  
	  }
	
	 
	
}
