package newm.business.test;
 

import java.util.*;

import newm.dao.entity.test.AdcSyncLogEntity;
import newm.dao.inter.test.AdcSyncLogDao;
import newm.vo.test.AdcSyncLogVo;

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
 * @description fsdf 对应的（业务逻辑类）
 * @version v1.0.0
 * @author Ethanlam  
 * @CreateTime Mon Jun 04 13:00:20 GMT 2012
 *
 */
@Scope("prototype") 
@Service("adcSyncLogBusiness")
public class AdcSyncLogBusiness  extends BaseBusiness<Long> implements BaseQuery<AdcSyncLogBusiness,Long,AdcSyncLogVo>{
    
      //日志服务对象
      static Logger logger = Logger.getLogger(AdcSyncLogBusiness.class);
    
      //实体属性
	  //*****************************************************************************************************************
	  private AdcSyncLogEntity adcSyncLogEntity;
          
	 
	 //构造函数
	 //*****************************************************************************************************************
	
     /**
     * 默认构造函数
     */
	 public AdcSyncLogBusiness() {
	     adcSyncLogEntity = new AdcSyncLogEntity();
	 }
	
     /**
     * 默认构造函数
     */
	 public AdcSyncLogBusiness(AdcSyncLogEntity entity) {
	      this.adcSyncLogEntity = entity;
	 }
	
	
	//属性对应的get 和 set 方法
	//*****************************************************************************************************************
	
     
        /**
         * @param id 
         */
         public void setId(long id){
	        this.adcSyncLogEntity.setId(id);     
         }
        /**
         * @return id 
         */
         public long getId( ){ 
	        return this.adcSyncLogEntity.getId( );   
         }
     
        /**
         * @param xml adc响应的内容 服务开通成功  SI201 ADCProvisioningResponse 服务状态变更成功 SI202 ServiceStateResponseEC基本信息同步成功 SI107 ECInfoResponse
         */
         public void setXml(String xml){
	        this.adcSyncLogEntity.setXml(xml);     
         }
        /**
         * @return xml adc响应的内容 服务开通成功  SI201 ADCProvisioningResponse 服务状态变更成功 SI202 ServiceStateResponseEC基本信息同步成功 SI107 ECInfoResponse
         */
         public String getXml( ){ 
	        return this.adcSyncLogEntity.getXml( );   
         }
     
        /**
         * @param eccode 学校eccode
         */
         public void setEccode(String eccode){
	        this.adcSyncLogEntity.setEccode(eccode);     
         }
        /**
         * @return eccode 学校eccode
         */
         public String getEccode( ){ 
	        return this.adcSyncLogEntity.getEccode( );   
         }
     
        /**
         * @param type 1 3正向接收，4正向响应
         */
         public void setType(long type){
	        this.adcSyncLogEntity.setType(type);     
         }
        /**
         * @return type 1 3正向接收，4正向响应
         */
         public long getType( ){ 
	        return this.adcSyncLogEntity.getType( );   
         }
     
        /**
         * @param dt 创建时间
         */
         public void setDt(Date dt){
	        this.adcSyncLogEntity.setDt(dt);     
         }
        /**
         * @return dt 创建时间
         */
         public Date getDt( ){ 
	        return this.adcSyncLogEntity.getDt( );   
         }
     
        /**
         * @param schoolName 
         */
         public void setSchoolName(String schoolName){
	        this.adcSyncLogEntity.setSchoolName(schoolName);     
         }
        /**
         * @return schoolName 
         */
         public String getSchoolName( ){ 
	        return this.adcSyncLogEntity.getSchoolName( );   
         }
    
    
     
    
    
    //子类必须要实现父类的抽象方法（模块）。
	//******************************************************************************************************************************
	
	/**
	 * 设置业务逻辑名称
	 */
	@Override
	public String getBusinessName(){
	   // TODO Auto-generated method stub
		 return "test.AdcSyncLogBusiness";
	}


	/**
	 * 设置业务逻辑功能标识 对应数据表的功能标识
	 */
	@Override
	public String getFunctionFlag() {
		// TODO Auto-generated method stub
		 return "AdcSyncLogBusiness";
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
		        AdcSyncLogDao adcSyncLogDao= SpringUtil.getSpringBean(AdcSyncLogDao.class,"adcSyncLogDao");
		    adcSyncLogDao.setDaoAbb(this.getDaoAbb());
			AdcSyncLogEntity entity =  adcSyncLogDao.load(id);
			if(entity!=null){
			    entity.setDaoAbb(this.getDaoAbb()); //表分区信息
				this.adcSyncLogEntity = entity;
				return true;
			}else{
				this.adcSyncLogEntity = new AdcSyncLogEntity();
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
		      
		       AdcSyncLogDao adcSyncLogDao= SpringUtil.getSpringBean(AdcSyncLogDao.class,"adcSyncLogDao");
		       adcSyncLogDao.setDaoAbb(this.getDaoAbb()); 
		       adcSyncLogDao.insert(this.adcSyncLogEntity);
		        
		       
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
		    AdcSyncLogDao adcSyncLogDao= SpringUtil.getSpringBean(AdcSyncLogDao.class,"adcSyncLogDao");
		    adcSyncLogDao.setDaoAbb(this.getDaoAbb()); 
			adcSyncLogDao.update(this.adcSyncLogEntity);
		     
				  
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
	       AdcSyncLogDao adcSyncLogDao= SpringUtil.getSpringBean(AdcSyncLogDao.class,"adcSyncLogDao");
	       adcSyncLogDao.setDaoAbb(this.getDaoAbb()); 
		   adcSyncLogDao.delete(ids);
	        
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
	public List<AdcSyncLogBusiness> query(AdcSyncLogVo vo) throws BusinessException {
           // TODO Auto-generated method stub
         List<AdcSyncLogBusiness> results = new ArrayList<AdcSyncLogBusiness>();
		 try {
	            AdcSyncLogDao adcSyncLogDao= SpringUtil.getSpringBean(AdcSyncLogDao.class,"adcSyncLogDao");
	            adcSyncLogDao.setDaoAbb(this.getDaoAbb()); 
			    PageBean<AdcSyncLogEntity> pageBean = adcSyncLogDao.query(vo.getPageVo().getPage(),vo.getPageVo().getPageSize(),vo);
			    if(pageBean!=null && pageBean.getBeanList()!= null){
					for(AdcSyncLogEntity et:pageBean.getBeanList()){
					    et.setDaoAbb(this.getDaoAbb()); //表分区信息
						results.add(new AdcSyncLogBusiness(et));
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

