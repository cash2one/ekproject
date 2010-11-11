package cn.qtone.xxt.csop.webservices.bean;

import cn.qtone.xxt.csop.webservices.bean.anotation.ReqParam;
import cn.qtone.xxt.csop.webservices.bean.enums.ValueType;

/**
 * 服务请求参数列表  封装类
 * 基本元素
 * @author LinHanSheng
 * 
 */
public abstract class TransRequestParams  implements RequestParams {

	// 平台 platform=uniteview,目前统一使用uniteview平台
	
	@ReqParam(nodeName = "Platform", fetch =ValueType.TEXT_VALUE)
	String platform;
	
	// 标志位：0 正式 1 测试
	@ReqParam(nodeName = "Flag", fetch =ValueType.TEXT_VALUE)
	String flag;

	// 版本
	@ReqParam(nodeName = "Version", fetch =ValueType.TEXT_VALUE)
	String version;

	// 业务平台有效操作员ID
	@ReqParam(nodeName = "OperId", fetch =ValueType.TEXT_VALUE)
	String operId;

	// 业务平台密码，用MD5加密
	@ReqParam(nodeName = "Password", fetch =ValueType.TEXT_VALUE)
	String password;

	// 业务平台代码
	@ReqParam(nodeName = "SysCode", fetch =ValueType.TEXT_VALUE)
	String sysCode;

	// 服务号码 手机号码
	@ReqParam(nodeName = "ServNumber", fetch =ValueType.TEXT_VALUE)
	String servNumber;

	
	/* 
	 *                           业务代码
	 * 业务定制情况查询接口                    B005_01
	 * 业务受理接口                                        B005_02
	 * 业务历史订购记录查询接口          B005_03
	 * 业务使用记录查询接口                    B005_04
	 */
	@ReqParam(nodeName = "BusiCode", fetch =ValueType.TEXT_VALUE)
	String busiCode;

	
	// 查询手机号码
	@ReqParam(parent="Params",nodeName = "TelNo", fetch =ValueType.TEXT_VALUE)
	String telNo;

	
	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getServNumber() {
		return servNumber;
	}

	public void setServNumber(String servNumber) {
		this.servNumber = servNumber;
	}

	public String getBusiCode() {
		return busiCode;
	}

	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

}
