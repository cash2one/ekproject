package esfw.core.framework.controller;


/**
 * 
 * 描述：分页信息
 * 
 * @author Ethan.Lam
 * @dateTime 2012-6-2
 * 
 */
public class PageVo {

	Integer page = 1;
	Integer startRow = 1;
	Integer pageSize = 3;

	public Integer getPage() {
		return page == null ? 0 : page;
	}

	public void setPage(Integer page) {
		this.page = page;
		this.setStartRow((page - 1) * this.getPageSize() + 1);
	}

	public Integer getStartRow() {
		return startRow;
	}

	private void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
		this.setStartRow((page - 1) * this.getPageSize() + 1);
	}

}
