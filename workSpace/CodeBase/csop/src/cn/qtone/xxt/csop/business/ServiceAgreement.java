package cn.qtone.xxt.csop.business;

import cn.qtone.xxt.csop.util.CsopLog;
import cn.qtone.xxt.csop.webservices.bean.TransRequestParams;

/**
 * 
 * 定义请求服务的有效性 验证请求协议
 * 
 * @author LINHANSHENG
 * 
 */
public enum ServiceAgreement {

	TRANSCATION_CUSTOMER_QUERY("业务订购情况查询服务", "1.0", "B005", "uniteview", "B005_01"), 
	TRANSCATION_CUSTOMER_BOOK("业务受理服务", "1.0", "B005", "uniteview", "B005_02"),
	TRANSCATION_BOOKLOG("业务订购历史记录查询服务", "1.0", "B005", "uniteview", "B005_03"),
	TRANSCATION_USESTATE("业务使用状态查询服务", "1.0", "B005", "uniteview", "B005_04");

	String platform; // 平台
	String busiCode; // 业务代码
	String sysCode; // 业务平台代码
	String cname;
	String version; // 协议版本

	ServiceAgreement(String cname, String version, String sysCode,
			String platform, String busiCode) {
		this.cname = cname;
		this.platform = platform;
		this.busiCode = busiCode;
		this.sysCode = sysCode;
		this.version = version;
	}

	/*
	 * 服务可用性
	 */
	public boolean validator(TransRequestParams params) {
		if (!platform().equals(params.getPlatform()))
			return false;
		if (!version().equals(params.getVersion()))
			return false;
		if (!sysCode().equals(params.getSysCode()))
			return false;
		if (!busiCode().equals(params.getBusiCode()))
			return false;
		if(params.getTelNo()==null||params.getTelNo().trim().length()<11){
			CsopLog.info("请求查询的号码对象为非法号码，提供的号码为空或长度不正确！");
			return false;
		}
		return true;
	}

	public String platform() {
		return platform;
	}

	public String version() {
		return version;
	}

	public String busiCode() {
		return busiCode;
	}

	public String sysCode() {
		return sysCode;
	}

}
