package cn.qtone.xxt.csop.dao.impl;

import cn.qtone.xxt.csop.dao.inter.ResultRow;

/**
 * ҵ���������ѯ���
 * @author linhansheng
 *
 */
public class TransCustomerRow implements ResultRow {

	/**
	 * ҵ������ ҵ��˿� ҵ�����ݼ�� �ʷѣ�Ԫ�� �Ʒ����� ��ͨ��ʽ 
	 * ����ʱ��    ҵ��ʹ��״̬    �۷�ʱ��     Ӫ��������Ϣ
	 */

	// ҵ������
	String name;
	// ҵ��˿�
	String port;
	// ҵ�����ݼ��
	String desc;
	// �ʷѣ�Ԫ�� 
	String charge;
	// �Ʒ�����
	String chargeType;

	// ��ͨ��ʽ
	String openType;

	// ����ʱ��
	String orderTime;

	// ҵ��ʹ��״̬
	String serviceState;

	// �۷�ʱ��
	String payTime;

	// Ӫ��������Ϣ
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
