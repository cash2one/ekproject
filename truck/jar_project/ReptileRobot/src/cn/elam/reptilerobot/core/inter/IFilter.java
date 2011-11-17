package cn.elam.reptilerobot.core.inter;

import org.htmlparser.tags.LinkTag;

/**
 * 过滤处理接口
 * 
 * @author Ethan.Lam 2011-11-17
 * 
 */
public interface IFilter {

	boolean isLinkTagFilter(LinkTag linkTag);

}
