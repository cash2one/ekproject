package cn.elam.reptilerobot.persistent;

import java.util.List;

import cn.elam.reptilerobot.base.Page;

public interface IPageService {

	/**、
	 * 
	 * 方法：
	 * 
	 * @param page
	 * @return
	 *  
	 *    Add By Ethan Lam  At 2011-12-4
	 */
	long save(Page page);
	
	/**
	 * 
	 * 方法：
	 * 
	 * @param id
	 * @return
	 *  
	 *    Add By Ethan Lam  At 2011-12-4
	 */
	Page loadPageById(long id);
	
	
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
	List<Page> loadByPage(int page,int pageSize);
	
}
