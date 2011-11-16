package cn.elam.reptilerobot.core;

import java.util.LinkedList;

import cn.elam.reptilerobot.base.Node;

/**
 * 
 * @author Ethan.Lam  2011-11-16
 *
 */
public class CrawlQueue {

    private static CrawlQueue crawlQueue =  new CrawlQueue();
	
	private LinkedList<Node> currentNodes = new LinkedList<Node>();
	
	private CrawlQueue(){
		
	}
	
	public static CrawlQueue getQueueManager(){
		return crawlQueue;
	}
	
	public synchronized void newNode(String url){
		currentNodes.offer(new Node());
	}
	
	
    public synchronized Node nextNode(){
    	Node nextUrl = currentNodes.poll();
    	return nextUrl;
    }

	
}
