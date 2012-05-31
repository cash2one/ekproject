package test.edu.dao.entity.edu;

import java.io.Serializable;
import java.util.*;

/**
 * @description 学校信息 对应的实体
 * @version v1.0
 * @author Qtone  
 * @CreateTime Thu Jul 14 12:18:52 CST 2011
 */
public class XjSchoolEntry implements Serializable {
   
     
         private long  id; //学校ID 
         private long  townId; //所属镇区 
         private String  schoolName; //学校的名称 
         private long  schoolType; //学校类别(1国家重点、2省重点、3市重点、4县重点、5少数民族学校、6其他) 
         private Date  establishDate; //建校时间 
         private long  schoolMode; //办学模式(1教育部门和集体办、2社会力量办、3其他部门办) 
         private long  address; //所在地（1城市、2农村、3县镇、4其他） 
         private long  schoolClass; //学校类型（1小学、2独立设置少数民族小学、3一贯制学校小学部、4小学教学点5其他学校附设小学班、6完全中学、7高级中学、8初级中学、9一贯制学校10其他学校附设初中班、11少数民族完全中学、12少数民族高级中学13少数民族初级中学、14少数民族一贯制学校、15职业初高中合设16职业高中、17职业初中、18普通中学附设职业班、19其它单位20学校职业班、21少数民职业初高中合设、22少数民族职业高中23少数民族职业初中、24幼儿园、25独立少数民族幼儿园、26独立设置学前班27盲人学校、28聋人学校、29弱智学校、30特殊教育其他学校、31小学附设特教班32初中附设特教班、33工读学校、34普通中等专业技术学校、35成人中等专业技术学校、36其他） 
         private long  schoolBank; //学校等级（1国家级、2省级、3市级、4县级、5其他） 
         private String  schoolAddr; //学校地址 
         private String  postCode; //邮编 
         private String  phone; //电话 
         private String  website; //网址 
         private String  email; //E_MAIL 
         private String  schoolMaster; //校长 
         private String  mobie; //联系电话 
         private long  learnyear; //学制(小学为6年，初高中为3年) 
         private long  areaId; //地区id 
         private long  section; //学段:0幼儿园，1小学，2初中，3高中，4综合 
         private Date  createTime; //创建这个学校的时间 
         private String  adcEcCode; //对应BOSS系统集团编号 
         private String  schCode; // 
         private String  shortName; // 
         private long  isLong; //
    
     
          private long  siId; //SIID 
          private String  siName; //公司名称 
          private String  townName; //镇区名称 
          private String  areaName; //区域名称 
          private String  areaCode; //区域代码 
          private String  areaAbb; //区域简写
     
        //默认空构造函数
	  public XjSchoolEntry(){
	
	  }
	
	
	
	
	 
         /**
          * @param id 学校ID
          */
         public void setId(long id){
	        this.id=id;    
         }
         /**
          * @return id 学校ID
          */
         public long getId(  ){ 
	        return this.id;    
         }
     
         /**
          * @param townId 所属镇区
          */
         public void setTownId(long townId){
	        this.townId=townId;    
         }
         /**
          * @return townId 所属镇区
          */
         public long getTownId(  ){ 
	        return this.townId;    
         }
     
         /**
          * @param schoolName 学校的名称
          */
         public void setSchoolName(String schoolName){
	        this.schoolName=schoolName;    
         }
         /**
          * @return schoolName 学校的名称
          */
         public String getSchoolName(  ){ 
	        return this.schoolName;    
         }
     
         /**
          * @param schoolType 学校类别(1国家重点、2省重点、3市重点、4县重点、5少数民族学校、6其他)
          */
         public void setSchoolType(long schoolType){
	        this.schoolType=schoolType;    
         }
         /**
          * @return schoolType 学校类别(1国家重点、2省重点、3市重点、4县重点、5少数民族学校、6其他)
          */
         public long getSchoolType(  ){ 
	        return this.schoolType;    
         }
     
         /**
          * @param establishDate 建校时间
          */
         public void setEstablishDate(Date establishDate){
	        this.establishDate=establishDate;    
         }
         /**
          * @return establishDate 建校时间
          */
         public Date getEstablishDate(  ){ 
	        return this.establishDate;    
         }
     
