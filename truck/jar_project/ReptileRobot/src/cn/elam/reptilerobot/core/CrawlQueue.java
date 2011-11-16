package cn.elam.reptilerobot.core;

import java.util.LinkedList;

import cn.elam.reptilerobot.base.Node;

/**
 * �������
 * @author Ethan.Lam  2011-11-16
 *
 */
public class CrawlQueue {

    private static CrawlQueue crawlQueue =  new CrawlQueue();
	
	private LinkedList<Node> currentNodes = new LinkedList<Node>();
	
	/**
	 * �������
	 */
	private CrawlQueue(){
		
	}
	
	/**
	 * 
	 * �����������������ʵ��
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
	 * �������½�һ������ڵ�
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
	 * �������õ���һ������ڵ�
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
