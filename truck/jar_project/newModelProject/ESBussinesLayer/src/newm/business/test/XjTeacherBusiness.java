package newm.business.test;
 

import java.util.*;

import newm.dao.entity.test.XjTeacherEntity;
import newm.dao.inter.test.XjTeacherDao;
import newm.vo.test.XjTeacherVo;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import esfw.core.framework.SpringUtil;
import esfw.core.framework.business.BaseBusiness;
import esfw.core.framework.business.BaseQuery;
import esfw.core.framework.business.enumeration.ActionType;
import esfw.core.framework.dao.PageBean;
import esfw.core.framework.exception.BusinessException;
import esfw.core.framework.exception.DaoAccessException;





/**
 *
 * @description 测试 对应的（业务逻辑类）
 * @version v1.0.0
 * @author Ethanlam  
 * @CreateTime Mon Jun 04 13:23:19 GMT 2012
 *
 */
@Scope("prototype") 
@Service("xjTeacherBusiness")
public class XjTeacherBusiness  extends BaseBusiness<Long> implements BaseQuery<XjTeacherBusiness,Long,XjTeacherVo>{
    
      //日志服务对象
      static Logger logger = Logger.getLogger(XjTeacherBusiness.class);
    
      //实体属性
	  //*****************************************************************************************************************
	  private XjTeacherEntity xjTeacherEntity;
          
	 
	 //构造函数
	 //*****************************************************************************************************************
	
     /**
     * 默认构造函数
     */
	 public XjTeacherBusiness() {
	     xjTeacherEntity = new XjTeacherEntity();
	 }
	
     /**
     * 默认构造函数
     */
	 public XjTeacherBusiness(XjTeacherEntity entity) {
	      this.xjTeacherEntity = entity;
	 }
	
	
	//属性对应的get 和 set 方法
	//*****************************************************************************************************************
	
     
        /**
         * @param id 唯一Id
         */
         public void setId(long id){
	        this.xjTeacherEntity.setId(id);     
         }
        /**
         * @return id 唯一Id
         */
         public long getId( ){ 
	        return this.xjTeacherEntity.getId( );   
         }
     
        /**
         * @param schoolId 学校id
         */
         public void setSchoolId(long schoolId){
	        this.xjTeacherEntity.setSchoolId(schoolId);     
         }
        /**
         * @return schoolId 学校id
         */
         public long getSchoolId( ){ 
	        return this.xjTeacherEntity.getSchoolId( );   
         }
     
        /**
         * @param userid 教师登录账号
         */
         public void setUserid(String userid){
	        this.xjTeacherEntity.setUserid(userid);     
         }
        /**
         * @return userid 教师登录账号
         */
         public String getUserid( ){ 
	        return this.xjTeacherEntity.getUserid( );   
         }
     
        /**
         * @param psw 密码
         */
         public void setPsw(String psw){
	        this.xjTeacherEntity.setPsw(psw);     
         }
        /**
         * @return psw 密码
         */
         public String getPsw( ){ 
	        return this.xjTeacherEntity.getPsw( );   
         }
     
        /**
         * @param username 教师姓名
         */
         public void setUsername(String username){
	        this.xjTeacherEntity.setUsername(username);     
         }
        /**
         * @return username 教师姓名
         */
         public String getUsername( ){ 
	        return this.xjTeacherEntity.getUsername( );   
         }
     
        /**
         * @param code 教师代码：向家长发短信显示此代码,取值范围：000-999
         */
         public void setCode(String code){
	        this.xjTeacherEntity.setCode(code);     
         }
        /**
         * @return code 教师代码：向家长发短信显示此代码,取值范围：000-999
         */
         public String getCode( ){ 
	        return this.xjTeacherEntity.getCode( );   
         }
     
        /**
         * @param roleId 教师角色id
         */
         public void setRoleId(long roleId){
	        this.xjTeacherEntity.setRoleId(roleId);     
         }
        /**
         * @return roleId 教师角色id
         */
         public long getRoleId( ){ 
	        return this.xjTeacherEntity.getRoleId( );   
         }
     
        /**
         * @param mphone 电话
         */
         public void setMphone(String mphone){
	        this.xjTeacherEntity.setMphone(mphone);     
         }
        /**
         * @return mphone 电话
         */
         public String getMphone( ){ 
	        return this.xjTeacherEntity.getMphone( );   
         }
     
