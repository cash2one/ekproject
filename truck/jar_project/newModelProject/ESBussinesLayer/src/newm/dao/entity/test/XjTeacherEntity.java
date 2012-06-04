package newm.dao.entity.test;

import java.util.*;

import esfw.core.framework.dao.GenericEntity;

/**
 * @description 测试 对应的实体
 * @version v1.0.0
 * @author Ethanlam  
 * @CreateTime Mon Jun 04 13:23:17 GMT 2012
 */
public class XjTeacherEntity extends GenericEntity {
   
     
         private long  id; //唯一Id 
         private long  schoolId; //学校id 
         private String  userid; //教师登录账号 
         private String  psw; //密码 
         private String  username; //教师姓名 
         private String  code; //教师代码：向家长发短信显示此代码,取值范围：000-999 
         private long  roleId; //教师角色id 
         private String  mphone; //电话 
         private String  email; //Email 
         private long  breceive; //设置家长回复的短信是否发送到手机：0、不转；1、转 
         private long  flag; //设置是否发送到手机：0、不转；1、转 
         private long  cdata; //可用点数 
         private String  baseinfo; //基本信息 
         private String  messageCode; //短信代码：地区代码+si代码+学校代码+老师代码 
         private String  creditCode; //教育证号 
         private Date  birthday; //教师生日 
         private long  preid; // 
         private String  exchangeId; // 
         private long  seatNumber; // 
         private String  icNo; //教师的IC卡号 
         private Date  crateTime; // 
         private long  psend; // 
         private String  synlessonPsw; // 
         private Date  lastupdatepswdate; // 
         private String  optUserid; // 
         private Date  lastUpdateTimestamp; // 
         private Date  lastLoginTime; // 
         private long  isLocked; // 
         private long  loginSms; // 
         private long  smsCheck139; // 
         private String  loginName; //教师登陆别名 
         private String  gjflag; // 
         private String  sxUserid; //思讯用户名 
         private String  sxPsw; //思讯密码 
         private String  amassPhone; //积分兑换移动号码 
         private Date  lastupdatepswtime; // 
         private long  mmsData; // 
         private long  treceive; //老师回复短信是否转发至手机 0不转 1转 
         private long  isbindphone; //是否号码绑定：0-未绑定；1-已绑定
    
    
     
        //默认空构造函数
	  public XjTeacherEntity(){
	
	  }
	
	
	
	
	 
