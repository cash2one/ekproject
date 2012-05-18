package qtone.xxt.dao.entity.amass;

import java.io.Serializable;
import java.util.*;

/**
 * @description 教师积分 信息 对应的实体
 * @version v1.0
 * @author Qtone  
 * @CreateTime Fri May 18 09:41:49 CST 2012
 */
public class AmassCentEntry implements Serializable {
   
     
         private long  id; // 
         private long  areaId; // 
         private String  userId; //教师帐号 
         private String  userName; //教师名称 
         private long  siId; //SiId 
         private long  schoolId; //学校ID 
         private String  schoolName; //学校名 
         private long  year; //年 
         private long  month; //月 
         private long  cent; //该月的积分 
         private long  isLeader; //表am_cent里面得is_leader=2为计算出来得积分 汇总核算教师该月获得总积分 
         private String  insertuserid; // 
         private Date  dt; //创建时间 
         private long  amhId; // 
         private long  isConfirm; //是否是有效积分
    
     
          private String  areaName; //区域名称 
          private String  company; //SI公司名称
     
        //默认空构造函数
	  public AmassCentEntry(){
	
	  }
	
	
	
	
	 
         /**
          * @param id 
          */
         public void setId(long id){
	        this.id=id;    
         }
         /**
          * @return id 
          */
         public long getId(  ){ 
	        return this.id;    
         }
     
         /**
          * @param areaId 
          */
         public void setAreaId(long areaId){
	        this.areaId=areaId;    
         }
         /**
          * @return areaId 
          */
         public long getAreaId(  ){ 
	        return this.areaId;    
         }
     
         /**
          * @param userId 教师帐号
          */
         public void setUserId(String userId){
	        this.userId=userId;    
         }
         /**
          * @return userId 教师帐号
          */
         public String getUserId(  ){ 
	        return this.userId;    
         }
     
         /**
          * @param userName 教师名称
          */
         public void setUserName(String userName){
	        this.userName=userName;    
         }
         /**
          * @return userName 教师名称
          */
         public String getUserName(  ){ 
	        return this.userName;    
         }
     
         /**
          * @param siId SiId
          */
         public void setSiId(long siId){
	        this.siId=siId;    
         }
         /**
          * @return siId SiId
          */
         public long getSiId(  ){ 
	        return this.siId;    
         }
     
         /**
          * @param schoolId 学校ID
          */
         public void setSchoolId(long schoolId){
	        this.schoolId=schoolId;    
         }
         /**
          * @return schoolId 学校ID
          */
         public long getSchoolId(  ){ 
	        return this.schoolId;    
         }
     
         /**
          * @param schoolName 学校名
          */
         public void setSchoolName(String schoolName){
	        this.schoolName=schoolName;    
         }
         /**
          * @return schoolName 学校名
          */
         public String getSchoolName(  ){ 
	        return this.schoolName;    
         }
     
         /**
          * @param year 年
          */
         public void setYear(long year){
	        this.year=year;    
         }
         /**
          * @return year 年
          */
         public long getYear(  ){ 
	        return this.year;    
         }
     
         /**
          * @param month 月
          */
         public void setMonth(long month){
	        this.month=month;    
         }
         /**
          * @return month 月
          */
         public long getMonth(  ){ 
	        return this.month;    
         }
     
         /**
          * @param cent 该月的积分
          */
         public void setCent(long cent){
	        this.cent=cent;    
         }
         /**
          * @return cent 该月的积分
          */
         public long getCent(  ){ 
	        return this.cent;    
         }
     
         /**
          * @param isLeader 表am_cent里面得is_leader=2为计算出来得积分 汇总核算教师该月获得总积分
          */
         public void setIsLeader(long isLeader){
	        this.isLeader=isLeader;    
         }
         /**
          * @return isLeader 表am_cent里面得is_leader=2为计算出来得积分 汇总核算教师该月获得总积分
          */
         public long getIsLeader(  ){ 
	        return this.isLeader;    
         }
     
         /**
          * @param insertuserid 
          */
         public void setInsertuserid(String insertuserid){
	        this.insertuserid=insertuserid;    
         }
         /**
          * @return insertuserid 
          */
         public String getInsertuserid(  ){ 
	        return this.insertuserid;    
         }
     
         /**
          * @param dt 创建时间
          */
         public void setDt(Date dt){
	        this.dt=dt;    
         }
         /**
          * @return dt 创建时间
          */
         public Date getDt(  ){ 
	        return this.dt;    
         }
     
         /**
          * @param amhId 
          */
         public void setAmhId(long amhId){
	        this.amhId=amhId;    
         }
         /**
          * @return amhId 
          */
         public long getAmhId(  ){ 
	        return this.amhId;    
         }
     
         /**
          * @param isConfirm 是否是有效积分
          */
         public void setIsConfirm(long isConfirm){
	        this.isConfirm=isConfirm;    
         }
         /**
          * @return isConfirm 是否是有效积分
          */
         public long getIsConfirm(  ){ 
	        return this.isConfirm;    
         }
    
    
      
         /**
         * @param areaName 区域名称
         */
         public void setAreaName(String areaName){
	       this.areaName=areaName;    
         }
         /**
         * @return areaName 区域名称
         */
         public String getAreaName(  ){ 
	        return this.areaName;    
         }
     
         /**
         * @param company SI公司名称
         */
         public void setCompany(String company){
	       this.company=company;    
         }
         /**
         * @return company SI公司名称
         */
         public String getCompany(  ){ 
	        return this.company;    
         }
    

}
