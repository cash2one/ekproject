package cn.qtone.xxt.base.dao.comom;

public class PageModel {

	int currentPage = 1;
	int pageNum = 15;
	int allRecords = 0;
	int totalPages = 0;

	public PageModel() {
		currentPage = 1;
		pageNum = 15;
		allRecords = 0;
	}

	public PageModel(int pageNum) {
		currentPage = 1;
		this.pageNum = pageNum > 0 ? pageNum : 15;
		allRecords = 0;
	}

	public PageModel(int curpage, int pageNum, int allRecords) {
		currentPage = curpage <= 0 ? 1 : curpage;
		this.pageNum = pageNum > 0 ? pageNum : 15;
		this.allRecords = allRecords;
		if (allRecords > 0)
			this.totalPages = (this.allRecords / this.pageNum <= 1) ? 1
					: this.allRecords / this.pageNum + 1;
		else
			this.totalPages = 0;
	}

	public String getSqlString() {
		/*
		 * SELECT * FROM ( SELECT A.*, ROWNUM RN FROM (SELECT * FROM
		 * QX_ADMIN_USER) A WHERE ROWNUM <= 40 ) WHERE RN >= 38
		 */
		return "";
	}

	public String getSqlString(String baseSql, DBType db) {
		StringBuffer sql = new StringBuffer();
		int startRow = 0;
		int endRow = 0;
		
		startRow = (this.currentPage-1)*pageNum;
		endRow=startRow+pageNum;
		
		if (DBType.ORACLE.equals(db)) {
			sql.append(" SELECT * FROM ( SELECT A.*, ROWNUM RN  FROM ( ");
			sql.append(baseSql);
			sql.append(" ) A  WHERE ROWNUM <=").append(endRow).append(
					" )WHERE RN >= ").append(startRow);
			baseSql = sql.toString();
			sql = null;
			return baseSql;
		} else if (DBType.MYSQL.equals(db)) {
			return baseSql;
		} else
			return baseSql;
	}

}
