package cn.qtone.xxt.jzdx.smsinter.bean;

import java.util.HashMap;

public class EachSendBean {

	private String classId;

	private String studentIds;

	private String filterStuId="-1";

	private String filterStuName="";

	private HashMap contentMap = new HashMap();

	private HashMap isLongMap = new HashMap();

	private HashMap filterMap = new HashMap();

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public HashMap getContentMap() {
		return contentMap;
	}

	public void setContentMap(HashMap contentMap) {
		this.contentMap = contentMap;
	}

	public HashMap getIsLongMap() {
		return isLongMap;
	}

	public void setIsLongMap(HashMap isLongMap) {
		this.isLongMap = isLongMap;
	}

	public String getStudentIds() {
		return studentIds;
	}

	public void setStudentIds(String studentIds) {
		this.studentIds = studentIds;
	}

	public HashMap getFilterMap() {
		return filterMap;
	}

	public void setFilterMap(HashMap filterMap) {
		this.filterMap = filterMap;
	}

	public String getFilterStuId() {
		return filterStuId;
	}

	public void setFilterStuId(String filterStuId) {
		this.filterStuId = filterStuId;
	}

	public String getFilterStuName() {
		return filterStuName;
	}

	public void setFilterStuName(String filterStuName) {
		this.filterStuName = filterStuName;
	}

}
