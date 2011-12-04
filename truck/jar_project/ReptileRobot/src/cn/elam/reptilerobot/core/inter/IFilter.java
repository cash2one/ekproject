package cn.elam.reptilerobot.core.inter;

import org.htmlparser.tags.LinkTag;

/**
 * 过滤处理接口
 * 
 * @author Ethan.Lam 2011-11-17
 * 
 */
public interface IFilter {

	/**
	 * 
	 * 方法：链接过滤接口
	 * 
	 * @param linkTag
	 * @return
	 *  
	 *    Add By Ethan Lam  At 2011-12-4
	 */
	boolean isLinkTagFilter(LinkTag linkTag);

}
