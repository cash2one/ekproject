package com.qtone.datasync.misc.client.bean;

/**
 * 反向同步时，Misc对校讯通平台的响应
 * 
 * @author 杨腾飞 2009-6-4
 */
public class MiscRespBean {
	private String transactionId;

	private String hRet;
	
	public MiscRespBean(){}
	
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getHRet() {
		return hRet;
	}

	public void setHRet(String ret) {
		hRet = ret;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("ResponseFromMisc").append("\t");
		str.append(transactionId).append("\t");
		str.append(hRet);
		
		return str.toString();
	}
}
