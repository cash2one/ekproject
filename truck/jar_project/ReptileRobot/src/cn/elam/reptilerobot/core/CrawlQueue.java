package cn.elam.reptilerobot.core;

import java.util.LinkedList;

import cn.elam.reptilerobot.base.Node;
import cn.elam.reptilerobot.persistent.INodeService;
import cn.elam.reptilerobot.persistent.imps.NodeServiceImp;
import cn.elam.reptilerobot.utils.LoggerUtil;


/**
 * 
 * 爬虫队列 TO_DO
 * @author Ethan.Lam  2011-11-16
 *
 */
public class CrawlQueue {

    private static CrawlQueue crawlQueue =  new CrawlQueue();
	
	private LinkedList<Node> currentNodes = new LinkedList<Node>();
	
	INodeService nodeService = new NodeServiceImp();
	
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
		//未被访问过的，可以加入
		if(!VisitedTableManager.getManager().hasVisited(newNode)){
			LoggerUtil.p("CrawlQueue 新增 URL： ",url);
			currentNodes.offer(newNode);
			nodeService.save(newNode);
		}else{
			LoggerUtil.p("CrawlQueue 重复URL： ",url);
		}
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