        /**
         * @param email Email
         */
         public void setEmail(String email){
	        this.xjTeacherEntity.setEmail(email);     
         }
        /**
         * @return email Email
         */
         public String getEmail( ){ 
	        return this.xjTeacherEntity.getEmail( );   
         }
     
        /**
         * @param breceive 设置家长回复的短信是否发送到手机：0、不转；1、转
         */
         public void setBreceive(long breceive){
	        this.xjTeacherEntity.setBreceive(breceive);     
         }
        /**
         * @return breceive 设置家长回复的短信是否发送到手机：0、不转；1、转
         */
         public long getBreceive( ){ 
	        return this.xjTeacherEntity.getBreceive( );   
         }
     
        /**
         * @param flag 设置是否发送到手机：0、不转；1、转
         */
         public void setFlag(long flag){
	        this.xjTeacherEntity.setFlag(flag);     
         }
        /**
         * @return flag 设置是否发送到手机：0、不转；1、转
         */
         public long getFlag( ){ 
	        return this.xjTeacherEntity.getFlag( );   
         }
     
        /**
         * @param cdata 可用点数
         */
         public void setCdata(long cdata){
	        this.xjTeacherEntity.setCdata(cdata);     
         }
        /**
         * @return cdata 可用点数
         */
         public long getCdata( ){ 
	        return this.xjTeacherEntity.getCdata( );   
         }
     
        /**
         * @param baseinfo 基本信息
         */
         public void setBaseinfo(String baseinfo){
	        this.xjTeacherEntity.setBaseinfo(baseinfo);     
         }
        /**
         * @return baseinfo 基本信息
         */
         public String getBaseinfo( ){ 
	        return this.xjTeacherEntity.getBaseinfo( );   
         }
     
        /**
         * @param messageCode 短信代码：地区代码+si代码+学校代码+老师代码
         */
         public void setMessageCode(String messageCode){
	        this.xjTeacherEntity.setMessageCode(messageCode);     
         }
        /**
         * @return messageCode 短信代码：地区代码+si代码+学校代码+老师代码
         */
         public String getMessageCode( ){ 
	        return this.xjTeacherEntity.getMessageCode( );   
         }
     
        /**
         * @param creditCode 教育证号
         */
         public void setCreditCode(String creditCode){
	        this.xjTeacherEntity.setCreditCode(creditCode);     
         }
        /**
         * @return creditCode 教育证号
         */
         public String getCreditCode( ){ 
	        return this.xjTeacherEntity.getCreditCode( );   
         }
     
        /**
         * @param birthday 教师生日
         */
         public void setBirthday(Date birthday){
	        this.xjTeacherEntity.setBirthday(birthday);     
         }
        /**
         * @return birthday 教师生日
         */
         public Date getBirthday( ){ 
	        return this.xjTeacherEntity.getBirthday( );   
         }
     
        /**
         * @param preid 
         */
         public void setPreid(long preid){
	        this.xjTeacherEntity.setPreid(preid);     
         }
        /**
         * @return preid 
         */
         public long getPreid( ){ 
	        return this.xjTeacherEntity.getPreid( );   
         }
     
        /**
         * @param exchangeId 
         */
         public void setExchangeId(String exchangeId){
	        this.xjTeacherEntity.setExchangeId(exchangeId);     
         }
        /**
         * @return exchangeId 
         */
         public String getExchangeId( ){ 
	        return this.xjTeacherEntity.getExchangeId( );   
         }
     
        /**
         * @param seatNumber 
         */
         public void setSeatNumber(long seatNumber){
	        this.xjTeacherEntity.setSeatNumber(seatNumber);     
         }
        /**
         * @return seatNumber 
         */
         public long getSeatNumber( ){ 
	        return this.xjTeacherEntity.getSeatNumber( );   
         }
     
        /**
         * @param icNo 教师的IC卡号
         */
         public void setIcNo(String icNo){
	        this.xjTeacherEntity.setIcNo(icNo);     
         }
        /**
         * @return icNo 教师的IC卡号
         */
         public String getIcNo( ){ 
	        return this.xjTeacherEntity.getIcNo( );   
         }
     
