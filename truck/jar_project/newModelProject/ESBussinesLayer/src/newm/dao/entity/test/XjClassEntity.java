package newm.dao.entity.test;

import java.util.*;

import esfw.core.framework.dao.GenericEntity;

/**
 * @description 班级信息 对应的实体
 * @version v1.0.0
 * @author Ethanlam  
 * @CreateTime Sun Jun 03 11:05:31 GMT 2012
 */
public class XjClassEntity extends GenericEntity {
   
     
         private long  id; //班级ID 
         private long  gradeId; //所属的年级 
         private long  schoolId; //学校id 
         private long  classType; //班级类型（1正规班,2兴趣班） 
         private long  language; //外语语种（1英语、2日语、3俄语、4德语、5法语、6其他） 
         private long  userId; //班主任编号(用户表的编号) 
         private long  isGraduate; //是否毕业班(1是；0否),在页面要根据年级判断 
         private long  orderno; //排序号 
         private String  classno; //班级序号 
         private Date  establishDate; //创建日期(由系统自动生成，生成规则：属于小一、初一、高一、一年级的班级，创建日期是“当前年份－09－01”，其它的一次类推：小二，初二，高二，二年级则是“（当前年份-1）-09-01”，如小一（1）班2006-09-01，小二（1）班2005-09-01) 
         private long  inSchool; //是否在校(1、是；0、否）,后台操作字段，由系统在做自动毕业时设置。页面上不需要体现，但要提供查询 
         private String  studytype; //(全日制，业余) 
         private long  section; //学段:0幼儿园，1小学，2初中，3高中，4综合 
         private String  className; //班级名(页面上系统自动生成：年级名称+(班级序号)+班,可以由用户修改) 
         private String  exchangeId; //数据交互原始编号（保存其它地市上传数据的原始唯一编号，用户修改或删除该数据） 
         private long  preid; // 
         private String  sepCode; //专业代码(一个专业代码对应着一个专业) 
         private String  fatherSepCode; //专业类别代码(一个专业类别下有多个专业) 
         private Date  createTime; //创建班级的时间 
         private String  learningYear; // 
         private Date  lastUpdateTimestamp; // 
         private String  nickname; // 
         private long  graduateDel; //
    
    
     
        //默认空构造函数
	  public XjClassEntity(){
	
	  }
	
	
	
	
	 
         /**
          * @param id 班级ID
          */
         public void setId(long id){
	        this.id=id;    
         }
         /**
          * @return id 班级ID
          */
         public long getId(  ){ 
	        return this.id;    
         }
     
         /**
          * @param gradeId 所属的年级
          */
         public void setGradeId(long gradeId){
	        this.gradeId=gradeId;    
         }
         /**
          * @return gradeId 所属的年级
          */
         public long getGradeId(  ){ 
	        return this.gradeId;    
         }
     
         /**
          * @param schoolId 学校id
          */
         public void setSchoolId(long schoolId){
	        this.schoolId=schoolId;    
         }
         /**
          * @return schoolId 学校id
          */
         public long getSchoolId(  ){ 
	        return this.schoolId;    
         }
     
         /**
          * @param classType 班级类型（1正规班,2兴趣班）
          */
         public void setClassType(long classType){
	        this.classType=classType;    
         }
         /**
          * @return classType 班级类型（1正规班,2兴趣班）
          */
         public long getClassType(  ){ 
	        return this.classType;    
         }
     
         /**
          * @param language 外语语种（1英语、2日语、3俄语、4德语、5法语、6其他）
          */
         public void setLanguage(long language){
	        this.language=language;    
         }
         /**
          * @return language 外语语种（1英语、2日语、3俄语、4德语、5法语、6其他）
          */
         public long getLanguage(  ){ 
	        return this.language;    
         }
     
         /**
          * @param userId 班主任编号(用户表的编号)
          */
         public void setUserId(long userId){
	        this.userId=userId;    
         }
         /**
          * @return userId 班主任编号(用户表的编号)
          */
         public long getUserId(  ){ 
	        return this.userId;    
         }
     
         /**
          * @param isGraduate 是否毕业班(1是；0否),在页面要根据年级判断
          */
         public void setIsGraduate(long isGraduate){
	        this.isGraduate=isGraduate;    
         }
         /**
          * @return isGraduate 是否毕业班(1是；0否),在页面要根据年级判断
          */
         public long getIsGraduate(  ){ 
	        return this.isGraduate;    
         }
     
         /**
          * @param orderno 排序号
          */
         public void setOrderno(long orderno){
	        this.orderno=orderno;    
         }
         /**
          * @return orderno 排序号
          */
         public long getOrderno(  ){ 
	        return this.orderno;    
         }
     
         /**
          * @param classno 班级序号
          */
         public void setClassno(String classno){
	        this.classno=classno;    
         }
         /**
          * @return classno 班级序号
          */
         public String getClassno(  ){ 
	        return this.classno;    
         }
     
