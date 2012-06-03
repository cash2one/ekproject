package newm.vo.edu;

import java.util.Date;
import java.util.List;

import newm.dao.entity.edu.XjSchoolEntity;
import esfw.core.framework.controller.PageVo;
import esfw.core.framework.controller.ViewObject;
import esfw.core.framework.dao.mapper.OrderItem;

/**
 * 
 * 描述：模版代码 （负责页面传值，查询等 值域 ）
 * @author Ethan.Lam
 * @dateTime 2012-6-2
 *
 */
public class XjSchoolVo extends XjSchoolEntity implements ViewObject {

	private Date createTime1; //时间的区间查询
	
	private Date createTime2;
	
	private Date establishDate1; //时间的区间查询
	
	private Date establishDate2;
	
	
	public XjSchoolVo(){
        this.setTownId(-1); //所属镇区 
        this.setSchoolType(-1); //学校类别(1国家重点、2省重点、3市重点、4县重点、5少数民族学校、6其他) 
        this.setSchoolMode (-1); //办学模式(1教育部门和集体办、2社会力量办、3其他部门办) 
        this.setAddress (-1); //所在地（1城市、2农村、3县镇、4其他） 
        this.setSchoolClass (-1); //学校类型（1小学、2独立设置少数民族小学、3一贯制学校小学部、4小学教学点
        this.setSchoolBank (-1); //学校等级（1国家级、2省级、3市级、4县级、5其他） 
        this.setLearnyear (-1); //学制(小学为6年，初高中为3年) 
        this.setAreaId (-1); //地区id 
        this.setSection (-1); //学段:0幼儿园，1小学，2初中，3高中，4综合 
        this.setIsLong (-1); //
        this.setSiId (-1); //SIID 
	}
	
	public PageVo getPageVo() {
	    return pageVo;
	}
    
    private List<OrderItem> orderList; //排序控制

	public List<OrderItem> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderItem> orderList) {
		this.orderList = orderList;
	}

	public Date getCreateTime1() {
		return createTime1;
	}

	public void setCreateTime1(Date createTime1) {
		this.createTime1 = createTime1;
	}

	public Date getCreateTime2() {
		return createTime2;
	}

	public void setCreateTime2(Date createTime2) {
		this.createTime2 = createTime2;
	}

	public Date getEstablishDate1() {
		return establishDate1;
	}

	public void setEstablishDate1(Date establishDate1) {
		this.establishDate1 = establishDate1;
	}

	public Date getEstablishDate2() {
		return establishDate2;
	}

	public void setEstablishDate2(Date establishDate2) {
		this.establishDate2 = establishDate2;
	}
	
	
}