        /**
         * @param crateTime 
         */
         public void setCrateTime(Date crateTime){
	        this.xjTeacherEntity.setCrateTime(crateTime);     
         }
        /**
         * @return crateTime 
         */
         public Date getCrateTime( ){ 
	        return this.xjTeacherEntity.getCrateTime( );   
         }
     
        /**
         * @param psend 
         */
         public void setPsend(long psend){
	        this.xjTeacherEntity.setPsend(psend);     
         }
        /**
         * @return psend 
         */
         public long getPsend( ){ 
	        return this.xjTeacherEntity.getPsend( );   
         }
     
        /**
         * @param synlessonPsw 
         */
         public void setSynlessonPsw(String synlessonPsw){
	        this.xjTeacherEntity.setSynlessonPsw(synlessonPsw);     
         }
        /**
         * @return synlessonPsw 
         */
         public String getSynlessonPsw( ){ 
	        return this.xjTeacherEntity.getSynlessonPsw( );   
         }
     
        /**
         * @param lastupdatepswdate 
         */
         public void setLastupdatepswdate(Date lastupdatepswdate){
	        this.xjTeacherEntity.setLastupdatepswdate(lastupdatepswdate);     
         }
        /**
         * @return lastupdatepswdate 
         */
         public Date getLastupdatepswdate( ){ 
	        return this.xjTeacherEntity.getLastupdatepswdate( );   
         }
     
        /**
         * @param optUserid 
         */
         public void setOptUserid(String optUserid){
	        this.xjTeacherEntity.setOptUserid(optUserid);     
         }
        /**
         * @return optUserid 
         */
         public String getOptUserid( ){ 
	        return this.xjTeacherEntity.getOptUserid( );   
         }
     
        /**
         * @param lastUpdateTimestamp 
         */
         public void setLastUpdateTimestamp(Date lastUpdateTimestamp){
	        this.xjTeacherEntity.setLastUpdateTimestamp(lastUpdateTimestamp);     
         }
        /**
         * @return lastUpdateTimestamp 
         */
         public Date getLastUpdateTimestamp( ){ 
	        return this.xjTeacherEntity.getLastUpdateTimestamp( );   
         }
     
        /**
         * @param lastLoginTime 
         */
         public void setLastLoginTime(Date lastLoginTime){
	        this.xjTeacherEntity.setLastLoginTime(lastLoginTime);     
         }
        /**
         * @return lastLoginTime 
         */
         public Date getLastLoginTime( ){ 
	        return this.xjTeacherEntity.getLastLoginTime( );   
         }
     
        /**
         * @param isLocked 
         */
         public void setIsLocked(long isLocked){
	        this.xjTeacherEntity.setIsLocked(isLocked);     
         }
        /**
         * @return isLocked 
         */
         public long getIsLocked( ){ 
	        return this.xjTeacherEntity.getIsLocked( );   
         }
     
        /**
         * @param loginSms 
         */
         public void setLoginSms(long loginSms){
	        this.xjTeacherEntity.setLoginSms(loginSms);     
         }
        /**
         * @return loginSms 
         */
         public long getLoginSms( ){ 
	        return this.xjTeacherEntity.getLoginSms( );   
         }
     
        /**
         * @param smsCheck139 
         */
         public void setSmsCheck139(long smsCheck139){
	        this.xjTeacherEntity.setSmsCheck139(smsCheck139);     
         }
        /**
         * @return smsCheck139 
         */
         public long getSmsCheck139( ){ 
	        return this.xjTeacherEntity.getSmsCheck139( );   
         }
     
        /**
         * @param loginName 教师登陆别名
         */
         public void setLoginName(String loginName){
	        this.xjTeacherEntity.setLoginName(loginName);     
         }
        /**
         * @return loginName 教师登陆别名
         */
         public String getLoginName( ){ 
	        return this.xjTeacherEntity.getLoginName( );   
         }
     
        /**
         * @param gjflag 
         */
         public void setGjflag(String gjflag){
	        this.xjTeacherEntity.setGjflag(gjflag);     
         }
        /**
         * @return gjflag 
         */
         public String getGjflag( ){ 
	        return this.xjTeacherEntity.getGjflag( );   
         }
     
        /**
         * @param sxUserid 思讯用户名
         */
         public void setSxUserid(String sxUserid){
	        this.xjTeacherEntity.setSxUserid(sxUserid);     
         }
        /**
         * @return sxUserid 思讯用户名
         */
         public String getSxUserid( ){ 
	        return this.xjTeacherEntity.getSxUserid( );   
         }
     
