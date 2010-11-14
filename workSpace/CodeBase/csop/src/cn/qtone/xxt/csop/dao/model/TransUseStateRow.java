package cn.qtone.xxt.csop.dao.model;

import cn.qtone.xxt.csop.inter.ResultRow;
import cn.qtone.xxt.csop.util.Checker;

/**
 * 6.4.1.4	业务使用记录 返回列
 * @author linhansheng
 *
 */
public class TransUseStateRow implements ResultRow {

	/*
	 * 发送日期 接收日期 学生姓名 短信类型 发送号码 接收号码 发送状态 接收状态
	 */
	String sendDate;
	String receviceDate;
	String studentName;
	String dxType;
	String sendPhone;
	String recevicePhone;
	String sendState;
	String receviceState;

	public String getSendState() {
		return sendState;
	}

	public void setSendState(String sendState) {
		this.sendState = sendState;
	}
	
	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		if (!Checker.isNull(sendDate))
			this.sendDate = sendDate;
	}

	public String getReceviceDate() {
		return receviceDate;
	}

	public void setReceviceDate(String receviceDate) {
		if (!Checker.isNull(receviceDate))
			this.receviceDate = receviceDate;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		if (!Checker.isNull(studentName))
			this.studentName = studentName;
	}

	public String getDxType() {
		return dxType;
	}

	public void setDxType(String dxType) {
		if (!Checker.isNull(dxType))
			this.dxType = dxType;
	}

	public String getSendPhone() {
		return sendPhone;
	}

	public void setSendPhone(String sendPhone) {
		if (!Checker.isNull(sendPhone))
			this.sendPhone = sendPhone;
	}

	public String getRecevicePhone() {
		return recevicePhone;
	}

	public void setRecevicePhone(String recevicePhone) {
		if (!Checker.isNull(recevicePhone))
			this.recevicePhone = recevicePhone;
	}

	public String getReceviceState() {
		return receviceState;
	}

	public void setReceviceState(String receviceState) {
		if (!Checker.isNull(receviceState))
			this.receviceState = receviceState;
	}
	
	
	public String formColumnData(){
		StringBuffer resultXml = new StringBuffer();
		resultXml.append("<column>");
		resultXml.append("<column1>").append(this.getSendDate()).append("</column1>");
		resultXml.append("<column2>").append(this.getReceviceDate()).append("</column2>");
		resultXml.append("<column3>").append(this.getStudentName()).append("</column3>");
		resultXml.append("<column4>").append(this.getDxType()).append("</column4>");
		resultXml.append("<column5>").append(this.getSendPhone()).append("</column5>");
		resultXml.append("<column6>").append(this.getRecevicePhone()).append("</column6>"); 
		resultXml.append("<column7>").append(this.getSendState()).append("</column7>");
		resultXml.append("<column8>").append(this.getReceviceState()).append("</column8>");
		resultXml.append("</column>");
		return resultXml.toString();
	}
	

}
