package newm.dao.entity.test;

import java.util.*;

import esfw.core.framework.dao.GenericEntity;

/**
 * @description 测试 对应的实体
 * @version v1.0.0
 * @author Ethanlam  
 * @CreateTime Mon Jun 04 13:26:23 GMT 2012
 */
public class XjStudentEntity extends GenericEntity {
   
     
         private long  id; //主键 
         private String  stuSequence; //学生编号（系统分配、全省唯一），生成规则：2位年份＋2位地区代码＋8位自动增长编号 
         private String  stuNo; //学号 
         private String  name; //姓名(长度改为100) 
         private long  sex; //性别(1男；0女) 
         private Date  birthday; //出生日期 
         private String  address; //家庭住址 
         private String  origin; //籍贯(用户直接输入) 
         private long  nation; //主表字段:NATION 
         private String  originAddr; //户口所在地 
         private long  bloodType; //血型(1A、2B、3AB、4O、5空白) 
         private long  politics; //政治面貌(1少先队员、2共青团员、3中共预备党员、4中共党员、5民革会员、6民盟盟员、7民建会员、8民进会员、9农工党党员、10致公党员、11九三学社社员、12台盟盟员、13无党派民主人士、14群众,99空白) 
         private String  icNo; //IC卡号(前台不允许新增，修改，只可以查看，卡号通过客户端刷卡进入数据库) 
         private String  homePhone; //家庭电话 
         private String  goodAt; //特长 
         private String  identyNo; //身份证号 
         private long  homeMacau; //港澳台侨(1归侨、2华侨、3侨眷、4港澳、5台胞、6外籍华人、7华籍外人、8非华籍外人、9其他) 
         private String  bornAddress; //出生地 
         private String  health; //健康状况 
         private long  icStatus; //IC卡状态:未发卡(0),已发卡(1),挂失(2),补卡(3) 
         private Date  inSchoolDate; //入校日期 
         private String  exchangeId; //数据交互原始编号（保存其它地市上传数据的原始唯一编号，用户修改或删除该数据） 
         private Date  createTime; //创建时间 
         private long  callTime; //通话时间 
         private long  stuType; // 
         private long  seatNumber; //座位号码 
         private String  cardNo; //IC卡号 
         private String  icNo2; //IC卡号2 
         private Date  lastUpdateTimestamp; //最后更新日期 
         private String  sxUserid; //思讯用户名 
         private String  sxPsw; //思讯密码 
         private String  comments; //备注信息
    
     
          private long  schoolId; //所属的学校 
          private long  classId; //班级编号 
          private String  schoolName; //学校的名称 
          private String  className; //班级名(页面上系统自动生成：年级名称+(班级序号)+班,可以由用户修改)
     
        //默认空构造函数
	  public XjStudentEntity(){
	
	  }
	
	
	
	
	 
         /**
          * @param id 主键
          */
         public void setId(long id){
	        this.id=id;    
         }
         /**
          * @return id 主键
          */
         public long getId(  ){ 
	        return this.id;    
         }
     
         /**
          * @param stuSequence 学生编号（系统分配、全省唯一），生成规则：2位年份＋2位地区代码＋8位自动增长编号
          */
         public void setStuSequence(String stuSequence){
	        this.stuSequence=stuSequence;    
         }
         /**
          * @return stuSequence 学生编号（系统分配、全省唯一），生成规则：2位年份＋2位地区代码＋8位自动增长编号
          */
         public String getStuSequence(  ){ 
	        return this.stuSequence;    
         }
     
         /**
          * @param stuNo 学号
          */
         public void setStuNo(String stuNo){
	        this.stuNo=stuNo;    
         }
         /**
          * @return stuNo 学号
          */
         public String getStuNo(  ){ 
	        return this.stuNo;    
         }
     
         /**
          * @param name 姓名(长度改为100)
          */
         public void setName(String name){
	        this.name=name;    
         }
         /**
          * @return name 姓名(长度改为100)
          */
         public String getName(  ){ 
	        return this.name;    
         }
     
         /**
          * @param sex 性别(1男；0女)
          */
         public void setSex(long sex){
	        this.sex=sex;    
         }
         /**
          * @return sex 性别(1男；0女)
          */
         public long getSex(  ){ 
	        return this.sex;    
         }
     
         /**
          * @param birthday 出生日期
          */
         public void setBirthday(Date birthday){
	        this.birthday=birthday;    
         }
         /**
          * @return birthday 出生日期
          */
         public Date getBirthday(  ){ 
	        return this.birthday;    
         }
     