         /**
          * @param schoolMode 办学模式(1教育部门和集体办、2社会力量办、3其他部门办)
          */
         public void setSchoolMode(long schoolMode){
	        this.schoolMode=schoolMode;    
         }
         /**
          * @return schoolMode 办学模式(1教育部门和集体办、2社会力量办、3其他部门办)
          */
         public long getSchoolMode(  ){ 
	        return this.schoolMode;    
         }
     
         /**
          * @param address 所在地（1城市、2农村、3县镇、4其他）
          */
         public void setAddress(long address){
	        this.address=address;    
         }
         /**
          * @return address 所在地（1城市、2农村、3县镇、4其他）
          */
         public long getAddress(  ){ 
	        return this.address;    
         }
     
         /**
          * @param schoolClass 学校类型（1小学、2独立设置少数民族小学、3一贯制学校小学部、4小学教学点5其他学校附设小学班、6完全中学、7高级中学、8初级中学、9一贯制学校10其他学校附设初中班、11少数民族完全中学、12少数民族高级中学13少数民族初级中学、14少数民族一贯制学校、15职业初高中合设16职业高中、17职业初中、18普通中学附设职业班、19其它单位20学校职业班、21少数民职业初高中合设、22少数民族职业高中23少数民族职业初中、24幼儿园、25独立少数民族幼儿园、26独立设置学前班27盲人学校、28聋人学校、29弱智学校、30特殊教育其他学校、31小学附设特教班32初中附设特教班、33工读学校、34普通中等专业技术学校、35成人中等专业技术学校、36其他）
          */
         public void setSchoolClass(long schoolClass){
	        this.schoolClass=schoolClass;    
         }
         /**
          * @return schoolClass 学校类型（1小学、2独立设置少数民族小学、3一贯制学校小学部、4小学教学点5其他学校附设小学班、6完全中学、7高级中学、8初级中学、9一贯制学校10其他学校附设初中班、11少数民族完全中学、12少数民族高级中学13少数民族初级中学、14少数民族一贯制学校、15职业初高中合设16职业高中、17职业初中、18普通中学附设职业班、19其它单位20学校职业班、21少数民职业初高中合设、22少数民族职业高中23少数民族职业初中、24幼儿园、25独立少数民族幼儿园、26独立设置学前班27盲人学校、28聋人学校、29弱智学校、30特殊教育其他学校、31小学附设特教班32初中附设特教班、33工读学校、34普通中等专业技术学校、35成人中等专业技术学校、36其他）
          */
         public long getSchoolClass(  ){ 
	        return this.schoolClass;    
         }
     
         /**
          * @param schoolBank 学校等级（1国家级、2省级、3市级、4县级、5其他）
          */
         public void setSchoolBank(long schoolBank){
	        this.schoolBank=schoolBank;    
         }
         /**
          * @return schoolBank 学校等级（1国家级、2省级、3市级、4县级、5其他）
          */
         public long getSchoolBank(  ){ 
	        return this.schoolBank;    
         }
     
         /**
          * @param schoolAddr 学校地址
          */
         public void setSchoolAddr(String schoolAddr){
	        this.schoolAddr=schoolAddr;    
         }
         /**
          * @return schoolAddr 学校地址
          */
         public String getSchoolAddr(  ){ 
	        return this.schoolAddr;    
         }
     
         /**
          * @param postCode 邮编
          */
         public void setPostCode(String postCode){
	        this.postCode=postCode;    
         }
         /**
          * @return postCode 邮编
          */
         public String getPostCode(  ){ 
	        return this.postCode;    
         }
     
         /**
          * @param phone 电话
          */
         public void setPhone(String phone){
	        this.phone=phone;    
         }
         /**
          * @return phone 电话
          */
         public String getPhone(  ){ 
	        return this.phone;    
         }
     
         /**
          * @param website 网址
          */
         public void setWebsite(String website){
	        this.website=website;    
         }
         /**
          * @return website 网址
          */
         public String getWebsite(  ){ 
	        return this.website;    
         }
     
         /**
          * @param email E_MAIL
          */
         public void setEmail(String email){
	        this.email=email;    
         }
         /**
          * @return email E_MAIL
          */
         public String getEmail(  ){ 
	        return this.email;    
         }
     