        /**
         * @param sxPsw 思讯密码
         */
         public void setSxPsw(String sxPsw){
	        this.xjTeacherEntity.setSxPsw(sxPsw);     
         }
        /**
         * @return sxPsw 思讯密码
         */
         public String getSxPsw( ){ 
	        return this.xjTeacherEntity.getSxPsw( );   
         }
     
        /**
         * @param amassPhone 积分兑换移动号码
         */
         public void setAmassPhone(String amassPhone){
	        this.xjTeacherEntity.setAmassPhone(amassPhone);     
         }
        /**
         * @return amassPhone 积分兑换移动号码
         */
         public String getAmassPhone( ){ 
	        return this.xjTeacherEntity.getAmassPhone( );   
         }
     
        /**
         * @param lastupdatepswtime 
         */
         public void setLastupdatepswtime(Date lastupdatepswtime){
	        this.xjTeacherEntity.setLastupdatepswtime(lastupdatepswtime);     
         }
        /**
         * @return lastupdatepswtime 
         */
         public Date getLastupdatepswtime( ){ 
	        return this.xjTeacherEntity.getLastupdatepswtime( );   
         }
     
        /**
         * @param mmsData 
         */
         public void setMmsData(long mmsData){
	        this.xjTeacherEntity.setMmsData(mmsData);     
         }
        /**
         * @return mmsData 
         */
         public long getMmsData( ){ 
	        return this.xjTeacherEntity.getMmsData( );   
         }
     
        /**
         * @param treceive 老师回复短信是否转发至手机 0不转 1转
         */
         public void setTreceive(long treceive){
	        this.xjTeacherEntity.setTreceive(treceive);     
         }
        /**
         * @return treceive 老师回复短信是否转发至手机 0不转 1转
         */
         public long getTreceive( ){ 
	        return this.xjTeacherEntity.getTreceive( );   
         }
     
        /**
         * @param isbindphone 是否号码绑定：0-未绑定；1-已绑定
         */
         public void setIsbindphone(long isbindphone){
	        this.xjTeacherEntity.setIsbindphone(isbindphone);     
         }
        /**
         * @return isbindphone 是否号码绑定：0-未绑定；1-已绑定
         */
         public long getIsbindphone( ){ 
	        return this.xjTeacherEntity.getIsbindphone( );   
         }
    
    
     
    
    
    //子类必须要实现父类的抽象方法（模块）。
	//******************************************************************************************************************************
	
	/**
	 * 设置业务逻辑名称
	 */
	@Override
	public String getBusinessName(){
	   // TODO Auto-generated method stub
		 return "test.XjTeacherBusiness";
	}


	/**
	 * 设置业务逻辑功能标识 对应数据表的功能标识
	 */
	@Override
	public String getFunctionFlag() {
		// TODO Auto-generated method stub
		 return "XjTeacherBusiness";
	}

	/**
	 * 设置获取模块名称
	 */
	@Override
	public String getModel() {
		// TODO Auto-generated method stub
		 return "test";
	}

    /**
     *  数据验证接口
     *      注意：当进行记录新增、修改时 进行的数据校验操作,针对异常的数据或操作直接抛出异常，
     *      由外层处理
     * @param  type :  1.新增 ,2.修改 
     *
     */
	@Override
	protected void checkAndFilter(ActionType type) throws BusinessException {
		// TODO Auto-generated method stub
		String checkMsg = "";
		boolean isOk = true;
		if(type==ActionType.add){	        
		}
		if(type==ActionType.mdf){
		}
		if(!isOk)
		   throw new BusinessException(checkMsg,"执行操作时，数据验证不通过。");
	}
	
	
	
	//实现对应具体的业务功能
	//*****************************************************************************************************************
	
	
   
