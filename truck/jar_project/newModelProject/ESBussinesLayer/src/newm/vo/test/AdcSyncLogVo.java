package newm.vo.test;

import java.util.*;

import newm.dao.entity.test.AdcSyncLogEntity;

import esfw.core.framework.controller.PageVo;
import esfw.core.framework.controller.ViewObject;
import esfw.core.framework.dao.mapper.OrderItem;

/**
 * @description fsdf 对应的 ViewOject 实体 vo
 * @version v1.0.0
 * @author Ethanlam  
 * @CreateTime Mon Jun 04 13:00:20 GMT 2012
 */
public class   AdcSyncLogVo  extends AdcSyncLogEntity implements ViewObject{
   
     
         private Date  dt1; //创建时间 -- 开始时间 
         private Date  dt2; //创建时间 -- 结束时间 
    
    
          
      
      private List<OrderItem> orderList; //排序控制
      
      //默认空构造函数
	  public AdcSyncLogVo(){
	
	    
               this.setId(-1);
 
               this.setType(-1);

  
    
    
	
	  }
		
	
	 
         /**
          * @param dt1 创建时间 -- 开始时间 
          */
         public void setDt1(Date dt){
	        this.dt1=dt;    
         }
         /**
          * @return dt1 创建时间 -- 结束时间 
          */
         public Date getDt1( ){ 
	        return this.dt1;    
         }
         
         /**
          * @param dt2 创建时间 -- 开始时间 
          */
         public void setDt2(Date dt){
	        this.dt2=dt;    
         }
         
         /**
          * @return dt2 创建时间 -- 结束时间 
          */
         public Date getDt2( ){ 
	        return this.dt2;    
         }
        
    
    
    
     
    
    //页面的分页 Vo 信息
    public PageVo getPageVo() {
	    return pageVo;
	}
    
    //列表查询的排序控制
	public List<OrderItem> getOrderList() {
		return orderList;
	}

    //设置列表查询的排序
	public void setOrderList(List<OrderItem> orderList) {
		this.orderList = orderList;
	}

}
