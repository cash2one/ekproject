package cn.qtone.xxt.csop.business;

import cn.qtone.xxt.csop.webservices.bean.RequestParams;

/**
 * 
 * 定义请求服务的有效性
 * 验证请求协议
 * @author LINHANSHENG
 * 
 */
public enum ServiceAgreement {

	TRANSCATION_CUSTOMER("业务订购情况查询服务","B005", "uniteview", "B005_01"),
	TRANSCATION_BOOK("业务受理服务","B005", "uniteview", "B005_02"),
	TRANSCATION_BOOKLOG("业务订购历史记录查询服务","B005", "uniteview", "B005_03"),
	TRANSCATION_USESTATE("业务使用状态查询服务","B005", "uniteview", "B005_04");

	String platform; // 平台
	String busiCode; // 业务代码
	String sysCode; // 业务平台代码
    String cname;
	
	ServiceAgreement(String cname,String sysCode, String platform, String busiCode) {
		this.cname = cname;
		this.platform = platform;
		this.busiCode = busiCode;
		this.sysCode = sysCode;
	}

	/*
	 * 服务可用性
	 */
	public boolean validator(RequestParams params) {
		if (!platform().equals(params.getPlatform()))
			return false;
		if (!sysCode().equals(params.getSysCode()))
			return false;
		if (!busiCode().equals(params.getBusiCode()))
			return false;
		return true;
	}

	public String platform() {
		return platform;
	}


	public String busiCode() {
		return busiCode;
	}

	public String sysCode() {
		return sysCode;
	}

}
