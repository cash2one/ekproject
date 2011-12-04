package cn.elam.reptilerobot.persistent.imps;

import java.util.List;

import cn.elam.reptilerobot.base.Page;
import cn.elam.reptilerobot.persistent.IPageService;
import cn.elam.reptilerobot.persistent.base.BaseServiceImpl;

/**
 * 页面数据
 * @author Ethan.Lam  
 * @createTime 2011-12-3
 *
 */
public class PageServiceImp extends BaseServiceImpl<Page> implements IPageService{

	private static final String GLOBAL_PAGE_ID = "global:nextPageId";
	private static final String PAGE_ID_FORMAT = "page:id:%s";
	private static final String PAGE_ID_LIST = "page:ids";
	
	@Override
	public long save(Page page) {
		// TODO Auto-generated method stub
         page.setId(super.incr(GLOBAL_PAGE_ID));
         //保存value
		 super.save(String.format(PAGE_ID_FORMAT,page.getId()), page);
		 //保存id到集合中
		 super.addHeadList(PAGE_ID_LIST, page.getId()+"");
		 return page.getId();
	}
	

	@Override
	public Page loadPageById(long id) {
		// TODO Auto-generated method stub
		return super.get(String.format(PAGE_ID_FORMAT,id));
	}


	@Override
	public List<Page> loadByPage(int page, int pageSize) {
		// TODO Auto-generated method stub
		return loadListByPageIds(PageServiceImp.PAGE_ID_LIST, PageServiceImp.PAGE_ID_FORMAT,page, pageSize);
	}
	
	
}
