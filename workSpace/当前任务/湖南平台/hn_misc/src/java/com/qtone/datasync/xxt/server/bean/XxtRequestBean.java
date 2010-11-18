package com.qtone.datasync.xxt.server.bean;

/**
 * 反向同步时，校讯通平台组织生成的用户信息实体
 * 
 * @author 杨腾飞 2009-6-2
 */
public class XxtRequestBean {
	/**
	 * 为支持CP业务而添加的字段，用于标识该记录是否与CP相关
	 * 
	 * 0 表示非CP业务		1表示CP业务
	 */
	private int isCp = 0;
	
	/**
	 * 该实体Bean所属地区
	 */
	private String areaAbb;

	/**
	 * 业务ID
	 */
	private String transactionId;

	/**
	 * 实体Bean的ID
	 */
	private long id;

	/**
	 * 用户手机号码
	 */
	private String phone;

	/**
	 * 用户需要订购的业务
	 */
	private String spServiceId;

	/**
	 * 用户业务操作模式 1 开通 2 取消
	 */
	private int action;

	/**
	 * 消息类型
	 */
	private String msgType;
	
	private int family_id;

	public XxtRequestBean() {
	}

	/**
	 * @return the isCp
	 */
	public int getIsCp() {
		return isCp;
	}

	/**
	 * @param isCp the isCp to set
	 */
	public void setIsCp(int isCp) {
		this.isCp = isCp;
	}

	public String getAreaAbb() {
		return areaAbb;
	}

	public void setAreaAbb(String areaAbb) {
		this.areaAbb = areaAbb;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSpServiceId() {
		return spServiceId;
	}

	public void setSpServiceId(String spServiceId) {
		this.spServiceId = spServiceId;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder();
		ret.append("RequestToMisc").append("\t");
		ret.append(msgType).append("\t");
		ret.append(areaAbb).append("\t");
		ret.append(transactionId).append("\t");
		ret.append(id).append("\t");
		ret.append(phone).append("\t");
		ret.append(spServiceId).append("\t");
		ret.append(action).append("\t");
		
		return ret.toString();
	}

	public int getFamily_id() {
		return family_id;
	}

	public void setFamily_id(int family_id) {
		this.family_id = family_id;
	}
}
