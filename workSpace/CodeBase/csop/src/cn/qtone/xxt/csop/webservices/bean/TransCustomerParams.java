package cn.qtone.xxt.csop.webservices.bean;

import cn.qtone.xxt.csop.webservices.bean.anotation.ReqParam;
import cn.qtone.xxt.csop.webservices.bean.enums.ValueType;

/**
 * 业务受理定制 B005_01
 * 
 * @author linhansheng
 * 
 */
public class TransCustomerParams extends RequestParams {

	public String getDoFlag() {
		return doFlag;
	}

	public void setDoFlag(String doFlag) {
		this.doFlag = doFlag;
	}

	public String getBusiFlag() {
		return busiFlag;
	}

	public void setBusiFlag(String busiFlag) {
		this.busiFlag = busiFlag;
	}

	// 受理类型 开通、取消
	@ReqParam(parent = "Params", nodeName = "DoFlag", fetch = ValueType.TEXT_VALUE)
	String doFlag;

	// 业务标识 家长短信箱、留言板、考勤短信、亲情电话
	@ReqParam(parent = "Params", nodeName = "BusiFlag", fetch = ValueType.TEXT_VALUE)
	String busiFlag;

}
