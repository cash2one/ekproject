package model.vo;

import java.util.List;

import model.entity.ModelEntity;
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
public class ModelVo extends ModelEntity implements ViewObject {

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
	
    
}
