package esfw.core.framework.dao;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 描述：PageBean  分页用的对象
 * @author Ethan.Lam
 * @dateTime 2012-5-22
 * @param <E>
 *
 */
public class PageBean<E> implements Serializable{

	//当前页码
	private int curPage;
	
	//页面大小
	private int pageSize;
	
	//总页数
	private int pages;
	
	//总记录数
	private int totalRecords;
	
	//记录
	private List<E> beanList;
	
	
	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public List<E> getBeanList() {
		return beanList;
	}

	public void setBeanList(List<E> beanList) {
		this.beanList = beanList;
	}

	
}
