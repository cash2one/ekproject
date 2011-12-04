package cn.elam.reptilerobot.persistent;

import java.util.List;

import cn.elam.reptilerobot.base.Node;
import cn.elam.reptilerobot.base.Page;
import cn.elam.reptilerobot.persistent.imps.NodeServiceImp;
import cn.elam.reptilerobot.persistent.imps.PageServiceImp;
import cn.elam.reptilerobot.utils.LoggerUtil;

public class Query {

	public static void main(String... args) {
//		testNode();
		testPage();
	}

	
	public static void testNode(){
		INodeService ns = new NodeServiceImp();
		Node node = ns.loadNodeById(9953);
		LoggerUtil.p("testNode",node.getId(),node.getUrl(),node.getLastVisitTime());
		String id = ns.loadNodeIdByUrl(node.getUrl());
		LoggerUtil.p("testNode",id);
		long ct = System.currentTimeMillis();
	    List<Node> nodes =  ns.loadByPage(1,10);
	    for(Node n:nodes){
	    	LoggerUtil.p("testNode->loadListByPageIds: ",n.getId(),n.getUrl(),n.getLastVisitTime());
	    }
	    LoggerUtil.p("∫ƒ ±£∫",(System.currentTimeMillis()-ct)+"∫¡√Î");
	}
	
	
	public static void testPage(){
		IPageService ps = new PageServiceImp();
        Page page = ps.loadPageById(677);
        List<Page> pages = ps.loadByPage(1, 100);
        long ct = System.currentTimeMillis();
        for(Page n:pages){
	    	LoggerUtil.p("testPage->loadListByPageIds: ",n.getId(),n.getUrl(),n.getSegment());
	    }
        LoggerUtil.p("∫ƒ ±£∫",(System.currentTimeMillis()-ct)+"∫¡√Î");
        
        
	}
	


}
