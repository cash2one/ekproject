package cn.elam.reptilerobot.core;

import org.htmlparser.tags.LinkTag;

import cn.elam.reptilerobot.core.inter.IFilter;

public class FilterImp implements IFilter{

	@Override
	public boolean isLinkTagFilter(LinkTag linkTag) {
		// TODO Auto-generated method stub
		if(linkTag==null)
			return false;
		String linkUrl = linkTag.getLink().toLowerCase();
		boolean isFilter = linkUrl.indexOf(".jpg")>=0?true:false;
		isFilter = (!isFilter&&linkUrl.indexOf(".png")>0)?true:false;
		isFilter = (!isFilter&&linkUrl.indexOf(".gif")>0)?true:false;
		isFilter = (!isFilter&&linkUrl.indexOf(".flv")>0)?true:false;
		isFilter = (!isFilter&&linkUrl.indexOf("news")<0)?true:false; //Ö»ÅÀ blog ÐÅÏ¢
		return isFilter;
	}

}
