package cn.elam.reptilerobot.persistent;

import java.util.List;

import cn.elam.reptilerobot.base.Node;

/**
 * URL �ڵ���Ϣ
 * @author Ethan.Lam  
 * @createTime 2011-12-3
 *
 */
public interface INodeService {

	/**
	 * 
	 * ������
	 * 
	 * @param node
	 * @return
	 *  
	 *    Add By Ethan Lam  At 2011-12-4
	 */
	long save(Node node);
	
	
	/**
	 * 
	 * ������
	 * 
	 * @param url
	 * @return
	 *  
	 *    Add By Ethan Lam  At 2011-12-4
	 */
	String loadNodeIdByUrl(String url);
	
	/**
	 * 
	 * ������
	 * 
	 * @param id
	 * @return
	 *  
	 *    Add By Ethan Lam  At 2011-12-4
	 */
	Node loadNodeById(long id);
	
	
	/**
	 * 
	 * ������
	 * 
	 * @param page
	 * @param pageSize
	 * @return
	 *  
	 *    Add By Ethan Lam  At 2011-12-4
	 */
	List<Node> loadByPage(int page,int pageSize);
	
}