         /**
          * @param address 家庭住址
          */
         public void setAddress(String address){
	        this.address=address;    
         }
         /**
          * @return address 家庭住址
          */
         public String getAddress(  ){ 
	        return this.address;    
         }
     
         /**
          * @param origin 籍贯(用户直接输入)
          */
         public void setOrigin(String origin){
	        this.origin=origin;    
         }
         /**
          * @return origin 籍贯(用户直接输入)
          */
         public String getOrigin(  ){ 
	        return this.origin;    
         }
     
         /**
          * @param nation 主表字段:NATION
          */
         public void setNation(long nation){
	        this.nation=nation;    
         }
         /**
          * @return nation 主表字段:NATION
          */
         public long getNation(  ){ 
	        return this.nation;    
         }
     
         /**
          * @param originAddr 户口所在地
          */
         public void setOriginAddr(String originAddr){
	        this.originAddr=originAddr;    
         }
         /**
          * @return originAddr 户口所在地
          */
         public String getOriginAddr(  ){ 
	        return this.originAddr;    
         }
     
         /**
          * @param bloodType 血型(1A、2B、3AB、4O、5空白)
          */
         public void setBloodType(long bloodType){
	        this.bloodType=bloodType;    
         }
         /**
          * @return bloodType 血型(1A、2B、3AB、4O、5空白)
          */
         public long getBloodType(  ){ 
	        return this.bloodType;    
         }
     
         /**
          * @param politics 政治面貌(1少先队员、2共青团员、3中共预备党员、4中共党员、5民革会员、6民盟盟员、7民建会员、8民进会员、9农工党党员、10致公党员、11九三学社社员、12台盟盟员、13无党派民主人士、14群众,99空白)
          */
         public void setPolitics(long politics){
	        this.politics=politics;    
         }
         /**
          * @return politics 政治面貌(1少先队员、2共青团员、3中共预备党员、4中共党员、5民革会员、6民盟盟员、7民建会员、8民进会员、9农工党党员、10致公党员、11九三学社社员、12台盟盟员、13无党派民主人士、14群众,99空白)
          */
         public long getPolitics(  ){ 
	        return this.politics;    
         }
     
         /**
          * @param icNo IC卡号(前台不允许新增，修改，只可以查看，卡号通过客户端刷卡进入数据库)
          */
         public void setIcNo(String icNo){
	        this.icNo=icNo;    
         }
         /**
          * @return icNo IC卡号(前台不允许新增，修改，只可以查看，卡号通过客户端刷卡进入数据库)
          */
         public String getIcNo(  ){ 
	        return this.icNo;    
         }
     
         /**
          * @param homePhone 家庭电话
          */
         public void setHomePhone(String homePhone){
	        this.homePhone=homePhone;    
         }
         /**
          * @return homePhone 家庭电话
          */
         public String getHomePhone(  ){ 
	        return this.homePhone;    
         }
     
         /**
          * @param goodAt 特长
          */
         public void setGoodAt(String goodAt){
	        this.goodAt=goodAt;    
         }
         /**
          * @return goodAt 特长
          */
         public String getGoodAt(  ){ 
	        return this.goodAt;    
         }
     
         /**
          * @param identyNo 身份证号
          */
         public void setIdentyNo(String identyNo){
	        this.identyNo=identyNo;    
         }
         /**
          * @return identyNo 身份证号
          */
         public String getIdentyNo(  ){ 
	        return this.identyNo;    
         }
     
         /**
          * @param homeMacau 港澳台侨(1归侨、2华侨、3侨眷、4港澳、5台胞、6外籍华人、7华籍外人、8非华籍外人、9其他)
          */
         public void setHomeMacau(long homeMacau){
	        this.homeMacau=homeMacau;    
         }
         /**
          * @return homeMacau 港澳台侨(1归侨、2华侨、3侨眷、4港澳、5台胞、6外籍华人、7华籍外人、8非华籍外人、9其他)
          */
         public long getHomeMacau(  ){ 
	        return this.homeMacau;    
         }
     
         /**
          * @param bornAddress 出生地
          */
         public void setBornAddress(String bornAddress){
	        this.bornAddress=bornAddress;    
         }
         /**
          * @return bornAddress 出生地
          */
         public String getBornAddress(  ){ 
	        return this.bornAddress;    
         }
     
