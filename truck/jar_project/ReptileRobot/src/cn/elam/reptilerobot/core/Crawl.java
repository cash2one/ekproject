package cn.elam.reptilerobot.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import cn.elam.reptilerobot.base.Node;
import cn.elam.reptilerobot.utils.LoggerUtil;

/**
 * 
 * @author Ethan.Lam  2011-11-16
 *
 */
public class Crawl extends Thread{

	   private Node nextNode = null;   
	
	   public void run(){
		   while((nextNode=CrawlQueue.getQueueManager().nextNode())!=null){
			   String curUrl = nextNode.getUrl();
			   if(curUrl==null||"".equals(curUrl))
				   continue;
			   LoggerUtil.info("Crawl","爬虫到："+curUrl);
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
		            
			    } catch (Exception e) {
				  e.printStackTrace();
			    }finally{
			    	uRLConnection.disconnect();
			    }
		   }
	   }
	    	
}
