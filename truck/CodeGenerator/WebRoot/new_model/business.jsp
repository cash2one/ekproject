<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="qtone.generator.*" %>
<%@ page import="qtone.generator.util.*" %>
<%
    String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String cfgPath = BaseCfg.CFG_PATH+"/templates/"+request.getParameter("cfg");
	BusinessMap map = new BusinessMap(cfgPath);
	String entityName=map.getClazz();
	
	entityName=StringHelper.fistChartUpperCase(entityName);
	String basePackageName = BaseCfg.basePackageName;
	String businessPackageName = basePackageName+map.getBusinessNamespace()+"."+map.getNamespace();
	String entryPackageName = basePackageName+map.getDaoNamespace()+"."+map.getEntityNamespace()+"."+map.getNamespace();
	String mapperPackageName = basePackageName+map.getDaoNamespace()+"."+map.getNamespace();
	String voPackageName = basePackageName+map.getVoNamespace()+"."+map.getNamespace();
	
	String mapperObjName = StringHelper.fistChartUpperCase(entityName)+"Dao";
	String entryObjName =  StringHelper.fistChartUpperCase(entityName)+"Entity";
	
	
	//如果是该表是分表的就该在接口方法(新增、更新、删除方法，查询方法默认要保留)中加上对应的参数
	String areaAbbParamStr = "@SearchParameter(name =\""+BaseCfg.AREA_ABB+"\")String "+BaseCfg.AREA_ABB;
	String areaAbbDealComment="* @param "+BaseCfg.AREA_ABB+"    地区缩写（分表前缀名:如 “CS”）";
	String isAppendAreaDeal=map.isAreaDeal()?areaAbbParamStr+",":"";
	String appendAreaDealcomment=map.isAreaDeal()?areaAbbDealComment:"";
	
	
	List<FieldItem> mainFields = map.getMainFields();
    List<FieldItem> subFields = map.getJoinFields();
    
    String mapperImport=mapperObjName+" "+StringHelper.fistChartLowerCase(mapperObjName)+"= SpringUtil.getSpringBean("+mapperObjName+".class,\""+StringHelper.fistChartLowerCase(mapperObjName)+"\");";
    
    String tempStr="";
%>

package <%=businessPackageName%>;
 

import java.util.*;

import <%=entryPackageName+"."+map.getClazz()%>Entity;
import <%=mapperPackageName+"."+map.getClazz()%>Dao;
import <%=voPackageName+"."+map.getClazz()%>Vo;

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
 * @description <%=map.getDescription()%> 对应的（业务逻辑类）
 * @version <%=map.getVersion()%>
 * @author <%=map.getAuthor()%>  
 * @CreateTime <%=new Date().toString()%>
 *
 */
@Scope("prototype") 
@Service("<%=StringHelper.fistChartLowerCase(map.getName())%>")
public class <%=map.getClazz()%>Business  extends BaseBusiness<%="<"%>Long<%=">"%> implements BaseQuery<%="<"%><%=map.getClazz()%>Business,Long,<%=map.getClazz()%>Vo<%=">"%>{
    
      //日志服务对象
      static Logger logger = Logger.getLogger(<%=map.getClazz()%>Business.class);
    
      //实体属性
	  //*****************************************************************************************************************
	  private <%=entryObjName%> <%=StringHelper.fistChartLowerCase(entryObjName)%>;
          
	 
	 //构造函数
	 //*****************************************************************************************************************
	
     /**
     * 默认构造函数
     */
	 public <%=map.getClazz()%>Business() {
	     <%=StringHelper.fistChartLowerCase(entryObjName)%> = new <%=entryObjName%>();
	 }
	
     /**
     * 默认构造函数
     */
	 public <%=map.getClazz()%>Business(<%=entryObjName%> entity) {
	      this.<%=StringHelper.fistChartLowerCase(entryObjName)%> = entity;
	 }
	
	
	//属性对应的get 和 set 方法
	//*****************************************************************************************************************
	
