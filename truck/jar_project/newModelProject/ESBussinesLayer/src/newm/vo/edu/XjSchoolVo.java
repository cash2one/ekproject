package newm.vo.edu;

import java.util.*;

import newm.dao.entity.edu.XjSchoolEntity;

import esfw.core.framework.controller.PageVo;
import esfw.core.framework.controller.ViewObject;
import esfw.core.framework.dao.mapper.OrderItem;

/**
 * @description 学校信息 对应的 ViewOject 实体 vo
 * @version v1.0
 * @author Qtone
 * @CreateTime Sun Jun 03 10:01:44 GMT 2012
 */
public class XjSchoolVo extends XjSchoolEntity implements ViewObject {

	private Date establishDate1; // 建校时间 -- 开始时间
	private Date establishDate2; // 建校时间 -- 结束时间
	private Date createTime1; // 创建这个学校的时间 -- 开始时间
	private Date createTime2; // 创建这个学校的时间 -- 结束时间

	private List<OrderItem> orderList; // 排序控制

	// 默认空构造函数
	public XjSchoolVo() {

		this.setId(-1);

		this.setTownId(-1);

		this.setSchoolType(-1);

		this.setSchoolMode(-1);

		this.setAddress(-1);

		this.setSchoolClass(-1);

		this.setSchoolBank(-1);

		this.setLearnyear(-1);

		this.setAreaId(-1);

		this.setSection(-1);

		this.setIsLong(-1);

		this.setSiId(-1);

	}

	/**
	 * @param establishDate1
	 *            建校时间 -- 开始时间
	 */
	public void setEstablishDate1(Date establishDate) {
		this.establishDate1 = establishDate;
	}

	/**
	 * @return establishDate1 建校时间 -- 结束时间
	 */
	public Date getEstablishDate1() {
		return this.establishDate1;
	}

	/**
	 * @param establishDate2
	 *            建校时间 -- 开始时间
	 */
	public void setEstablishDate2(Date establishDate) {
		this.establishDate2 = establishDate;
	}

	/**
	 * @return establishDate2 建校时间 -- 结束时间
	 */
	public Date getEstablishDate2() {
		return this.establishDate2;
	}

	/**
	 * @param createTime1
	 *            创建这个学校的时间 -- 开始时间
	 */
	public void setCreateTime1(Date createTime) {
		this.createTime1 = createTime;
	}

	/**
	 * @return createTime1 创建这个学校的时间 -- 结束时间
	 */
	public Date getCreateTime1() {
		return this.createTime1;
	}

	/**
	 * @param createTime2
	 *            创建这个学校的时间 -- 开始时间
	 */
	public void setCreateTime2(Date createTime) {
		this.createTime2 = createTime;
	}

	/**
	 * @return createTime2 创建这个学校的时间 -- 结束时间
	 */
	public Date getCreateTime2() {
		return this.createTime2;
	}

	// 页面的分页 Vo 信息
	public PageVo getPageVo() {
		return pageVo;
	}

	// 列表查询的排序控制
	public List<OrderItem> getOrderList() {
		return orderList;
	}

	// 设置列表查询的排序
	public void setOrderList(List<OrderItem> orderList) {
		this.orderList = orderList;
	}

}