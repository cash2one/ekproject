package cn.qtone.xxt.csop.business;

import cn.qtone.xxt.csop.webservices.bean.RequestParams;

/**
 * 
 * 定义请求服务的有效性
 * 
 * @author LINHANSHENG
 * 
 */
public enum ServiceAgreement {

	TRANSCATION_CUSTOMER("B005", "uniteview", "B005_01"),
	TRANSCATION_BOOKLOG("B005", "uniteview", "B005_03");

	String platform; // 平台
	String busiCode; // 业务代码
	String sysCode; // 业务平台代码

	ServiceAgreement(String sysCode, String platform, String busiCode) {
		this.platform = platform;
		this.busiCode = busiCode;
		this.sysCode = sysCode;
	}

	/*
	 * 服务可用性
	 */
	public boolean validator(RequestParams params) {
		if (!this.getPlatform().equals(params.getPlatform()))
			return false;
		if (!this.getSysCode().equals(params.getSysCode()))
			return false;
		if (!this.getBusiCode().equals(params.getBusiCode()))
			return false;
		return false;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getBusiCode() {
		return busiCode;
	}

	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

}
