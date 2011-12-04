package cn.elam.reptilerobot.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @author Ethan.Lam  
 * @createTime 2011-11-16
 *
 */
public class Director {

      ExecutorService executor = null;
	
      int crawlNums =10;
      
      
	  public Director(){
		   
		  
	  } 
	
	  /**
	   *  
	   * 方法：初始化爬虫线程
	   * 
	   *  
	   *    Add By Ethan Lam  At 2011-11-17
	   */
	  public void init( ){
		  executor = Executors.newFixedThreadPool(this.crawlNums);
		  CrawlQueue.getQueueManager().newNode("新浪", "http://news.sina.com.cn/", null);
		  for(int i = 1;i<=this.crawlNums;i++){
			  executor.execute(new Crawl(""+i));
		  }
	  }
	
	 
	  public static void main(String...args){
		  Director director = new Director();
		  director.init();
	  }
	
}
