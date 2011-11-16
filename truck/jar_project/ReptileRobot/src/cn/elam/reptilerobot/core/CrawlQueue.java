package cn.elam.reptilerobot.core;

import java.util.LinkedList;

import cn.elam.reptilerobot.base.Node;

/**
 * 爬虫队列
 * @author Ethan.Lam  2011-11-16
 *
 */
public class CrawlQueue {

    private static CrawlQueue crawlQueue =  new CrawlQueue();
	
	private LinkedList<Node> currentNodes = new LinkedList<Node>();
	
	/**
	 * 爬虫队列
	 */
	private CrawlQueue(){
		
	}
	
	/**
	 * 
	 * 方法：返回爬虫队列实例
	 * 
	 * @return
	 *  
	 *    Add By Ethan Lam  At 2011-11-16
	 */
	public static CrawlQueue getQueueManager(){
		return crawlQueue;
	}
	
	
	/**
	 * 
	 * 方法：新建一个爬虫节点
	 * 
	 * @param url
	 *  
	 *    Add By Ethan Lam  At 2011-11-16
	 */
	public synchronized void newNode(String title,String url,String preUrl){
		Node newNode = new Node();
		newNode.setUrl(url);
		newNode.setTitle(title);
		newNode.setPreUrl(preUrl);
		currentNodes.offer(newNode);
	}
	
	
	/**
	 * 
	 * 方法：得到下一个爬虫节点
	 * 
	 * @return
	 *  
	 *    Add By Ethan Lam  At 2011-11-16
	 */
    public synchronized Node nextNode(){
    	Node nextUrl = currentNodes.poll();
    	return nextUrl;
    }

	
}
