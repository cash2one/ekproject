package newm.business.test;
 

import java.util.*;

import newm.dao.entity.test.XjClassEntity;
import newm.dao.inter.test.XjClassDao;
import newm.vo.test.XjClassVo;

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
 * @description 班级信息 对应的（业务逻辑类）
 * @version v1.0.0
 * @author Ethanlam  
 * @CreateTime Sun Jun 03 11:05:32 GMT 2012
 *
 */
@Scope("prototype") 
@Service("xjClassBusiness")
public class XjClassBusiness  extends BaseBusiness<Long> implements BaseQuery<XjClassBusiness,Long,XjClassVo>{
    
      //日志服务对象
      static Logger logger = Logger.getLogger(XjClassBusiness.class);
    
      //实体属性
	  //*****************************************************************************************************************
	  private XjClassEntity xjClassEntity;
          
	 
	 //构造函数
	 //*****************************************************************************************************************
	
     /**
     * 默认构造函数
     */
	 public XjClassBusiness() {
	     xjClassEntity = new XjClassEntity();
	 }
	
     /**
     * 默认构造函数
     */
	 public XjClassBusiness(XjClassEntity entity) {
	      this.xjClassEntity = entity;
	 }
	
	
	//属性对应的get 和 set 方法
	//*****************************************************************************************************************
	
     
        /**
         * @param id 班级ID
         */
         public void setId(long id){
	        this.xjClassEntity.setId(id);     
         }
        /**
         * @return id 班级ID
         */
         public long getId( ){ 
	        return this.xjClassEntity.getId( );   
         }
     
        /**
         * @param gradeId 所属的年级
         */
         public void setGradeId(long gradeId){
	        this.xjClassEntity.setGradeId(gradeId);     
         }
        /**
         * @return gradeId 所属的年级
         */
         public long getGradeId( ){ 
	        return this.xjClassEntity.getGradeId( );   
         }
     
        /**
         * @param schoolId 学校id
         */
         public void setSchoolId(long schoolId){
	        this.xjClassEntity.setSchoolId(schoolId);     
         }
        /**
         * @return schoolId 学校id
         */
         public long getSchoolId( ){ 
	        return this.xjClassEntity.getSchoolId( );   
         }
     
        /**
         * @param classType 班级类型（1正规班,2兴趣班）
         */
         public void setClassType(long classType){
	        this.xjClassEntity.setClassType(classType);     
         }
        /**
         * @return classType 班级类型（1正规班,2兴趣班）
         */
         public long getClassType( ){ 
	        return this.xjClassEntity.getClassType( );   
         }
     
        /**
         * @param language 外语语种（1英语、2日语、3俄语、4德语、5法语、6其他）
         */
         public void setLanguage(long language){
	        this.xjClassEntity.setLanguage(language);     
         }
        /**
         * @return language 外语语种（1英语、2日语、3俄语、4德语、5法语、6其他）
         */
         public long getLanguage( ){ 
	        return this.xjClassEntity.getLanguage( );   
         }
     
        /**
         * @param userId 班主任编号(用户表的编号)
         */
         public void setUserId(long userId){
	        this.xjClassEntity.setUserId(userId);     
         }
        /**
         * @return userId 班主任编号(用户表的编号)
         */
         public long getUserId( ){ 
	        return this.xjClassEntity.getUserId( );   
         }
     
        /**
         * @param isGraduate 是否毕业班(1是；0否),在页面要根据年级判断
         */
         public void setIsGraduate(long isGraduate){
	        this.xjClassEntity.setIsGraduate(isGraduate);     
         }
        /**
         * @return isGraduate 是否毕业班(1是；0否),在页面要根据年级判断
         */
         public long getIsGraduate( ){ 
	        return this.xjClassEntity.getIsGraduate( );   
         }
     
        /**
         * @param orderno 排序号
         */
         public void setOrderno(long orderno){
	        this.xjClassEntity.setOrderno(orderno);     
         }
        /**
         * @return orderno 排序号
         */
         public long getOrderno( ){ 
	        return this.xjClassEntity.getOrderno( );   
         }
     
        /**
         * @param classno 班级序号
         */
         public void setClassno(String classno){
	        this.xjClassEntity.setClassno(classno);     
         }
        /**
         * @return classno 班级序号
         */
         public String getClassno( ){ 
	        return this.xjClassEntity.getClassno( );   
         }
     
        /**
         * @param establishDate 创建日期(由系统自动生成，生成规则：属于小一、初一、高一、一年级的班级，创建日期是“当前年份－09－01”，其它的一次类推：小二，初二，高二，二年级则是“（当前年份-1）-09-01”，如小一（1）班2006-09-01，小二（1）班2005-09-01)
         */
         public void setEstablishDate(Date establishDate){
	        this.xjClassEntity.setEstablishDate(establishDate);     
         }
        /**
         * @return establishDate 创建日期(由系统自动生成，生成规则：属于小一、初一、高一、一年级的班级，创建日期是“当前年份－09－01”，其它的一次类推：小二，初二，高二，二年级则是“（当前年份-1）-09-01”，如小一（1）班2006-09-01，小二（1）班2005-09-01)
         */
         public Date getEstablishDate( ){ 
	        return this.xjClassEntity.getEstablishDate( );   
         }
     