         /**
          * @param schoolMaster 校长
          */
         public void setSchoolMaster(String schoolMaster){
	        this.schoolMaster=schoolMaster;    
         }
         /**
          * @return schoolMaster 校长
          */
         public String getSchoolMaster(  ){ 
	        return this.schoolMaster;    
         }
     
         /**
          * @param mobie 联系电话
          */
         public void setMobie(String mobie){
	        this.mobie=mobie;    
         }
         /**
          * @return mobie 联系电话
          */
         public String getMobie(  ){ 
	        return this.mobie;    
         }
     
         /**
          * @param learnyear 学制(小学为6年，初高中为3年)
          */
         public void setLearnyear(long learnyear){
	        this.learnyear=learnyear;    
         }
         /**
          * @return learnyear 学制(小学为6年，初高中为3年)
          */
         public long getLearnyear(  ){ 
	        return this.learnyear;    
         }
     
         /**
          * @param areaId 地区id
          */
         public void setAreaId(long areaId){
	        this.areaId=areaId;    
         }
         /**
          * @return areaId 地区id
          */
         public long getAreaId(  ){ 
	        return this.areaId;    
         }
     
         /**
          * @param section 学段:0幼儿园，1小学，2初中，3高中，4综合
          */
         public void setSection(long section){
	        this.section=section;    
         }
         /**
          * @return section 学段:0幼儿园，1小学，2初中，3高中，4综合
          */
         public long getSection(  ){ 
	        return this.section;    
         }
     
         /**
          * @param createTime 创建这个学校的时间
          */
         public void setCreateTime(Date createTime){
	        this.createTime=createTime;    
         }
         /**
          * @return createTime 创建这个学校的时间
          */
         public Date getCreateTime(  ){ 
	        return this.createTime;    
         }
     
         /**
          * @param adcEcCode 对应BOSS系统集团编号
          */
         public void setAdcEcCode(String adcEcCode){
	        this.adcEcCode=adcEcCode;    
         }
         /**
          * @return adcEcCode 对应BOSS系统集团编号
          */
         public String getAdcEcCode(  ){ 
	        return this.adcEcCode;    
         }
     
         /**
          * @param schCode 
          */
         public void setSchCode(String schCode){
	        this.schCode=schCode;    
         }
         /**
          * @return schCode 
          */
         public String getSchCode(  ){ 
	        return this.schCode;    
         }
     
         /**
          * @param shortName 
          */
         public void setShortName(String shortName){
	        this.shortName=shortName;    
         }
         /**
          * @return shortName 
          */
         public String getShortName(  ){ 
	        return this.shortName;    
         }
     
         /**
          * @param isLong 
          */
         public void setIsLong(long isLong){
	        this.isLong=isLong;    
         }
         /**
          * @return isLong 
          */
         public long getIsLong(  ){ 
	        return this.isLong;    
         }
    
    
      
         /**
         * @param siId SIID
         */
         public void setSiId(long siId){
	       this.siId=siId;    
         }
         /**
         * @return siId SIID
         */
         public long getSiId(  ){ 
	        return this.siId;    
         }
     
         /**
         * @param siName 公司名称
         */
         public void setSiName(String siName){
	       this.siName=siName;    
         }
         /**
         * @return siName 公司名称
         */
         public String getSiName(  ){ 
	        return this.siName;    
         }
     
         /**
         * @param townName 镇区名称
         */
         public void setTownName(String townName){
	       this.townName=townName;    
         }
         /**
         * @return townName 镇区名称
         */
         public String getTownName(  ){ 
	        return this.townName;    
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
         * @param areaCode 区域代码
         */
         public void setAreaCode(String areaCode){
	       this.areaCode=areaCode;    
         }
         /**
         * @return areaCode 区域代码
         */
         public String getAreaCode(  ){ 
	        return this.areaCode;    
         }
     
         /**
         * @param areaAbb 区域简写
         */
         public void setAreaAbb(String areaAbb){
	       this.areaAbb=areaAbb;    
         }
         /**
         * @return areaAbb 区域简写
         */
         public String getAreaAbb(  ){ 
	        return this.areaAbb;    
         }
    

}
