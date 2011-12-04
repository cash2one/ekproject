package cn.elam.reptilerobot.persistent;

import java.util.List;

import cn.elam.reptilerobot.base.Node;

/**
 * URL 节点信息
 * @author Ethan.Lam  
 * @createTime 2011-12-3
 *
 */
public interface INodeService {

	/**
	 * 
	 * 方法：
	 * 
	 * @param node
	 * @return
	 *  
	 *    Add By Ethan Lam  At 2011-12-4
	 */
	long save(Node node);
	
	
	/**
	 * 
	 * 方法：
	 * 
	 * @param url
	 * @return
	 *  
	 *    Add By Ethan Lam  At 2011-12-4
	 */
	String loadNodeIdByUrl(String url);
	
	/**
	 * 
	 * 方法：
	 * 
	 * @param id
	 * @return
	 *  
	 *    Add By Ethan Lam  At 2011-12-4
	 */
	Node loadNodeById(long id);
	
	
	/**
	 * 
	 * 方法：
	 * 
	 * @param page
	 * @param pageSize
	 * @return
	 *  
	 *    Add By Ethan Lam  At 2011-12-4
	 */
	List<Node> loadByPage(int page,int pageSize);
	
}