        /**
         * @param inSchool 是否在校(1、是；0、否）,后台操作字段，由系统在做自动毕业时设置。页面上不需要体现，但要提供查询
         */
         public void setInSchool(long inSchool){
	        this.xjClassEntity.setInSchool(inSchool);     
         }
        /**
         * @return inSchool 是否在校(1、是；0、否）,后台操作字段，由系统在做自动毕业时设置。页面上不需要体现，但要提供查询
         */
         public long getInSchool( ){ 
	        return this.xjClassEntity.getInSchool( );   
         }
     
        /**
         * @param studytype (全日制，业余)
         */
         public void setStudytype(String studytype){
	        this.xjClassEntity.setStudytype(studytype);     
         }
        /**
         * @return studytype (全日制，业余)
         */
         public String getStudytype( ){ 
	        return this.xjClassEntity.getStudytype( );   
         }
     
        /**
         * @param section 学段:0幼儿园，1小学，2初中，3高中，4综合
         */
         public void setSection(long section){
	        this.xjClassEntity.setSection(section);     
         }
        /**
         * @return section 学段:0幼儿园，1小学，2初中，3高中，4综合
         */
         public long getSection( ){ 
	        return this.xjClassEntity.getSection( );   
         }
     
        /**
         * @param className 班级名(页面上系统自动生成：年级名称+(班级序号)+班,可以由用户修改)
         */
         public void setClassName(String className){
	        this.xjClassEntity.setClassName(className);     
         }
        /**
         * @return className 班级名(页面上系统自动生成：年级名称+(班级序号)+班,可以由用户修改)
         */
         public String getClassName( ){ 
	        return this.xjClassEntity.getClassName( );   
         }
     
        /**
         * @param exchangeId 数据交互原始编号（保存其它地市上传数据的原始唯一编号，用户修改或删除该数据）
         */
         public void setExchangeId(String exchangeId){
	        this.xjClassEntity.setExchangeId(exchangeId);     
         }
        /**
         * @return exchangeId 数据交互原始编号（保存其它地市上传数据的原始唯一编号，用户修改或删除该数据）
         */
         public String getExchangeId( ){ 
	        return this.xjClassEntity.getExchangeId( );   
         }
     
        /**
         * @param preid 
         */
         public void setPreid(long preid){
	        this.xjClassEntity.setPreid(preid);     
         }
        /**
         * @return preid 
         */
         public long getPreid( ){ 
	        return this.xjClassEntity.getPreid( );   
         }
     
        /**
         * @param sepCode 专业代码(一个专业代码对应着一个专业)
         */
         public void setSepCode(String sepCode){
	        this.xjClassEntity.setSepCode(sepCode);     
         }
        /**
         * @return sepCode 专业代码(一个专业代码对应着一个专业)
         */
         public String getSepCode( ){ 
	        return this.xjClassEntity.getSepCode( );   
         }
     
        /**
         * @param fatherSepCode 专业类别代码(一个专业类别下有多个专业)
         */
         public void setFatherSepCode(String fatherSepCode){
	        this.xjClassEntity.setFatherSepCode(fatherSepCode);     
         }
        /**
         * @return fatherSepCode 专业类别代码(一个专业类别下有多个专业)
         */
         public String getFatherSepCode( ){ 
	        return this.xjClassEntity.getFatherSepCode( );   
         }
     
        /**
         * @param createTime 创建班级的时间
         */
         public void setCreateTime(Date createTime){
	        this.xjClassEntity.setCreateTime(createTime);     
         }
        /**
         * @return createTime 创建班级的时间
         */
         public Date getCreateTime( ){ 
	        return this.xjClassEntity.getCreateTime( );   
         }
     
        /**
         * @param learningYear 
         */
         public void setLearningYear(String learningYear){
	        this.xjClassEntity.setLearningYear(learningYear);     
         }
        /**
         * @return learningYear 
         */
         public String getLearningYear( ){ 
	        return this.xjClassEntity.getLearningYear( );   
         }
     
        /**
         * @param lastUpdateTimestamp 
         */
         public void setLastUpdateTimestamp(Date lastUpdateTimestamp){
	        this.xjClassEntity.setLastUpdateTimestamp(lastUpdateTimestamp);     
         }
        /**
         * @return lastUpdateTimestamp 
         */
         public Date getLastUpdateTimestamp( ){ 
	        return this.xjClassEntity.getLastUpdateTimestamp( );   
         }
     
        /**
         * @param nickname 
         */
         public void setNickname(String nickname){
	        this.xjClassEntity.setNickname(nickname);     
         }
        /**
         * @return nickname 
         */
         public String getNickname( ){ 
	        return this.xjClassEntity.getNickname( );   
         }
     
        /**
         * @param graduateDel 
         */
         public void setGraduateDel(long graduateDel){
	        this.xjClassEntity.setGraduateDel(graduateDel);     
         }
        /**
         * @return graduateDel 
         */
         public long getGraduateDel( ){ 
	        return this.xjClassEntity.getGraduateDel( );   
         }
    
    
     
    
    