         /**
          * @param establishDate 创建日期(由系统自动生成，生成规则：属于小一、初一、高一、一年级的班级，创建日期是“当前年份－09－01”，其它的一次类推：小二，初二，高二，二年级则是“（当前年份-1）-09-01”，如小一（1）班2006-09-01，小二（1）班2005-09-01)
          */
         public void setEstablishDate(Date establishDate){
	        this.establishDate=establishDate;    
         }
         /**
          * @return establishDate 创建日期(由系统自动生成，生成规则：属于小一、初一、高一、一年级的班级，创建日期是“当前年份－09－01”，其它的一次类推：小二，初二，高二，二年级则是“（当前年份-1）-09-01”，如小一（1）班2006-09-01，小二（1）班2005-09-01)
          */
         public Date getEstablishDate(  ){ 
	        return this.establishDate;    
         }
     
         /**
          * @param inSchool 是否在校(1、是；0、否）,后台操作字段，由系统在做自动毕业时设置。页面上不需要体现，但要提供查询
          */
         public void setInSchool(long inSchool){
	        this.inSchool=inSchool;    
         }
         /**
          * @return inSchool 是否在校(1、是；0、否）,后台操作字段，由系统在做自动毕业时设置。页面上不需要体现，但要提供查询
          */
         public long getInSchool(  ){ 
	        return this.inSchool;    
         }
     
         /**
          * @param studytype (全日制，业余)
          */
         public void setStudytype(String studytype){
	        this.studytype=studytype;    
         }
         /**
          * @return studytype (全日制，业余)
          */
         public String getStudytype(  ){ 
	        return this.studytype;    
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
          * @param className 班级名(页面上系统自动生成：年级名称+(班级序号)+班,可以由用户修改)
          */
         public void setClassName(String className){
	        this.className=className;    
         }
         /**
          * @return className 班级名(页面上系统自动生成：年级名称+(班级序号)+班,可以由用户修改)
          */
         public String getClassName(  ){ 
	        return this.className;    
         }
     
         /**
          * @param exchangeId 数据交互原始编号（保存其它地市上传数据的原始唯一编号，用户修改或删除该数据）
          */
         public void setExchangeId(String exchangeId){
	        this.exchangeId=exchangeId;    
         }
         /**
          * @return exchangeId 数据交互原始编号（保存其它地市上传数据的原始唯一编号，用户修改或删除该数据）
          */
         public String getExchangeId(  ){ 
	        return this.exchangeId;    
         }
     
         /**
          * @param preid 
          */
         public void setPreid(long preid){
	        this.preid=preid;    
         }
         /**
          * @return preid 
          */
         public long getPreid(  ){ 
	        return this.preid;    
         }
     
         /**
          * @param sepCode 专业代码(一个专业代码对应着一个专业)
          */
         public void setSepCode(String sepCode){
	        this.sepCode=sepCode;    
         }
         /**
          * @return sepCode 专业代码(一个专业代码对应着一个专业)
          */
         public String getSepCode(  ){ 
	        return this.sepCode;    
         }
     
         /**
          * @param fatherSepCode 专业类别代码(一个专业类别下有多个专业)
          */
         public void setFatherSepCode(String fatherSepCode){
	        this.fatherSepCode=fatherSepCode;    
         }
         /**
          * @return fatherSepCode 专业类别代码(一个专业类别下有多个专业)
          */
         public String getFatherSepCode(  ){ 
	        return this.fatherSepCode;    
         }
     
         /**
          * @param createTime 创建班级的时间
          */
         public void setCreateTime(Date createTime){
	        this.createTime=createTime;    
         }
         /**
          * @return createTime 创建班级的时间
          */
         public Date getCreateTime(  ){ 
	        return this.createTime;    
         }
     
         /**
          * @param learningYear 
          */
         public void setLearningYear(String learningYear){
	        this.learningYear=learningYear;    
         }
         /**
          * @return learningYear 
          */
         public String getLearningYear(  ){ 
	        return this.learningYear;    
         }
     
         /**
          * @param lastUpdateTimestamp 
          */
         public void setLastUpdateTimestamp(Date lastUpdateTimestamp){
	        this.lastUpdateTimestamp=lastUpdateTimestamp;    
         }
         /**
          * @return lastUpdateTimestamp 
          */
         public Date getLastUpdateTimestamp(  ){ 
	        return this.lastUpdateTimestamp;    
         }
     
         /**
          * @param nickname 
          */
         public void setNickname(String nickname){
	        this.nickname=nickname;    
         }
         /**
          * @return nickname 
          */
         public String getNickname(  ){ 
	        return this.nickname;    
         }
     
         /**
          * @param graduateDel 
          */
         public void setGraduateDel(long graduateDel){
	        this.graduateDel=graduateDel;    
         }
         /**
          * @return graduateDel 
          */
         public long getGraduateDel(  ){ 
	        return this.graduateDel;    
         }
    
    
     

}
