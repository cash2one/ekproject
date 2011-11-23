package cn.elam.reptilerobot.core;

import java.net.HttpURLConnection;
import java.net.URL;

import cn.elam.reptilerobot.base.Node;
import cn.elam.reptilerobot.core.inter.IParserHandler;
import cn.elam.reptilerobot.utils.LoggerUtil;

/**
 * 
 * 爬虫类
 * @author Ethan.Lam  2011-11-16
 *
 */
public class Crawl extends Thread{

	   private Node nextNode = null;   
     
	   private IParserHandler parse = new ParserHandler();
	   
	   private String name;
	   
	   private int sleepSec = 10;
	   
	   /**
	    * 
	    */
	   public Crawl(String name){
		   this.name = name;
	   }
	   
	   
	   
	    @SuppressWarnings("static-access")
		public void run(){
			   while(true){
				   nextNode=CrawlQueue.getQueueManager().nextNode();
				   if(nextNode!=null){
					   String curUrl = nextNode.getUrl();
					   if(curUrl==null||"".equals(curUrl)||VisitedTableManager.getManager().hasVisited(curUrl))
						   continue;
					   
					    LoggerUtil.debug("Crawl【"+this.name+"】","爬虫到："+curUrl);
					    String response = "";
					    int responseCode = 0;
					    HttpURLConnection uRLConnection = null;
					    try {
						    URL url = new URL(curUrl);  
				            uRLConnection = (HttpURLConnection)url.openConnection();    
				            uRLConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
				            uRLConnection.setRequestProperty("Accept","image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/vnd.ms-powerpoint, application/vnd.ms-excel, application/msword, */*");
				            uRLConnection.setRequestProperty("Accept-Language", "zh-cn");
				            uRLConnection.setRequestProperty("UA-CPU", "x86");
				            //uRLConnection.setRequestProperty("Accept-Encoding", "gzip");//为什么没有deflate呢
				            uRLConnection.setRequestProperty("Content-type", "text/html");
				            uRLConnection.setRequestProperty("Connection", "close");
				            uRLConnection.setUseCaches(false);//不要用cache，用了也没有什么用，因为我们不会经常对一个链接频繁访问。（针对程序）
				            //uRLConnection.setConnectTimeout(10 * 1000);
				            //uRLConnection.setReadTimeout(10 * 1000);
				            uRLConnection.setDoOutput(true);
				            uRLConnection.setDoInput(true); 
				            
				            parse.analyzeHTML(curUrl,uRLConnection);
				            
					    } catch (Exception e) {
						  e.printStackTrace();
						  LoggerUtil.error("Crawl【"+this.name+"】","发生异常....",e);
					    }finally{
					    	uRLConnection.disconnect();
					    	uRLConnection = null;
					    }
				   }else{
					   LoggerUtil.info("Crawl【"+this.name+"】","没有更多的链接需要分析，休眠中...");
					   try {
						Thread.currentThread().sleep(sleepSec*1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				   }
			   }
		   }
	    	
}
