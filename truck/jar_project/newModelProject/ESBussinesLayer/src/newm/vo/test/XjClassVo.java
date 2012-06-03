package newm.vo.test;

import java.util.*;

import newm.dao.entity.test.XjClassEntity;

import esfw.core.framework.controller.PageVo;
import esfw.core.framework.controller.ViewObject;
import esfw.core.framework.dao.mapper.OrderItem;

/**
 * @description 班级信息 对应的 ViewOject 实体 vo
 * @version v1.0.0
 * @author Ethanlam  
 * @CreateTime Sun Jun 03 11:05:32 GMT 2012
 */
public class   XjClassVo  extends XjClassEntity implements ViewObject{
   
     
         private Date  establishDate1; //创建日期(由系统自动生成，生成规则：属于小一、初一、高一、一年级的班级，创建日期是“当前年份－09－01”，其它的一次类推：小二，初二，高二，二年级则是“（当前年份-1）-09-01”，如小一（1）班2006-09-01，小二（1）班2005-09-01) -- 开始时间 
         private Date  establishDate2; //创建日期(由系统自动生成，生成规则：属于小一、初一、高一、一年级的班级，创建日期是“当前年份－09－01”，其它的一次类推：小二，初二，高二，二年级则是“（当前年份-1）-09-01”，如小一（1）班2006-09-01，小二（1）班2005-09-01) -- 结束时间  
         private Date  createTime1; //创建班级的时间 -- 开始时间 
         private Date  createTime2; //创建班级的时间 -- 结束时间  
         private Date  lastUpdateTimestamp1; // -- 开始时间 
         private Date  lastUpdateTimestamp2; // -- 结束时间 
    
    
          
      
      private List<OrderItem> orderList; //排序控制
      
      //默认空构造函数
	  public XjClassVo(){
	
	    
               this.setId(-1);
 
               this.setGradeId(-1);
 
               this.setSchoolId(-1);
 
               this.setClassType(-1);
 
               this.setLanguage(-1);
 
               this.setUserId(-1);
 
               this.setIsGraduate(-1);
 
               this.setOrderno(-1);
 
               this.setInSchool(-1);
 
               this.setSection(-1);
 
               this.setPreid(-1);
 
               this.setGraduateDel(-1);

  
    
    
	
	  }
		
	
	 
         /**
          * @param establishDate1 创建日期(由系统自动生成，生成规则：属于小一、初一、高一、一年级的班级，创建日期是“当前年份－09－01”，其它的一次类推：小二，初二，高二，二年级则是“（当前年份-1）-09-01”，如小一（1）班2006-09-01，小二（1）班2005-09-01) -- 开始时间 
          */
         public void setEstablishDate1(Date establishDate){
	        this.establishDate1=establishDate;    
         }
         /**
          * @return establishDate1 创建日期(由系统自动生成，生成规则：属于小一、初一、高一、一年级的班级，创建日期是“当前年份－09－01”，其它的一次类推：小二，初二，高二，二年级则是“（当前年份-1）-09-01”，如小一（1）班2006-09-01，小二（1）班2005-09-01) -- 结束时间 
          */
         public Date getEstablishDate1( ){ 
	        return this.establishDate1;    
         }
         
         /**
          * @param establishDate2 创建日期(由系统自动生成，生成规则：属于小一、初一、高一、一年级的班级，创建日期是“当前年份－09－01”，其它的一次类推：小二，初二，高二，二年级则是“（当前年份-1）-09-01”，如小一（1）班2006-09-01，小二（1）班2005-09-01) -- 开始时间 
          */
         public void setEstablishDate2(Date establishDate){
	        this.establishDate2=establishDate;    
         }
         
         /**
          * @return establishDate2 创建日期(由系统自动生成，生成规则：属于小一、初一、高一、一年级的班级，创建日期是“当前年份－09－01”，其它的一次类推：小二，初二，高二，二年级则是“（当前年份-1）-09-01”，如小一（1）班2006-09-01，小二（1）班2005-09-01) -- 结束时间 
          */
         public Date getEstablishDate2( ){ 
	        return this.establishDate2;    
         }
        
     
         /**
          * @param createTime1 创建班级的时间 -- 开始时间 
          */
         public void setCreateTime1(Date createTime){
	        this.createTime1=createTime;    
         }
         /**
          * @return createTime1 创建班级的时间 -- 结束时间 
          */
         public Date getCreateTime1( ){ 
	        return this.createTime1;    
         }
         
         /**
          * @param createTime2 创建班级的时间 -- 开始时间 
          */
         public void setCreateTime2(Date createTime){
	        this.createTime2=createTime;    
         }
         
         /**
          * @return createTime2 创建班级的时间 -- 结束时间 
          */
         public Date getCreateTime2( ){ 
	        return this.createTime2;    
         }
        
     
         /**
          * @param lastUpdateTimestamp1  -- 开始时间 
          */
         public void setLastUpdateTimestamp1(Date lastUpdateTimestamp){
	        this.lastUpdateTimestamp1=lastUpdateTimestamp;    
         }
         /**
          * @return lastUpdateTimestamp1  -- 结束时间 
          */
         public Date getLastUpdateTimestamp1( ){ 
	        return this.lastUpdateTimestamp1;    
         }
         
         /**
          * @param lastUpdateTimestamp2  -- 开始时间 
          */
         public void setLastUpdateTimestamp2(Date lastUpdateTimestamp){
	        this.lastUpdateTimestamp2=lastUpdateTimestamp;    
         }
         
         /**
          * @return lastUpdateTimestamp2  -- 结束时间 
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