    <%for(FieldItem field:mainFields){ //生成对应的getting 和 setting 方法%> 
        /**
         * @param <%=field.getName()+" "+field.getDescript()%>
         */
         <%=field.getIsReadonly()?"private":"public" %> void set<%=StringHelper.fistChartUpperCase(field.getName())+"("+field.getType()+" "+field.getName()+"){"%>
	        this.<%=StringHelper.fistChartLowerCase(entryObjName)%>.set<%=StringHelper.fistChartUpperCase(field.getName())+"("+field.getName()+");" %>     
         }
        /**
         * @return <%=field.getName()+" "+field.getDescript()%>
         */
         public <%=field.getType() %> get<%=StringHelper.fistChartUpperCase(field.getName())+"( ){ "%>
	        return this.<%=StringHelper.fistChartLowerCase(entryObjName)%>.get<%=StringHelper.fistChartUpperCase(field.getName())+"( );" %>   
         }
    <%}%>
    
     <%for(FieldItem field:subFields){ //生成从表的getting 方法%>          
        /**
         * @param <%=field.getName()+" "+field.getDescript()%>
         */
         private void set<%out.print(StringHelper.fistChartUpperCase(field.getName())+"("+field.getType()+" "+field.getName()+"){");%>
	        this.<%=StringHelper.fistChartLowerCase(entryObjName)%>.set<%=StringHelper.fistChartUpperCase(field.getName())+"("+field.getName()+");" %>
         }
         
        /**
          * @return <%=field.getName()+" "+field.getDescript()%>
         */
         public <%=field.getType() %> get<%out.print(StringHelper.fistChartUpperCase(field.getName())+"( ){ ");%>
	        return this.<%=StringHelper.fistChartLowerCase(entryObjName)%>.get<%=StringHelper.fistChartUpperCase(field.getName())+"( );" %>     
         }
    <%}%>
    
    
    //子类必须要实现父类的抽象方法（模块）。
	//******************************************************************************************************************************
	
	/**
	 * 设置业务逻辑名称
	 */
	@Override
	public String getBusinessName(){
	   // TODO Auto-generated method stub
		 return "<%=map.getNamespace()+"."+map.getClazz()%>Business";
	}


	/**
	 * 设置业务逻辑功能标识 对应数据表的功能标识
	 */
	@Override
	public String getFunctionFlag() {
		// TODO Auto-generated method stub
		 return "<%=map.getClazz()%>Business";
	}

	/**
	 * 设置获取模块名称
	 */
	@Override
	public String getModel() {
		// TODO Auto-generated method stub
		 return "<%=map.getNamespace()%>";
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
	
	
   <% if(map.getPrimaryKeyItem()!=null){//有主键才可以有此方法 %>
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
		        <%=mapperImport%>
			<%=map.getClazz()%>Entity entity =  <%=StringHelper.fistChartLowerCase(map.getClazz())%>Dao.load(id);
			if(entity!=null){
				this.<%=StringHelper.fistChartLowerCase(entryObjName)%> = entity;
				return true;
			}else{
				this.<%=StringHelper.fistChartLowerCase(entryObjName)%> = new <%=map.getClazz()%>Entity();
				// 没有对应的记录是，返回空集对象
				return false;
			}
		} catch (DaoAccessException ex) {
		    logger.error(getBusinessName()+" 执行记录  load 操作时出现异常",ex);
		    throw new BusinessException("查询记录详细发生异常",ex.getMessage(),ex);
		}
	}


	 <%}//END IF%>
	
	
  <% if(map.isTransactionOff()){//非事务控制类型的方法 %>
	/**
	 * 新增
	 */
	@Override
	protected void onAdd() throws BusinessException {
		// TODO Auto-generated method stub
		   try{
		       <%=mapperImport%>
		       <%=StringHelper.fistChartLowerCase(map.getClazz())%>Dao.insert(this.<%=StringHelper.fistChartLowerCase(map.getClazz())%>Entity);
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
		        <%=mapperImport%>
		     <%=StringHelper.fistChartLowerCase(map.getClazz())%>Dao.update(this.<%=StringHelper.fistChartLowerCase(map.getClazz())%>Entity);
	    }catch(DaoAccessException ex){
	          logger.error(getBusinessName()+"执行记录修改操作后，执行提交操作时出现数据库异常",ex);
	          throw new BusinessException("修改记录信息发生异常",ex.getMessage(),ex);
	    }
	}
	
	
	/**
	 * 删除
	 */
	@Override
	protected void onDelete(long ids[]) throws BusinessException {
		// TODO Auto-generated method stub
		try{
	           <%=mapperImport%>
		   <%=StringHelper.fistChartLowerCase(mapperObjName)%>.delete(ids);
	    }catch(DaoAccessException ex){
	       logger.error(getBusinessName()+"执行记录删除操作后，进行提交时出现数据库异常",ex);
	       throw new BusinessException("删除记录发生异常",ex.getMessage(),ex);
	    }
	}

   	<%}//END IF%>
   	
   	
   	<% if(!map.isTransactionOff()){//事务控制类型的方法 %>
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
		      
		       <%=mapperImport%>
		       <%=StringHelper.fistChartLowerCase(mapperObjName)%>.insert(<%="this."+StringHelper.fistChartLowerCase(entryObjName)%>);
		       <%=(true)?"":StringHelper.fistChartLowerCase(entryObjName)+"= null;"%> 
		       
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
		
		//执行数据验证
		checkAndFilter(ActionType.mdf);
        
        //打开事务控制
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		DataSourceTransactionManager txManager =  SpringUtil.getSpringBean(DataSourceTransactionManager.class,"transactionManager");
		TransactionStatus status = txManager.getTransaction(def);
		try{
		    <%=mapperImport%>
			<%=StringHelper.fistChartLowerCase(mapperObjName)%>.update(<%="this."+StringHelper.fistChartLowerCase(entryObjName)%>);
		    <%=(true)?"":StringHelper.fistChartLowerCase(entryObjName)+"= null;"%> 
				  
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
	       <%=mapperImport%>
		   <%=StringHelper.fistChartLowerCase(mapperObjName)%>.delete(ids);
	        
		    txManager.commit(status);	        
	        }catch(DaoAccessException ex){
		    	logger.error(getBusinessName()+"执行记录删除后，进行业务提交时出现数据库异常",ex);
		    	txManager.rollback(status); //事务回滚
		    	throw new BusinessException("删除记录时发生异常",ex.getMessage(),ex);
		    } 
		    txManager = null;
		    def = null;
	}
   	<%}//END IF%>

	


	/**
	 * 
	 * 功能描述：列表查询(分页)
	 * @author Ethan.Lam
	 * (non-Javadoc)
	 * @see esfw.core.framework.business.BaseQuery#query(java.io.Serializable)
	 */
	public List<<%=map.getClazz()%>Business> query(<%=map.getClazz()%>Vo vo) throws BusinessException {
           // TODO Auto-generated method stub
         List<<%=map.getClazz()%>Business> results = new ArrayList<<%=map.getClazz()%>Business>();
		 try {
	            <%=mapperImport%>
			    PageBean<<%=map.getClazz()%>Entity> pageBean = <%=StringHelper.fistChartLowerCase(mapperObjName)%>.query(vo.getPageVo().getPage(),vo.getPageVo().getPageSize(),vo);
			    if(pageBean!=null && pageBean.getBeanList()!= null){
					for(<%=map.getClazz()%>Entity et:pageBean.getBeanList()){
						results.add(new <%=map.getClazz()%>Business(et));
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
