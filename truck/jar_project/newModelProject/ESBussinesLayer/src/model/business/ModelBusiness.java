package model.business;

import java.util.ArrayList;
import java.util.List;

import model.dao.ModelDao;
import model.entity.ModelEntity;
import model.vo.ModelVo;

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
 * 描述：业务逻辑层的 模版
 * @author Ethan.Lam 
 * @date 2012-5-24
 *
 */
@Scope("prototype") 
@Service("modelBusiness")
public class ModelBusiness extends BaseBusiness<Long> implements BaseQuery<ModelBusiness,Long,ModelVo>{

	
	//日志服务对象
    static Logger logger = Logger.getLogger(ModelBusiness.class);
   
    
    //实体对象
    private ModelEntity entity;
 
    
	/**
     * 默认构造函数
     */
	 public ModelBusiness() {
		 entity = new ModelEntity();
	 }
	
     /**
     * 默认构造函数
     */
	 public ModelBusiness(ModelEntity entity) {
	      this.entity = entity;
	 }
	
	
	//可访问对象
	 /**
     * @param id 学校ID
     */
    public void setId(long id){
       this.entity.setId(id);    
    }
    /**
     * @return id 学校ID
     */
    public long getId(){ 
       return this.entity.getId();    
    }

    /**
     * @param schoolName 学校的名称
     */
    public void setSchoolName(String schoolName){
    	this.entity.setSchoolName(schoolName);    
    }
    /**
     * @return schoolName 学校的名称
     */
    public String getSchoolName( ){ 
       return this.entity.getSchoolName();    
    }
	
	

	@Override
	protected void checkAndFilter(ActionType type) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getBusinessName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFunctionFlag() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public String getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * 
	 * 功能描述：列表查询(分页)
	 * @author Ethan.Lam
	 * @date 2012-5-31
	 * (non-Javadoc)
	 * @see esfw.core.framework.business.BaseQuery#query(java.io.Serializable)
	 */
	public List<ModelBusiness> query(ModelVo vo) throws BusinessException {
           // TODO Auto-generated method stub
		 List<ModelBusiness> results = new ArrayList<ModelBusiness>();
		 try {
			    ModelDao modelDao= SpringUtil.getSpringBean(ModelDao.class,"modelDao");
			    PageBean<ModelEntity> pageBean = modelDao.query(vo.getPageVo().getPage(),vo.getPageVo().getPageSize(),vo);
			    if(pageBean!=null && pageBean.getBeanList()!= null){
					for(ModelEntity e:pageBean.getBeanList()){
						results.add(new ModelBusiness(e));
					}
			        this.setQeuryRecordTotalNum(pageBean.getTotalRecords());
			    }
			} catch (DaoAccessException ex) {
			    logger.error(getBusinessName()+"执行记录查询操作时出现数据库异常",ex);
			    throw new BusinessException("查询记录发生异常",ex.getMessage(),ex);
			}
		return results;
	}


	/**
	 * 
	 * 功能描述：Load 查询
	 * @author Ethan.Lam
	 * @date 2012-5-31
	 * (non-Javadoc)
	 * @see esfw.core.framework.business.BaseQuery#load(java.lang.Object)
	 * 
	 */
	public Boolean load(Long id) throws BusinessException {
	// TODO Auto-generated method stub
	 try {
		    ModelDao modelDao= SpringUtil.getSpringBean(ModelDao.class,"modelDao");
			ModelEntity entity =  modelDao.load(id);
			if(entity!=null){
				this.entity = entity;
				return true;
			}else{
				this.entity = new ModelEntity();
				// 没有对应的记录是，返回空集对象
				return false;
			}
		} catch (DaoAccessException ex) {
		    logger.error(getBusinessName()+"执行记录findOne操作时出现异常",ex);
		    throw new BusinessException("查询记录详细发生异常",ex.getMessage(),ex);
		}
	}
	

	@Override
	protected void onAdd() throws BusinessException {
		// TODO Auto-generated method stub
		   //打开事务控制
           DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		   def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		   DataSourceTransactionManager txManager =  SpringUtil.getSpringBean(DataSourceTransactionManager.class,"transactionManager");
		   TransactionStatus status = txManager.getTransaction(def);
		   try{
				  ModelDao modelDao= SpringUtil.getSpringBean(ModelDao.class,"modelDao");
			      //请实现业务新增的逻辑过程
				  modelDao.insert(this.entity);
				  
				  txManager.commit(status);
		       }catch(DaoAccessException ex){
		    	  logger.error(getBusinessName()+"执行记录新增后，进行业务提交时出现数据库异常",ex);
		    	  txManager.rollback(status); //事务回滚
		    	  throw new BusinessException("新增记录发生异常",ex.getMessage(),ex);
		    } 
		    txManager = null;
		    def = null;
	}

	
	
	@Override
	protected void onDelete(Long[] ids) throws BusinessException {
		// TODO Auto-generated method stub
		 try {
			  ModelDao modelDao= SpringUtil.getSpringBean(ModelDao.class,"modelDao");
			  if(ids!=null && ids.length >0)
			      modelDao.delete(ids);
			} catch (DaoAccessException ex) {
			  logger.error(getBusinessName()+"执行记录删除操作后，进行提交时出现数据库异常",ex);
			  throw new BusinessException("删除记录发生异常",ex.getMessage(),ex);
		    }
	}

	
	
	@Override
	protected void onModify() throws BusinessException {
		// TODO Auto-generated method stub
		 try {
			  ModelDao modelDao= SpringUtil.getSpringBean(ModelDao.class,"modelDao");
			  modelDao.update(this.entity);	
			} catch (DaoAccessException ex) {
		       logger.error(getBusinessName()+"执行记录修改操作后，执行提交操作时出现数据库异常",ex);
		       throw new BusinessException("修改记录信息发生异常",ex.getMessage(),ex);
			}
	}

	
}