         /**
          * @param health 健康状况
          */
         public void setHealth(String health){
	        this.health=health;    
         }
         /**
          * @return health 健康状况
          */
         public String getHealth(  ){ 
	        return this.health;    
         }
     
         /**
          * @param icStatus IC卡状态:未发卡(0),已发卡(1),挂失(2),补卡(3)
          */
         public void setIcStatus(long icStatus){
	        this.icStatus=icStatus;    
         }
         /**
          * @return icStatus IC卡状态:未发卡(0),已发卡(1),挂失(2),补卡(3)
          */
         public long getIcStatus(  ){ 
	        return this.icStatus;    
         }
     
         /**
          * @param inSchoolDate 入校日期
          */
         public void setInSchoolDate(Date inSchoolDate){
	        this.inSchoolDate=inSchoolDate;    
         }
         /**
          * @return inSchoolDate 入校日期
          */
         public Date getInSchoolDate(  ){ 
	        return this.inSchoolDate;    
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
          * @param createTime 创建时间
          */
         public void setCreateTime(Date createTime){
	        this.createTime=createTime;    
         }
         /**
          * @return createTime 创建时间
          */
         public Date getCreateTime(  ){ 
	        return this.createTime;    
         }
     
         /**
          * @param callTime 通话时间
          */
         public void setCallTime(long callTime){
	        this.callTime=callTime;    
         }
         /**
          * @return callTime 通话时间
          */
         public long getCallTime(  ){ 
	        return this.callTime;    
         }
     
         /**
          * @param stuType 
          */
         public void setStuType(long stuType){
	        this.stuType=stuType;    
         }
         /**
          * @return stuType 
          */
         public long getStuType(  ){ 
	        return this.stuType;    
         }
     
         /**
          * @param seatNumber 座位号码
          */
         public void setSeatNumber(long seatNumber){
	        this.seatNumber=seatNumber;    
         }
         /**
          * @return seatNumber 座位号码
          */
         public long getSeatNumber(  ){ 
	        return this.seatNumber;    
         }
     
         /**
          * @param cardNo IC卡号
          */
         public void setCardNo(String cardNo){
	        this.cardNo=cardNo;    
         }
         /**
          * @return cardNo IC卡号
          */
         public String getCardNo(  ){ 
	        return this.cardNo;    
         }
     
         /**
          * @param icNo2 IC卡号2
          */
         public void setIcNo2(String icNo2){
	        this.icNo2=icNo2;    
         }
         /**
          * @return icNo2 IC卡号2
          */
         public String getIcNo2(  ){ 
	        return this.icNo2;    
         }
     
         /**
          * @param lastUpdateTimestamp 最后更新日期
          */
         public void setLastUpdateTimestamp(Date lastUpdateTimestamp){
	        this.lastUpdateTimestamp=lastUpdateTimestamp;    
         }
         /**
          * @return lastUpdateTimestamp 最后更新日期
          */
         public Date getLastUpdateTimestamp(  ){ 
	        return this.lastUpdateTimestamp;    
         }
     
         /**
          * @param sxUserid 思讯用户名
          */
         public void setSxUserid(String sxUserid){
	        this.sxUserid=sxUserid;    
         }
         /**
          * @return sxUserid 思讯用户名
          */
         public String getSxUserid(  ){ 
	        return this.sxUserid;    
         }
     
         /**
          * @param sxPsw 思讯密码
          */
         public void setSxPsw(String sxPsw){
	        this.sxPsw=sxPsw;    
         }
         /**
          * @return sxPsw 思讯密码
          */
         public String getSxPsw(  ){ 
	        return this.sxPsw;    
         }
     
         /**
          * @param comments 备注信息
          */
         public void setComments(String comments){
	        this.comments=comments;    
         }
         /**
          * @return comments 备注信息
          */
         public String getComments(  ){ 
	        return this.comments;    
         }
    
    
      
         /**
         * @param schoolId 所属的学校
         */
         public void setSchoolId(long schoolId){
	       this.schoolId=schoolId;    
         }
         /**
         * @return schoolId 所属的学校
         */
         public long getSchoolId(  ){ 
	        return this.schoolId;    
         }
     
         /**
         * @param classId 班级编号
         */
         public void setClassId(long classId){
	       this.classId=classId;    
         }
         /**
         * @return classId 班级编号
         */
         public long getClassId(  ){ 
	        return this.classId;    
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
    

}
