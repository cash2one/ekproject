package cn.qtone.xxt.csop.dao.model;

import cn.qtone.xxt.csop.inter.ResultRow;
import cn.qtone.xxt.csop.util.Checker;

/**
 * 业务定制情况查询结果
 * 
 * @author linhansheng
 * 
 */
public class TransCustomerRow implements ResultRow {

	/**
	 * 业务名称 业务端口 业务内容简介 资费（元） 计费类型 开通方式 订购时间 业务使用状态 扣费时间 营销关联信息
	 */

	// 业务名称
	String name = "";
	// 业务端口
	String port = "";
	// 业务内容简介
	String desc = "";
	// 资费（元）
	String charge = "";
	// 计费类型
	String chargeType = "";

	// 开通方式
	String openType = "";

	// 订购时间
	String orderTime = "";

	// 业务使用状态
	String serviceState = "";

	// 扣费时间
	String payTime = "";

	// 营销关联信息
	String saleRelationShip = "";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (!Checker.isNull(name))
			this.name = name;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		if (!Checker.isNull(port))
			this.port = port;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		if (!Checker.isNull(desc))
			this.desc = desc;
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		if (!Checker.isNull(charge))
			this.charge = charge;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		if (!Checker.isNull(chargeType))
			this.chargeType = chargeType;
	}

	public String getOpenType() {
		return openType;
	}

	public void setOpenType(String openType) {
		if (!Checker.isNull(openType))
			this.openType = openType;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		if (!Checker.isNull(orderTime))
			this.orderTime = orderTime;
	}

	public String getServiceState() {
		return serviceState;
	}

	public void setServiceState(String serviceState) {
		if (!Checker.isNull(serviceState))
			this.serviceState = serviceState;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		if (!Checker.isNull(payTime))
			this.payTime = payTime;
	}

	public String getSaleRelationShip() {
		return saleRelationShip;
	}

	public void setSaleRelationShip(String saleRelationShip) {
		if (!Checker.isNull(saleRelationShip))
			this.saleRelationShip = saleRelationShip;
	}


	public String formColumnData() {
		StringBuffer resultXml = new StringBuffer();
		resultXml.append("<column>");
		resultXml.append("<column1>").append(this.getName()).append("</column1>");
		resultXml.append("<column2>").append(this.getPort()).append("</column2>");
		resultXml.append("<column3>").append(this.getDesc()).append("</column3>");
		resultXml.append("<column4>").append(this.getCharge()).append("</column4>");
		resultXml.append("<column5>").append(this.getChargeType()).append("</column5>");
		resultXml.append("<column6>").append(this.getOpenType()).append("</column6>");
		resultXml.append("<column7>").append(this.getOrderTime()).append("</column7>");
		resultXml.append("<column8>").append(this.getServiceState()).append("</column8>");
		resultXml.append("<column9>").append(this.getPayTime()).append("</column9>");
		resultXml.append("<column10>").append(this.getSaleRelationShip()).append("</column10>");
		resultXml.append("</column>");
		return resultXml.toString();
	}

}
