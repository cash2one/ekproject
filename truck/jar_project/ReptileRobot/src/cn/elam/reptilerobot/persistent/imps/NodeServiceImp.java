package cn.elam.reptilerobot.persistent.imps;

import java.util.List;

import cn.elam.reptilerobot.base.Node;
import cn.elam.reptilerobot.persistent.INodeService;
import cn.elam.reptilerobot.persistent.base.BaseServiceImpl;
import cn.elam.reptilerobot.utils.MD5;

/**
 * URL 节点数据
 * @author Ethan.Lam  
 * @createTime 2011-12-3
 *
 */
public class NodeServiceImp extends BaseServiceImpl<Node> implements INodeService{

	private static final String GLOBAL_NODE_ID = "global:nextNodeId";
	private static final String NODE_ID_FORMAT = "node:id:%s";
	private static final String NODE_ID_LIST = "node:ids";
	private static final String NODE_URL_FORMAT = "node:url:%s";
	
    
	@Override
	public long save(Node node) {
		// TODO Auto-generated method stub
		if(loadNodeIdByUrl(node.getUrl())==null){
		   node.setId(super.incr(GLOBAL_NODE_ID));	
		   super.save(String.format(NODE_ID_FORMAT, node.getId()), node);
		   super.addHeadList(NODE_ID_LIST,node.getId()+"");
		   super.saveStr(String.format(NODE_URL_FORMAT,MD5.generateMD5Str(node.getUrl())),node.getId()+"");
		   return node.getId();
		}
		return -1;
	  
	}

	
	@Override
	public String loadNodeIdByUrl(String url) {
		// TODO Auto-generated method stub
		url = MD5.generateMD5Str(url);
        String id = super.getStr(String.format(NODE_URL_FORMAT, url));
        return id;
	}


	
	@Override
	public Node loadNodeById(long id) {
		// TODO Auto-generated method stub
		return super.get(String.format(NODE_ID_FORMAT, id));
	}


	
	@Override
	public List<Node> loadByPage(int page, int pageSize) {
		// TODO Auto-generated method stub
		return super.loadListByPageIds(NODE_ID_LIST, NODE_ID_FORMAT, page, pageSize);
	}
	
	
	
}