    //子类必须要实现父类的抽象方法（模块）。
	//******************************************************************************************************************************
	
	/**
	 * 设置业务逻辑名称
	 */
	@Override
	public String getBusinessName(){
	   // TODO Auto-generated method stub
		 return "test.XjClassBusiness";
	}


	/**
	 * 设置业务逻辑功能标识 对应数据表的功能标识
	 */
	@Override
	public String getFunctionFlag() {
		// TODO Auto-generated method stub
		 return "XjClassBusiness";
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
		        XjClassDao xjClassDao= SpringUtil.getSpringBean(XjClassDao.class,"xjClassDao");
			XjClassEntity entity =  xjClassDao.load(id);
			if(entity!=null){
				this.xjClassEntity = entity;
				return true;
			}else{
				this.xjClassEntity = new XjClassEntity();
				// 没有对应的记录是，返回空集对象
				return false;
			}
		} catch (DaoAccessException ex) {
		    logger.error(getBusinessName()+" 执行记录  load 操作时出现异常",ex);
		    throw new BusinessException("查询记录详细发生异常",ex.getMessage(),ex);
		}
	}


	 
	
	
  
   	
   	
   	
	/**
	 * 新增记录（事务控制类型）
	 */
	@Override
	protected void onAdd() throws BusinessException {
		// TODO Auto-generated method stub
		   //打开事务控制
           DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		   def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		   DataSourceTransactionManager txManager =  SpringUtil.getSpringBean(DataSourceTransactionManager.class,"transactionManager");
		   TransactionStatus status = txManager.getTransaction(def);
		   try{
		      
		       XjClassDao xjClassDao= SpringUtil.getSpringBean(XjClassDao.class,"xjClassDao");
		       xjClassDao.insert(this.xjClassEntity);
		        
		       
		       //请实现业务新增的逻辑过程
		       
			   txManager.commit(status);		       
		       }catch(DaoAccessException ex){
		    	  logger.error(getBusinessName()+"执行记录新增后，进行业务提交时出现数据库异常",ex);
		    	  txManager.rollback(status); //事务回滚
		    	  throw new BusinessException("新增记录发生异常",ex.getMessage(),ex);
		    } 
		    txManager = null;
		    def = null;
	}
	
	
	/**
	 * 修改 （事务控制类型）
	 */
	@Override
	protected void onModify() throws BusinessException {
		// TODO Auto-generated method stub
        //打开事务控制
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		DataSourceTransactionManager txManager =  SpringUtil.getSpringBean(DataSourceTransactionManager.class,"transactionManager");
		TransactionStatus status = txManager.getTransaction(def);
		try{
		    XjClassDao xjClassDao= SpringUtil.getSpringBean(XjClassDao.class,"xjClassDao");
			xjClassDao.update(this.xjClassEntity);
		     
				  
		    txManager.commit(status);	        
	        }catch(DaoAccessException ex){
		    	logger.error(getBusinessName()+"执行记录修改后，进行业务提交时出现数据库异常",ex);
		    	txManager.rollback(status); //事务回滚
		    	throw new BusinessException("修改记录详细信息发生异常",ex.getMessage(),ex);
		    } 
		    txManager = null;
		    def = null;
	}
	
	
    /**
     * 删除记录（事务控制类型）
     */
	@Override
      protected void onDelete(Long[] ids) throws BusinessException {
		// TODO Auto-generated method stub
		        
        //打开事务控制
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		DataSourceTransactionManager txManager =  SpringUtil.getSpringBean(DataSourceTransactionManager.class,"transactionManager");
		TransactionStatus status = txManager.getTransaction(def);
		try{
	       XjClassDao xjClassDao= SpringUtil.getSpringBean(XjClassDao.class,"xjClassDao");
		   xjClassDao.delete(ids);
	        
		    txManager.commit(status);	        
	        }catch(DaoAccessException ex){
		    	logger.error(getBusinessName()+"执行记录删除后，进行业务提交时出现数据库异常",ex);
		    	txManager.rollback(status); //事务回滚
		    	throw new BusinessException("删除记录时发生异常",ex.getMessage(),ex);
		    } 
		    txManager = null;
		    def = null;
	}
   	

	


	/**
	 * 
	 * 功能描述：列表查询(分页)
	 * @author Ethan.Lam
	 * (non-Javadoc)
	 * @see esfw.core.framework.business.BaseQuery#query(java.io.Serializable)
	 */
	public List<XjClassBusiness> query(XjClassVo vo) throws BusinessException {
           // TODO Auto-generated method stub
         List<XjClassBusiness> results = new ArrayList<XjClassBusiness>();
		 try {
	            XjClassDao xjClassDao= SpringUtil.getSpringBean(XjClassDao.class,"xjClassDao");
			    PageBean<XjClassEntity> pageBean = xjClassDao.query(vo.getPageVo().getPage(),vo.getPageVo().getPageSize(),vo);
			    if(pageBean!=null && pageBean.getBeanList()!= null){
					for(XjClassEntity et:pageBean.getBeanList()){
						results.add(new XjClassBusiness(et));
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

