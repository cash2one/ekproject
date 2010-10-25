package cn.qtone.xxt.csop.dao.model;

import cn.qtone.xxt.csop.inter.ResultRow;
import cn.qtone.xxt.csop.util.Checker;

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

	String school = "";
	String className = "";
	String studentName = "";
	String parentName = "";
	String transaction = "";
	String operationType = "";
	String charge = "";
	String bookType = "";
	String operateDate = "";
	String reason = "";
	String operator = "";
	String saleRelationShip = "";
	String remark = "";

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		if (!Checker.isNull(school))
			this.school = school;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		if (!Checker.isNull(className))
			this.className = className;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		if (!Checker.isNull(studentName))
			this.studentName = studentName;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		if (!Checker.isNull(parentName))
			this.parentName = parentName;
	}

	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		if (!Checker.isNull(transaction))
			this.transaction = transaction;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		if (!Checker.isNull(operationType))
			this.operationType = operationType;
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		if (!Checker.isNull(charge))
			this.charge = charge;
	}

	public String getBookType() {
		return bookType;
	}

	public void setBookType(String bookType) {
		if (!Checker.isNull(bookType))
			this.bookType = bookType;
	}

	public String getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(String operateDate) {
		if (!Checker.isNull(operateDate))
			this.operateDate = operateDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		if (!Checker.isNull(reason))
			this.reason = reason;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		if (!Checker.isNull(operator))
			this.operator = operator;
	}

	public String getSaleRelationShip() {
		return saleRelationShip;
	}

	public void setSaleRelationShip(String saleRelationShip) {
		if (!Checker.isNull(saleRelationShip))
			this.saleRelationShip = saleRelationShip;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		if (!Checker.isNull(remark))
			this.remark = remark;
	}
}
