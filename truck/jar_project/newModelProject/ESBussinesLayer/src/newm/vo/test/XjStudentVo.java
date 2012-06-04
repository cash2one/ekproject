package newm.vo.test;

import java.util.*;

import newm.dao.entity.test.XjStudentEntity;

import esfw.core.framework.controller.PageVo;
import esfw.core.framework.controller.ViewObject;
import esfw.core.framework.dao.mapper.OrderItem;

/**
 * @description 测试 对应的 ViewOject 实体 vo
 * @version v1.0.0
 * @author Ethanlam  
 * @CreateTime Mon Jun 04 13:26:23 GMT 2012
 */
public class   XjStudentVo  extends XjStudentEntity implements ViewObject{
   
     
         private Date  birthday1; //出生日期 -- 开始时间 
         private Date  birthday2; //出生日期 -- 结束时间  
         private Date  inSchoolDate1; //入校日期 -- 开始时间 
         private Date  inSchoolDate2; //入校日期 -- 结束时间  
         private Date  createTime1; //创建时间 -- 开始时间 
         private Date  createTime2; //创建时间 -- 结束时间  
         private Date  lastUpdateTimestamp1; //最后更新日期 -- 开始时间 
         private Date  lastUpdateTimestamp2; //最后更新日期 -- 结束时间 
    
    
          
      
      private List<OrderItem> orderList; //排序控制
      
      //默认空构造函数
	  public XjStudentVo(){
	
	    
               this.setId(-1);
 
               this.setSex(-1);
 
               this.setNation(-1);
 
               this.setBloodType(-1);
 
               this.setPolitics(-1);
 
               this.setHomeMacau(-1);
 
               this.setIcStatus(-1);
 
               this.setCallTime(-1);
 
               this.setStuType(-1);
 
               this.setSeatNumber(-1);

  
    
     
             this.setSchoolId(-1);
 
             this.setClassId(-1);

	
	  }
		
	
	 
         /**
          * @param birthday1 出生日期 -- 开始时间 
          */
         public void setBirthday1(Date birthday){
	        this.birthday1=birthday;    
         }
         /**
          * @return birthday1 出生日期 -- 结束时间 
          */
         public Date getBirthday1( ){ 
	        return this.birthday1;    
         }
         
         /**
          * @param birthday2 出生日期 -- 开始时间 
          */
         public void setBirthday2(Date birthday){
	        this.birthday2=birthday;    
         }
         
         /**
          * @return birthday2 出生日期 -- 结束时间 
          */
         public Date getBirthday2( ){ 
	        return this.birthday2;    
         }
        
     
         /**
          * @param inSchoolDate1 入校日期 -- 开始时间 
          */
         public void setInSchoolDate1(Date inSchoolDate){
	        this.inSchoolDate1=inSchoolDate;    
         }
         /**
          * @return inSchoolDate1 入校日期 -- 结束时间 
          */
         public Date getInSchoolDate1( ){ 
	        return this.inSchoolDate1;    
         }
         
         /**
          * @param inSchoolDate2 入校日期 -- 开始时间 
          */
         public void setInSchoolDate2(Date inSchoolDate){
	        this.inSchoolDate2=inSchoolDate;    
         }
         
         /**
          * @return inSchoolDate2 入校日期 -- 结束时间 
          */
         public Date getInSchoolDate2( ){ 
	        return this.inSchoolDate2;    
         }
        
     
         /**
          * @param createTime1 创建时间 -- 开始时间 
          */
         public void setCreateTime1(Date createTime){
	        this.createTime1=createTime;    
         }
         /**
          * @return createTime1 创建时间 -- 结束时间 
          */
         public Date getCreateTime1( ){ 
	        return this.createTime1;    
         }
         
         /**
          * @param createTime2 创建时间 -- 开始时间 
          */
         public void setCreateTime2(Date createTime){
	        this.createTime2=createTime;    
         }
         
         /**
          * @return createTime2 创建时间 -- 结束时间 
          */
         public Date getCreateTime2( ){ 
	        return this.createTime2;    
         }
        
     
         /**
          * @param lastUpdateTimestamp1 最后更新日期 -- 开始时间 
          */
         public void setLastUpdateTimestamp1(Date lastUpdateTimestamp){
	        this.lastUpdateTimestamp1=lastUpdateTimestamp;    
         }
         /**
          * @return lastUpdateTimestamp1 最后更新日期 -- 结束时间 
          */
         public Date getLastUpdateTimestamp1( ){ 
	        return this.lastUpdateTimestamp1;    
         }
         
         /**
          * @param lastUpdateTimestamp2 最后更新日期 -- 开始时间 
          */
         public void setLastUpdateTimestamp2(Date lastUpdateTimestamp){
	        this.lastUpdateTimestamp2=lastUpdateTimestamp;    
         }
         
         /**
          * @return lastUpdateTimestamp2 最后更新日期 -- 结束时间 
          */
         public Date getLastUpdateTimestamp2( ){ 
	        return this.lastUpdateTimestamp2;    
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
