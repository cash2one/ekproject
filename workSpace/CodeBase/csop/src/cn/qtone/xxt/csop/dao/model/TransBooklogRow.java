package cn.qtone.xxt.csop.dao.model;

import cn.qtone.xxt.csop.inter.ResultRow;

/**
 * 业务定制情况查询结果
 * 
 * @author linhansheng
 * 
 */
public class TransBooklogRow implements ResultRow {

	/**
	 * 学校 班级 学生姓名 家长姓名 服务名称 操作方式 标准资费（元） 操作类型 操作日期 操作原因 操作人 营销关联信息 备注
	 */

	String school;
	String className;
	String studentName;
	String parentName;
	String transaction;
	String operationType;
	String charge;
	String bookType;
	String operateDate;
	String reason;
	String operator;
	String saleRelationShip;
	String remark;

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

	public String getBookType() {
		return bookType;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}

	public String getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getSaleRelationShip() {
		return saleRelationShip;
	}

	public void setSaleRelationShip(String saleRelationShip) {
		this.saleRelationShip = saleRelationShip;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
