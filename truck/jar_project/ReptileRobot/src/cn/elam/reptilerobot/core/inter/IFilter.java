package cn.elam.reptilerobot.core.inter;

import org.htmlparser.tags.LinkTag;

/**
 * ���˴���ӿ�
 * 
 * @author Ethan.Lam 2011-11-17
 * 
 */
public interface IFilter {

	boolean isLinkTagFilter(LinkTag linkTag);

}
