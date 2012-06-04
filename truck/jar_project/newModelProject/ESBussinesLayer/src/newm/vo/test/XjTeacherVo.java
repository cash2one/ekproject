package newm.vo.test;

import java.util.*;

import newm.dao.entity.test.XjTeacherEntity;

import esfw.core.framework.controller.PageVo;
import esfw.core.framework.controller.ViewObject;
import esfw.core.framework.dao.mapper.OrderItem;

/**
 * @description 测试 对应的 ViewOject 实体 vo
 * @version v1.0.0
 * @author Ethanlam  
 * @CreateTime Mon Jun 04 13:23:19 GMT 2012
 */
public class   XjTeacherVo  extends XjTeacherEntity implements ViewObject{
   
     
         private Date  birthday1; //教师生日 -- 开始时间 
         private Date  birthday2; //教师生日 -- 结束时间  
         private Date  crateTime1; // -- 开始时间 
         private Date  crateTime2; // -- 结束时间  
         private Date  lastupdatepswdate1; // -- 开始时间 
         private Date  lastupdatepswdate2; // -- 结束时间  
         private Date  lastUpdateTimestamp1; // -- 开始时间 
         private Date  lastUpdateTimestamp2; // -- 结束时间  
         private Date  lastLoginTime1; // -- 开始时间 
         private Date  lastLoginTime2; // -- 结束时间  
         private Date  lastupdatepswtime1; // -- 开始时间 
         private Date  lastupdatepswtime2; // -- 结束时间 
    
    
          
      
      private List<OrderItem> orderList; //排序控制
      
      //默认空构造函数
	  public XjTeacherVo(){
	
	    
               this.setId(-1);
 
               this.setSchoolId(-1);
 
               this.setRoleId(-1);
 
               this.setBreceive(-1);
 
               this.setFlag(-1);
 
               this.setCdata(-1);
 
               this.setPreid(-1);
 
               this.setSeatNumber(-1);
 
               this.setPsend(-1);
 
               this.setIsLocked(-1);
 
               this.setLoginSms(-1);
 
               this.setSmsCheck139(-1);
 
               this.setMmsData(-1);
 
               this.setTreceive(-1);
 
               this.setIsbindphone(-1);

  
    
    
	
	  }
		
	
	 
         /**
          * @param birthday1 教师生日 -- 开始时间 
          */
         public void setBirthday1(Date birthday){
	        this.birthday1=birthday;    
         }
         /**
          * @return birthday1 教师生日 -- 结束时间 
          */
         public Date getBirthday1( ){ 
	        return this.birthday1;    
         }
         
         /**
          * @param birthday2 教师生日 -- 开始时间 
          */
         public void setBirthday2(Date birthday){
	        this.birthday2=birthday;    
         }
         
         /**
          * @return birthday2 教师生日 -- 结束时间 
          */
         public Date getBirthday2( ){ 
	        return this.birthday2;    
         }
        
     
         /**
          * @param crateTime1  -- 开始时间 
          */
         public void setCrateTime1(Date crateTime){
	        this.crateTime1=crateTime;    
         }
         /**
          * @return crateTime1  -- 结束时间 
          */
         public Date getCrateTime1( ){ 
	        return this.crateTime1;    
         }
         
         /**
          * @param crateTime2  -- 开始时间 
          */
         public void setCrateTime2(Date crateTime){
	        this.crateTime2=crateTime;    
         }
         
         /**
          * @return crateTime2  -- 结束时间 
          */
         public Date getCrateTime2( ){ 
	        return this.crateTime2;    
         }
        
     
         /**
          * @param lastupdatepswdate1  -- 开始时间 
          */
         public void setLastupdatepswdate1(Date lastupdatepswdate){
	        this.lastupdatepswdate1=lastupdatepswdate;    
         }
         /**
          * @return lastupdatepswdate1  -- 结束时间 
          */
         public Date getLastupdatepswdate1( ){ 
	        return this.lastupdatepswdate1;    
         }
         
         /**
          * @param lastupdatepswdate2  -- 开始时间 
          */
         public void setLastupdatepswdate2(Date lastupdatepswdate){
	        this.lastupdatepswdate2=lastupdatepswdate;    
         }
         
         /**
          * @return lastupdatepswdate2  -- 结束时间 
          */
         public Date getLastupdatepswdate2( ){ 
	        return this.lastupdatepswdate2;    
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
        
     
         /**
          * @param lastLoginTime1  -- 开始时间 
          */
         public void setLastLoginTime1(Date lastLoginTime){
	        this.lastLoginTime1=lastLoginTime;    
         }
         /**
          * @return lastLoginTime1  -- 结束时间 
          */
         public Date getLastLoginTime1( ){ 
	        return this.lastLoginTime1;    
         }
         
         /**
          * @param lastLoginTime2  -- 开始时间 
          */
         public void setLastLoginTime2(Date lastLoginTime){
	        this.lastLoginTime2=lastLoginTime;    
         }
         
         /**
          * @return lastLoginTime2  -- 结束时间 
          */
         public Date getLastLoginTime2( ){ 
	        return this.lastLoginTime2;    
         }
        
     
         /**
          * @param lastupdatepswtime1  -- 开始时间 
          */
         public void setLastupdatepswtime1(Date lastupdatepswtime){
	        this.lastupdatepswtime1=lastupdatepswtime;    
         }
         /**
          * @return lastupdatepswtime1  -- 结束时间 
          */
         public Date getLastupdatepswtime1( ){ 
	        return this.lastupdatepswtime1;    
         }
         
         /**
          * @param lastupdatepswtime2  -- 开始时间 
          */
         public void setLastupdatepswtime2(Date lastupdatepswtime){
	        this.lastupdatepswtime2=lastupdatepswtime;    
         }
         
         /**
          * @return lastupdatepswtime2  -- 结束时间 
          */
         public Date getLastupdatepswtime2( ){ 
	        return this.lastupdatepswtime2;    
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