         /**
          * @param id 唯一Id
          */
         public void setId(long id){
	        this.id=id;    
         }
         /**
          * @return id 唯一Id
          */
         public long getId(  ){ 
	        return this.id;    
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
          * @param userid 教师登录账号
          */
         public void setUserid(String userid){
	        this.userid=userid;    
         }
         /**
          * @return userid 教师登录账号
          */
         public String getUserid(  ){ 
	        return this.userid;    
         }
     
         /**
          * @param psw 密码
          */
         public void setPsw(String psw){
	        this.psw=psw;    
         }
         /**
          * @return psw 密码
          */
         public String getPsw(  ){ 
	        return this.psw;    
         }
     
         /**
          * @param username 教师姓名
          */
         public void setUsername(String username){
	        this.username=username;    
         }
         /**
          * @return username 教师姓名
          */
         public String getUsername(  ){ 
	        return this.username;    
         }
     
         /**
          * @param code 教师代码：向家长发短信显示此代码,取值范围：000-999
          */
         public void setCode(String code){
	        this.code=code;    
         }
         /**
          * @return code 教师代码：向家长发短信显示此代码,取值范围：000-999
          */
         public String getCode(  ){ 
	        return this.code;    
         }
     
         /**
          * @param roleId 教师角色id
          */
         public void setRoleId(long roleId){
	        this.roleId=roleId;    
         }
         /**
          * @return roleId 教师角色id
          */
         public long getRoleId(  ){ 
	        return this.roleId;    
         }
     
         /**
          * @param mphone 电话
          */
         public void setMphone(String mphone){
	        this.mphone=mphone;    
         }
         /**
          * @return mphone 电话
          */
         public String getMphone(  ){ 
	        return this.mphone;    
         }
     
         /**
          * @param email Email
          */
         public void setEmail(String email){
	        this.email=email;    
         }
         /**
          * @return email Email
          */
         public String getEmail(  ){ 
	        return this.email;    
         }
     
         /**
          * @param breceive 设置家长回复的短信是否发送到手机：0、不转；1、转
          */
         public void setBreceive(long breceive){
	        this.breceive=breceive;    
         }
         /**
          * @return breceive 设置家长回复的短信是否发送到手机：0、不转；1、转
          */
         public long getBreceive(  ){ 
	        return this.breceive;    
         }
     
         /**
          * @param flag 设置是否发送到手机：0、不转；1、转
          */
         public void setFlag(long flag){
	        this.flag=flag;    
         }
         /**
          * @return flag 设置是否发送到手机：0、不转；1、转
          */
         public long getFlag(  ){ 
	        return this.flag;    
         }
     
         /**
          * @param cdata 可用点数
          */
         public void setCdata(long cdata){
	        this.cdata=cdata;    
         }
         /**
          * @return cdata 可用点数
          */
         public long getCdata(  ){ 
	        return this.cdata;    
         }
     
         /**
          * @param baseinfo 基本信息
          */
         public void setBaseinfo(String baseinfo){
	        this.baseinfo=baseinfo;    
         }
         /**
          * @return baseinfo 基本信息
          */
         public String getBaseinfo(  ){ 
	        return this.baseinfo;    
         }
     
         /**
          * @param messageCode 短信代码：地区代码+si代码+学校代码+老师代码
          */
         public void setMessageCode(String messageCode){
	        this.messageCode=messageCode;    
         }
         /**
          * @return messageCode 短信代码：地区代码+si代码+学校代码+老师代码
          */
         public String getMessageCode(  ){ 
	        return this.messageCode;    
         }
     
         /**
          * @param creditCode 教育证号
          */
         public void setCreditCode(String creditCode){
	        this.creditCode=creditCode;    
         }
         /**
          * @return creditCode 教育证号
          */
         public String getCreditCode(  ){ 
	        return this.creditCode;    
         }
     
         /**
          * @param birthday 教师生日
          */
         public void setBirthday(Date birthday){
	        this.birthday=birthday;    
         }
         /**
          * @return birthday 教师生日
          */
         public Date getBirthday(  ){ 
	        return this.birthday;    
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
          * @param exchangeId 
          */
         public void setExchangeId(String exchangeId){
	        this.exchangeId=exchangeId;    
         }
         /**
          * @return exchangeId 
          */
         public String getExchangeId(  ){ 
	        return this.exchangeId;    
         }
     
         /**
          * @param seatNumber 
          */
         public void setSeatNumber(long seatNumber){
	        this.seatNumber=seatNumber;    
         }
         /**
          * @return seatNumber 
          */
         public long getSeatNumber(  ){ 
	        return this.seatNumber;    
         }
     
         /**
          * @param icNo 教师的IC卡号
          */
         public void setIcNo(String icNo){
	        this.icNo=icNo;    
         }
         /**
          * @return icNo 教师的IC卡号
          */
         public String getIcNo(  ){ 
	        return this.icNo;    
         }
     
         /**
          * @param crateTime 
          */
         public void setCrateTime(Date crateTime){
	        this.crateTime=crateTime;    
         }
         /**
          * @return crateTime 
          */
         public Date getCrateTime(  ){ 
	        return this.crateTime;    
         }
     
         /**
          * @param psend 
          */
         public void setPsend(long psend){
	        this.psend=psend;    
         }
         /**
          * @return psend 
          */
         public long getPsend(  ){ 
	        return this.psend;    
         }
     
         /**
          * @param synlessonPsw 
          */
         public void setSynlessonPsw(String synlessonPsw){
	        this.synlessonPsw=synlessonPsw;    
         }
         /**
          * @return synlessonPsw 
          */
         public String getSynlessonPsw(  ){ 
	        return this.synlessonPsw;    
         }
     
         /**
          * @param lastupdatepswdate 
          */
         public void setLastupdatepswdate(Date lastupdatepswdate){
	        this.lastupdatepswdate=lastupdatepswdate;    
         }
         /**
          * @return lastupdatepswdate 
          */
         public Date getLastupdatepswdate(  ){ 
	        return this.lastupdatepswdate;    
         }
     
         /**
          * @param optUserid 
          */
         public void setOptUserid(String optUserid){
	        this.optUserid=optUserid;    
         }
         /**
          * @return optUserid 
          */
         public String getOptUserid(  ){ 
	        return this.optUserid;    
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
          * @param lastLoginTime 
          */
         public void setLastLoginTime(Date lastLoginTime){
	        this.lastLoginTime=lastLoginTime;    
         }
         /**
          * @return lastLoginTime 
          */
         public Date getLastLoginTime(  ){ 
	        return this.lastLoginTime;    
         }
     
         /**
          * @param isLocked 
          */
         public void setIsLocked(long isLocked){
	        this.isLocked=isLocked;    
         }
         /**
          * @return isLocked 
          */
         public long getIsLocked(  ){ 
	        return this.isLocked;    
         }
     
         /**
          * @param loginSms 
          */
         public void setLoginSms(long loginSms){
	        this.loginSms=loginSms;    
         }
         /**
          * @return loginSms 
          */
         public long getLoginSms(  ){ 
	        return this.loginSms;    
         }
     
         /**
          * @param smsCheck139 
          */
         public void setSmsCheck139(long smsCheck139){
	        this.smsCheck139=smsCheck139;    
         }
         /**
          * @return smsCheck139 
          */
         public long getSmsCheck139(  ){ 
	        return this.smsCheck139;    
         }
     
         /**
          * @param loginName 教师登陆别名
          */
         public void setLoginName(String loginName){
	        this.loginName=loginName;    
         }
         /**
          * @return loginName 教师登陆别名
          */
         public String getLoginName(  ){ 
	        return this.loginName;    
         }
     
         /**
          * @param gjflag 
          */
         public void setGjflag(String gjflag){
	        this.gjflag=gjflag;    
         }
         /**
          * @return gjflag 
          */
         public String getGjflag(  ){ 
	        return this.gjflag;    
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
          * @param amassPhone 积分兑换移动号码
          */
         public void setAmassPhone(String amassPhone){
	        this.amassPhone=amassPhone;    
         }
         /**
          * @return amassPhone 积分兑换移动号码
          */
         public String getAmassPhone(  ){ 
	        return this.amassPhone;    
         }
     
         /**
          * @param lastupdatepswtime 
          */
         public void setLastupdatepswtime(Date lastupdatepswtime){
	        this.lastupdatepswtime=lastupdatepswtime;    
         }
         /**
          * @return lastupdatepswtime 
          */
         public Date getLastupdatepswtime(  ){ 
	        return this.lastupdatepswtime;    
         }
     
         /**
          * @param mmsData 
          */
         public void setMmsData(long mmsData){
	        this.mmsData=mmsData;    
         }
         /**
          * @return mmsData 
          */
         public long getMmsData(  ){ 
	        return this.mmsData;    
         }
     
         /**
          * @param treceive 老师回复短信是否转发至手机 0不转 1转
          */
         public void setTreceive(long treceive){
	        this.treceive=treceive;    
         }
         /**
          * @return treceive 老师回复短信是否转发至手机 0不转 1转
          */
         public long getTreceive(  ){ 
	        return this.treceive;    
         }
     
         /**
          * @param isbindphone 是否号码绑定：0-未绑定；1-已绑定
          */
         public void setIsbindphone(long isbindphone){
	        this.isbindphone=isbindphone;    
         }
         /**
          * @return isbindphone 是否号码绑定：0-未绑定；1-已绑定
          */
         public long getIsbindphone(  ){ 
	        return this.isbindphone;    
         }
    
    
     

}
