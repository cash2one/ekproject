package cn.elam.reptilerobot.core.inter;

import org.htmlparser.tags.LinkTag;

/**
 * ���˴���ӿ�
 * 
 * @author Ethan.Lam 2011-11-17
 * 
 */
public interface IFilter {

	/**
	 * 
	 * ���������ӹ��˽ӿ�
	 * 
	 * @param linkTag
	 * @return
	 *  
	 *    Add By Ethan Lam  At 2011-12-4
	 */
	boolean isLinkTagFilter(LinkTag linkTag);

}
