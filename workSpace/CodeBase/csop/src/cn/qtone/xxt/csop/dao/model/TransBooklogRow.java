package cn.qtone.xxt.csop.dao.model;

import cn.qtone.xxt.csop.inter.ResultRow;

/**
 * 业务定制情况查询结果
 * @author linhansheng
 *
 */
public class TransBooklogRow implements ResultRow {

	/**
	 * 业务名称 业务端口 业务内容简介 资费（元） 计费类型 开通方式 
	 * 订购时间    业务使用状态    扣费时间     营销关联信息
	 */

	// 业务名称
	String name;
	// 业务端口
	String port;
	// 业务内容简介
	String desc;
	// 资费（元） 
	String charge;
	// 计费类型
	String chargeType;

	// 开通方式
	String openType;

	// 订购时间
	String orderTime;

	// 业务使用状态
	String serviceState;

	// 扣费时间
	String payTime;

	// 营销关联信息
	String saleRelationShip;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public String getOpenType() {
		return openType;
	}

	public void setOpenType(String openType) {
		this.openType = openType;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getServiceState() {
		return serviceState;
	}

	public void setServiceState(String serviceState) {
		this.serviceState = serviceState;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getSaleRelationShip() {
		return saleRelationShip;
	}

	public void setSaleRelationShip(String saleRelationShip) {
		this.saleRelationShip = saleRelationShip;
	}
	
	public String rowToXml() {

		return null;
	}

}
