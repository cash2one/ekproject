package com.qtone.datasync.xxt.server.bean;

/**
 * ��������ͬ��ʱ������Misc��ͬ���������ɸ�BEAN
 * 
 * @author ���ڷ� 2009-05-20
 */
public class MiscRequestBean {
	/**
	 * ��Ϣ����
	 */
	private String msgType = "";

	/**
	 * ��Ϣ���
	 */
	private String transactionID = "";

	/**
	 * �����û�
	 */
	private String phoneFee = "";

	/**
	 * ʹ�÷�����û�
	 */
	private String phoneUse = "";

	/**
	 * ��������� 1.��ͨ���� 2.ȡ������ 3.������� 4.��ͣ����
	 */
	private int action = 0;

	/**
	 * ��������ԭ��Ҳ���ǵ�ǰ����ͬ�������ԭ����ʲô��: 1.�û�������Ϊ 2.Admin&1860������Ϊ 3.Bossͣ�� 4.Boss����
	 * 5.Boss���� 6.Boss���� 7.Boss�ĺ� 8.�۷�ʧ�ܵ��·���ȡ�� 9.����
	 */
	private int actionReason = 0;

	/**
	 * ��ҵ����,��ѡ
	 */
	private String spId = "";

	/**
	 * ������루����ҵ�ṩ�ķ���Ĵ��룩
	 */
	private String spServiceId = "";

	/**
	 * ���񶩹�/ȡ���ķ�ʽ��
	 * 1.WEB
	 * 2.WAP
	 * 3.SMS
	 * 4.MMS
	 */
	private int accessMode = 0;
	
	/**
	 * ���񶩹�����
	 */
	private String featureStr = "";
	/**
	 * �쳣����
	 */
	private String errorCode = "";

	public MiscRequestBean() {

	}

	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	public String getPhoneFee() {
		return phoneFee;
	}

	public void setPhoneFee(String phoneFee) {
		this.phoneFee = phoneFee;
	}

	public String getPhoneUse() {
		return phoneUse;
	}

	public void setPhoneUse(String phoneUse) {
		this.phoneUse = phoneUse;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public int getActionReason() {
		return actionReason;
	}

	public void setActionReason(int actionReason) {
		this.actionReason = actionReason;
	}

	public String getSpId() {
		return spId;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}

	public String getSpServiceId() {
		return spServiceId;
	}

	public void setSpServiceId(String spServiceId) {
		this.spServiceId = spServiceId;
	}

	/**
	 * @return the accessMode
	 */
	public int getAccessMode() {
		return accessMode;
	}

	/**
	 * @param accessMode the accessMode to set
	 */
	public void setAccessMode(int accessMode) {
		this.accessMode = accessMode;
	}

	/**
	 * @return the featureStr
	 */
	public String getFeatureStr() {
		return featureStr;
	}

	/**
	 * @param featureStr the featureStr to set
	 */
	public void setFeatureStr(String featureStr) {
		this.featureStr = featureStr;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		MiscRequestBean bean = (MiscRequestBean) obj;
		if (bean.getAction() == this.action
				&& bean.getActionReason() == this.actionReason
				&& bean.getMsgType().equalsIgnoreCase(this.msgType)
				&& bean.getPhoneFee().equals(this.phoneFee)
				&& bean.getPhoneUse().equals(this.phoneUse)
				&& bean.getSpId().equals(this.spId)
				&& bean.getSpServiceId().equals(this.getSpServiceId()))
			return true;

		return false;
	}
}
