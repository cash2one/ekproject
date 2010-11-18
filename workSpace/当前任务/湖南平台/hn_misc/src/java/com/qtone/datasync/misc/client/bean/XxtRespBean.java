package com.qtone.datasync.misc.client.bean;

/**
 * 该Bean为正向同步时，校讯通平台返回给Misc的响应报文Bean
 * 
 * @author 杨腾飞 2009-05-31
 */
public class XxtRespBean {
	private String msgType = "SyncOrderRelationResp";
	private String transactionID;
	private String version = "1.5.0";
	private String hRet;

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getHRet() {
		return hRet;
	}

	public void setHRet(String ret) {
		hRet = ret;
	}

	@Override
	public boolean equals(Object obj) {
		XxtRespBean bean = (XxtRespBean) obj;
		if (msgType.equals(bean.getMsgType())
				&& transactionID.equals(bean.getTransactionID())
				&& version.equals(bean.getVersion())
				&& hRet.equals(bean.getHRet()))
			return true;

		return false;
	}
}
