package com.qtone.datasync.xxt.server.bean;

/**
 * 正向数据同步时，解析Misc的同步请求生成该BEAN
 * 
 * @author 杨腾飞 2009-05-20
 */
public class MiscRequestBean {
	/**
	 * 消息类型
	 */
	private String msgType = "";

	/**
	 * 消息编号
	 */
	private String transactionID = "";

	/**
	 * 付费用户
	 */
	private String phoneFee = "";

	/**
	 * 使用服务的用户
	 */
	private String phoneUse = "";

	/**
	 * 服务操作： 1.开通服务 2.取消服务 3.激活服务 4.暂停服务
	 */
	private int action = 0;

	/**
	 * 操作产生原因（也就是当前数据同步发起的原因是什么）: 1.用户发起行为 2.Admin&1860发起行为 3.Boss停机 4.Boss开机
	 * 5.Boss过户 6.Boss销户 7.Boss改号 8.扣费失败导致服务取消 9.其它
	 */
	private int actionReason = 0;

	/**
	 * 企业代码,可选
	 */
	private String spId = "";

	/**
	 * 服务代码（该企业提供的服务的代码）
	 */
	private String spServiceId = "";

	/**
	 * 服务订购/取消的方式：
	 * 1.WEB
	 * 2.WAP
	 * 3.SMS
	 * 4.MMS
	 */
	private int accessMode = 0;
	
	/**
	 * 服务订购参数
	 */
	private String featureStr = "";
	/**
	 * 异常代码
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
