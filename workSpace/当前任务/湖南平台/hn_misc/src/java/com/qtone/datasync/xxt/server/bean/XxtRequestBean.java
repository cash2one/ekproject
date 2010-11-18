package com.qtone.datasync.xxt.server.bean;

/**
 * ����ͬ��ʱ��УѶͨƽ̨��֯���ɵ��û���Ϣʵ��
 * 
 * @author ���ڷ� 2009-6-2
 */
public class XxtRequestBean {
	/**
	 * Ϊ֧��CPҵ�����ӵ��ֶΣ����ڱ�ʶ�ü�¼�Ƿ���CP���
	 * 
	 * 0 ��ʾ��CPҵ��		1��ʾCPҵ��
	 */
	private int isCp = 0;
	
	/**
	 * ��ʵ��Bean��������
	 */
	private String areaAbb;

	/**
	 * ҵ��ID
	 */
	private String transactionId;

	/**
	 * ʵ��Bean��ID
	 */
	private long id;

	/**
	 * �û��ֻ�����
	 */
	private String phone;

	/**
	 * �û���Ҫ������ҵ��
	 */
	private String spServiceId;

	/**
	 * �û�ҵ�����ģʽ 1 ��ͨ 2 ȡ��
	 */
	private int action;

	/**
	 * ��Ϣ����
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
