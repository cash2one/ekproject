package cn.qtone.xxt.jzdx.smsinter.bean;

import java.util.ArrayList;
import java.util.List;

public class Info {

	private String userId;

	private String abb;

	private String sendKind;// ���ͷ�����1 ȫУ���ͣ�2 ͳһ���ͣ�3 ���Ի����ͣ�

	private String parentType;

	// �������ͣ�zxqk ��У�����zy ��ҵ��dyjy ����������jjbk �ҽ̰ٿƣ�yejy �׶�����
	private String smsType;

	private String isAddSchool;

	private String isAddTeacher;

	private String isAddParent;

	private String isLongSMS;

	private String isSendNow;

	private String sendTime;
	
	private String stu_type;

	private List SMMList = new ArrayList();;// ���SMSBean

	public String getAbb() {
		return abb;
	}

	public void setAbb(String abb) {
		this.abb = abb;
	}

	public String getIsAddParent() {
		return isAddParent;
	}

	public void setIsAddParent(String isAddParent) {
		this.isAddParent = isAddParent;
	}

	public String getIsAddSchool() {
		return isAddSchool;
	}

	public void setIsAddSchool(String isAddSchool) {
		this.isAddSchool = isAddSchool;
	}

	public String getIsAddTeacher() {
		return isAddTeacher;
	}

	public void setIsAddTeacher(String isAddTeacher) {
		this.isAddTeacher = isAddTeacher;
	}

	public String getIsLongSMS() {
		return isLongSMS;
	}

	public void setIsLongSMS(String isLongSMS) {
		this.isLongSMS = isLongSMS;
	}

	public String getIsSendNow() {
		return isSendNow;
	}

	public void setIsSendNow(String isSendNow) {
		this.isSendNow = isSendNow;
	}

	public String getParentType() {
		return parentType;
	}

	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

	public String getSendKind() {
		return sendKind;
	}

	public void setSendKind(String sendKind) {
		this.sendKind = sendKind;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public List getSMMList() {
		return SMMList;
	}

	public void setSMMList(List list) {
		SMMList = list;
	}

	public String getSmsType() {
		return smsType;
	}

	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setStu_type(String stu_type) {
		this.stu_type = stu_type;
	}

	public String getStu_type() {
		return stu_type;
	}

}