    /**
	 * 
	 * 功能描述：Load 根据主键 查询对应的记录
	 * @author Ethan.Lam
	 * (non-Javadoc)
	 * @see esfw.core.framework.business.BaseQuery#load(java.lang.Object)
	 * 
	 */
	public Boolean load(Long id) throws BusinessException {
	// TODO Auto-generated method stub
	 try {
		        XjTeacherDao xjTeacherDao= SpringUtil.getSpringBean(XjTeacherDao.class,"xjTeacherDao");
		    xjTeacherDao.setDaoAbb(this.getDaoAbb());
			XjTeacherEntity entity =  xjTeacherDao.load(id);
			if(entity!=null){
			    entity.setDaoAbb(this.getDaoAbb()); //表分区信息
				this.xjTeacherEntity = entity;
				return true;
			}else{
				this.xjTeacherEntity = new XjTeacherEntity();
				// 没有对应的记录是，返回空集对象
				return false;
			}
		} catch (DaoAccessException ex) {
		    logger.error(getBusinessName()+" 执行记录  load 操作时出现异常",ex);
		    throw new BusinessException("查询记录详细发生异常",ex.getMessage(),ex);
		}
	}


	 
	
	
  
	/**
	 * 新增
	 */
	@Override
	protected void onAdd() throws BusinessException {
		// TODO Auto-generated method stub
		   try{
		       XjTeacherDao xjTeacherDao= SpringUtil.getSpringBean(XjTeacherDao.class,"xjTeacherDao");
		       xjTeacherDao.setDaoAbb(this.getDaoAbb());
		       xjTeacherDao.insert(this.xjTeacherEntity);
		       }catch(DaoAccessException ex){
		    	  logger.error(getBusinessName()+"执行记录新增后，进行提交时出现数据库异常",ex);
		    	  throw new BusinessException("记录新增发生异常",ex.getMessage(),ex);
		    } 
	}
	
	
	
	/**
	 * 修改
	 */
	@Override
	protected void onModify() throws BusinessException {
		// TODO Auto-generated method stub
        try{
		        XjTeacherDao xjTeacherDao= SpringUtil.getSpringBean(XjTeacherDao.class,"xjTeacherDao");
		     xjTeacherDao.setDaoAbb(this.getDaoAbb());   
		     xjTeacherDao.update(this.xjTeacherEntity);
	    }catch(DaoAccessException ex){
	          logger.error(getBusinessName()+"执行记录修改操作后，执行提交操作时出现数据库异常",ex);
	          throw new BusinessException("修改记录信息发生异常",ex.getMessage(),ex);
	    }
	}
	
	
	/**
	 * 删除
	 */
	@Override
	protected void onDelete(Long[] ids) throws BusinessException {
		// TODO Auto-generated method stub
		try{
	       XjTeacherDao xjTeacherDao= SpringUtil.getSpringBean(XjTeacherDao.class,"xjTeacherDao");
	       xjTeacherDao.setDaoAbb(this.getDaoAbb());    
		   xjTeacherDao.delete(ids);
	    }catch(DaoAccessException ex){
	       logger.error(getBusinessName()+"执行记录删除操作后，进行提交时出现数据库异常",ex);
	       throw new BusinessException("删除记录发生异常",ex.getMessage(),ex);
	    }
	}

   	
   	
   	
   	

	


	/**
	 * 
	 * 功能描述：列表查询(分页)
	 * @author Ethan.Lam
	 * (non-Javadoc)
	 * @see esfw.core.framework.business.BaseQuery#query(java.io.Serializable)
	 */
	public List<XjTeacherBusiness> query(XjTeacherVo vo) throws BusinessException {
           // TODO Auto-generated method stub
         List<XjTeacherBusiness> results = new ArrayList<XjTeacherBusiness>();
		 try {
	            XjTeacherDao xjTeacherDao= SpringUtil.getSpringBean(XjTeacherDao.class,"xjTeacherDao");
	            xjTeacherDao.setDaoAbb(this.getDaoAbb()); 
			    PageBean<XjTeacherEntity> pageBean = xjTeacherDao.query(vo.getPageVo().getPage(),vo.getPageVo().getPageSize(),vo);
			    if(pageBean!=null && pageBean.getBeanList()!= null){
					for(XjTeacherEntity et:pageBean.getBeanList()){
					    et.setDaoAbb(this.getDaoAbb()); //表分区信息
						results.add(new XjTeacherBusiness(et));
					}
			        this.setQeuryRecordTotalNum(pageBean.getTotalRecords());
			    }
			} catch (DaoAccessException ex) {
			    logger.error(getBusinessName()+"执行记录查询操作时出现数据库异常",ex);
			    throw new BusinessException("查询记录发生异常",ex.getMessage(),ex);
			}
		return results;
	}
						

	//自定义方法
	//*****************************************************************************************************************
	
	
}

