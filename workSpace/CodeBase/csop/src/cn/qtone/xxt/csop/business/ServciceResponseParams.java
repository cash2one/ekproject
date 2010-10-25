package cn.qtone.xxt.csop.business;

/**
 * 应答编码
 * 
 * @author LINHANSHENG
 * 
 */
public enum ServciceResponseParams {

	SUC("000", "服务调用成功", "平台已成功处理该请求"),
	SV0MMM("SV0MMM", "服务调用失败","平台处理服务请求失败"),
    SV0M99("SV0M99", "停止服务", "服务端关闭"), 
	SV0M95("SV0M95", "参数错误", "服务调用参数错误"),
	SV0M94("SV0M94", "错误来源", "Syscode错误"),
	SV0M93("SV0M93", "鉴权失败", "用户名或密码错误"),
	SV0M92("SV0M92", "参数不足", "必填参数不足"),
	SV0M91("SV0M91", "标志位出错", "Flag标志位出错");

	public String mean() {
		return mean;
	}


	public String code() {
		return code;
	}

	public String description() {
		return description;
	}

	ServciceResponseParams(String code, String mean, String desc) {
		this.code = code;
		this.mean = mean;
		this.description = desc;
	}

	String mean;
	String code;
	String description;

}
