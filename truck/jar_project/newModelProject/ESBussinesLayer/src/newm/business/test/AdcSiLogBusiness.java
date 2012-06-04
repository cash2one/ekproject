package newm.business.test;
 

import java.util.*;

import newm.dao.entity.test.AdcSiLogEntity;
import newm.dao.inter.test.AdcSiLogDao;
import newm.vo.test.AdcSiLogVo;

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
 * @description fdsf 对应的（业务逻辑类）
 * @version v1.0.0
 * @author Ethanlam  
 * @CreateTime Mon Jun 04 13:07:58 GMT 2012
 *
 */
@Scope("prototype") 
@Service("adcSiLogBusiness")
public class AdcSiLogBusiness  extends BaseBusiness<Long> implements BaseQuery<AdcSiLogBusiness,Long,AdcSiLogVo>{
    
      //日志服务对象
      static Logger logger = Logger.getLogger(AdcSiLogBusiness.class);
    
      //实体属性
	  //*****************************************************************************************************************
	  private AdcSiLogEntity adcSiLogEntity;
          
	 
	 //构造函数
	 //*****************************************************************************************************************
	
     /**
     * 默认构造函数
     */
	 public AdcSiLogBusiness() {
	     adcSiLogEntity = new AdcSiLogEntity();
	 }
	
     /**
     * 默认构造函数
     */
	 public AdcSiLogBusiness(AdcSiLogEntity entity) {
	      this.adcSiLogEntity = entity;
	 }
	
	
	//属性对应的get 和 set 方法
	//*****************************************************************************************************************
	
     
        /**
         * @param id 
         */
         public void setId(long id){
	        this.adcSiLogEntity.setId(id);     
         }
        /**
         * @return id 
         */
         public long getId( ){ 
	        return this.adcSiLogEntity.getId( );   
         }
     
        /**
         * @param phone 
         */
         public void setPhone(String phone){
	        this.adcSiLogEntity.setPhone(phone);     
         }
        /**
         * @return phone 
         */
         public String getPhone( ){ 
	        return this.adcSiLogEntity.getPhone( );   
         }
     
        /**
         * @param studentid 
         */
         public void setStudentid(String studentid){
	        this.adcSiLogEntity.setStudentid(studentid);     
         }
        /**
         * @return studentid 
         */
         public String getStudentid( ){ 
	        return this.adcSiLogEntity.getStudentid( );   
         }
     
        /**
         * @param servicecode   0 订购成功     1 未知错误     3 学生编号长度不是11位或SPID错误或学生不存在     4 绑定的手机号码不合法     5 扣费手机不是移动号码     6 业务类型不存在     7 扣费模式不存在     8 已存在定购关系（成功的或正在处理中的）     20 SPInfo为空值     21 SPID为空     22 SPPassword为空     23 密码错误     9001 数据库操作异常     9 IP鉴权不通过
         */
         public void setServicecode(String servicecode){
	        this.adcSiLogEntity.setServicecode(servicecode);     
         }
        /**
         * @return servicecode   0 订购成功     1 未知错误     3 学生编号长度不是11位或SPID错误或学生不存在     4 绑定的手机号码不合法     5 扣费手机不是移动号码     6 业务类型不存在     7 扣费模式不存在     8 已存在定购关系（成功的或正在处理中的）     20 SPInfo为空值     21 SPID为空     22 SPPassword为空     23 密码错误     9001 数据库操作异常     9 IP鉴权不通过
         */
         public String getServicecode( ){ 
	        return this.adcSiLogEntity.getServicecode( );   
         }
     
        /**
         * @param ip 
         */
         public void setIp(String ip){
	        this.adcSiLogEntity.setIp(ip);     
         }
        /**
         * @return ip 
         */
         public String getIp( ){ 
	        return this.adcSiLogEntity.getIp( );   
         }
     
        /**
         * @param dt 创建时间
         */
         public void setDt(Date dt){
	        this.adcSiLogEntity.setDt(dt);     
         }
        /**
         * @return dt 创建时间
         */
         public Date getDt( ){ 
	        return this.adcSiLogEntity.getDt( );   
         }
    
    
     
    
    
    //子类必须要实现父类的抽象方法（模块）。
	//******************************************************************************************************************************
	
	/**
	 * 设置业务逻辑名称
	 */
	@Override
	public String getBusinessName(){
	   // TODO Auto-generated method stub
		 return "test.AdcSiLogBusiness";
	}


	/**
	 * 设置业务逻辑功能标识 对应数据表的功能标识
	 */
	@Override
	public String getFunctionFlag() {
		// TODO Auto-generated method stub
		 return "AdcSiLogBusiness";
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
		        AdcSiLogDao adcSiLogDao= SpringUtil.getSpringBean(AdcSiLogDao.class,"adcSiLogDao");
		    adcSiLogDao.setDaoAbb(this.getDaoAbb());
			AdcSiLogEntity entity =  adcSiLogDao.load(id);
			if(entity!=null){
			    entity.setDaoAbb(this.getDaoAbb()); //表分区信息
				this.adcSiLogEntity = entity;
				return true;
			}else{
				this.adcSiLogEntity = new AdcSiLogEntity();
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
		      
		       AdcSiLogDao adcSiLogDao= SpringUtil.getSpringBean(AdcSiLogDao.class,"adcSiLogDao");
		       adcSiLogDao.setDaoAbb(this.getDaoAbb()); 
		       adcSiLogDao.insert(this.adcSiLogEntity);
		        
		       
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
		    AdcSiLogDao adcSiLogDao= SpringUtil.getSpringBean(AdcSiLogDao.class,"adcSiLogDao");
		    adcSiLogDao.setDaoAbb(this.getDaoAbb()); 
			adcSiLogDao.update(this.adcSiLogEntity);
		     
				  
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
	       AdcSiLogDao adcSiLogDao= SpringUtil.getSpringBean(AdcSiLogDao.class,"adcSiLogDao");
	       adcSiLogDao.setDaoAbb(this.getDaoAbb()); 
		   adcSiLogDao.delete(ids);
	        
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
	public List<AdcSiLogBusiness> query(AdcSiLogVo vo) throws BusinessException {
           // TODO Auto-generated method stub
         List<AdcSiLogBusiness> results = new ArrayList<AdcSiLogBusiness>();
		 try {
	            AdcSiLogDao adcSiLogDao= SpringUtil.getSpringBean(AdcSiLogDao.class,"adcSiLogDao");
	            adcSiLogDao.setDaoAbb(this.getDaoAbb()); 
			    PageBean<AdcSiLogEntity> pageBean = adcSiLogDao.query(vo.getPageVo().getPage(),vo.getPageVo().getPageSize(),vo);
			    if(pageBean!=null && pageBean.getBeanList()!= null){
					for(AdcSiLogEntity et:pageBean.getBeanList()){
					    et.setDaoAbb(this.getDaoAbb()); //表分区信息
						results.add(new AdcSiLogBusiness(et));
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

