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
	String basePackageName = "qtone.xxt.";
	String businessPackageName = basePackageName+map.getBusinessNamespace()+"."+map.getNamespace();
	String entryPackageName = basePackageName+map.getDaoNamespace()+"."+map.getEntityNamespace()+"."+map.getNamespace();
	String mapperPackageName = basePackageName+map.getDaoNamespace()+"."+map.getMapperNamespace()+"."+map.getNamespace();
	
	
	String mapperObjName = StringHelper.fistChartUpperCase(entityName)+"Mapper";
	String entryObjName =  StringHelper.fistChartUpperCase(entityName)+"Entry";
	
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
import java.util.List;


import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Scope;


import org.apache.log4j.Logger;
import esfw.core.framework.SpringUtil;
import esfw.core.framework.annotation.SeacherFun;
import esfw.core.framework.annotation.SearchParameter;
import esfw.core.framework.business.BaseBusiness;
import esfw.core.framework.business.enumeration.ActionType;
import esfw.core.framework.dao.mapper.OrderItem;
import esfw.core.framework.exception.BusinessException;
import esfw.core.framework.exception.DaoAccessException;

import <%=entryPackageName+"."+map.getClazz()%>Entry;
import <%=mapperPackageName+"."+map.getClazz()%>Mapper;


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
public class <%=map.getName()%> extends BaseBusiness {
    
      //日志服务对象
      static Logger logger = Logger.getLogger(<%=map.getName()%>.class);
    
      //实体属性
	  //*****************************************************************************************************************
	  private <%=entryObjName%> <%=StringHelper.fistChartLowerCase(entryObjName)%>;
          
	 
	 //构造函数
	 //*****************************************************************************************************************
	
     /**
     * 默认构造函数
     */
	 public <%=map.getName()%>() {
	     <%=StringHelper.fistChartLowerCase(entryObjName)%> = new <%=entryObjName%>();
	 }
	
     /**
     * 默认构造函数
     */
	 public <%=map.getName()%>(<%=entryObjName%> entry) {
	      this.<%=StringHelper.fistChartLowerCase(entryObjName)%> = entry;
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
		 return "<%=map.getName()%>";
	}


	/**
	 * 设置业务逻辑功能标识 对应数据表的功能标识
	 */
	@Override
	public String getFunctionFlag() {
		// TODO Auto-generated method stub
		 return "<%=map.getName()%>";
	}

	/**
	 * 设置获取模块名称
	 */
	@Override
	public String getModel() {
		// TODO Auto-generated method stub
		 return "";
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
     * 根据主键（<%=map.getPrimaryKeyItem().getName()%>）查询某条记录
     * @param <%=map.getPrimaryKeyItem().getName()%>
     * @return Boolean true:代表查询到结果,false：找不到记录
     */
	public Boolean findOne(<%="long  "+map.getPrimaryKeyItem().getName()%>)  throws BusinessException {
	    <%=mapperImport%>
		<%=entryObjName%> entry = null;
	    try{
	         entry = <%=StringHelper.fistChartLowerCase(mapperObjName)%>.findOne(this.getDaoAbb(),<%=map.getPrimaryKeyItem().getName()%>);
	    }catch(DaoAccessException ex){
	       logger.error(getBusinessName()+"执行记录findOne操作时出现异常",ex);
	       throw new BusinessException("查询记录详细发生异常",ex.getMessage(),ex);
	    }
		 
		 if (entry != null){<% //赋值
	     for(FieldItem field:mainFields){%>
	           this.set<%out.print(StringHelper.fistChartUpperCase(field.getName())+"(entry.get"+StringHelper.fistChartUpperCase(field.getName())+"());");
	     }
         for(FieldItem field:subFields){%>
                   this.set<%out.print(StringHelper.fistChartUpperCase(field.getName())+"(entry.get"+StringHelper.fistChartUpperCase(field.getName())+"());");}%>
		  
		   entry = null;
		   return true;
		  }else{ 
		        this.<%=StringHelper.fistChartLowerCase(entryObjName)%> = new <%=entryObjName%>();
		        return false;  
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
		       <%=StringHelper.fistChartLowerCase(mapperObjName)%>.insert<%=map.getClazz()%>(<%=map.isAreaDeal()?"this.getDaoAbb(),":""%><%="this."+StringHelper.fistChartLowerCase(entryObjName)%>);
		       <%=(true)?"":StringHelper.fistChartLowerCase(entryObjName)+"= null;"%> 
	       }catch(DaoAccessException ex){
	          logger.error(getBusinessName()+"执行记录新增后，执行提交操作时出现数据库异常",ex);
	          throw new BusinessException("新建记录发生异常",ex.getMessage(),ex);
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
			<%=StringHelper.fistChartLowerCase(mapperObjName)%>.update<%=map.getClazz()%>(<%=map.isAreaDeal()?"this.getDaoAbb(),":""%><%="this."+StringHelper.fistChartLowerCase(entryObjName)%>);
		    <%=(true)?"":StringHelper.fistChartLowerCase(entryObjName)+"= null;"%> 
	    }catch(DaoAccessException ex){
	          logger.error(getBusinessName()+"执行记录修改操作后，执行提交操作时出现数据库异常",ex);
	          throw new BusinessException("修改记录信息发生异常",ex.getMessage(),ex);
	    }
	}

   	<%}//END IF%>
   	
   	
   	<% if(!map.isTransactionOff()){//事务控制类型的方法 %>
	/**
	 * 新增
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
		       <%=StringHelper.fistChartLowerCase(mapperObjName)%>.insert<%=map.getClazz()%>(<%=map.isAreaDeal()?"this.getDaoAbb(),":""%><%="this."+StringHelper.fistChartLowerCase(entryObjName)%>);
		       <%=(true)?"":StringHelper.fistChartLowerCase(entryObjName)+"= null;"%> 
		       
		       //请实现业务新增的逻辑过程
		       
		       
		       }catch(DaoAccessException ex){
		    	  logger.error(getBusinessName()+"执行记录新增后，进行业务提交时出现数据库异常",ex);
		    	  txManager.rollback(status); //事务回滚
		    	  throw new BusinessException(ex.getUserOperateExMsg(),ex.getMessage(),ex);
		    } 
		    txManager = null;
		    def = null;
	}
	
	
	/**
	 * 修改
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
			<%=StringHelper.fistChartLowerCase(mapperObjName)%>.update<%=map.getClazz()%>(<%=map.isAreaDeal()?"this.getDaoAbb(),":""%><%="this."+StringHelper.fistChartLowerCase(entryObjName)%>);
		    <%=(true)?"":StringHelper.fistChartLowerCase(entryObjName)+"= null;"%> 
	        
	        }catch(DaoAccessException ex){
		    	logger.error(getBusinessName()+"执行记录修改后，进行业务提交时出现数据库异常",ex);
		    	txManager.rollback(status); //事务回滚
		    	throw new BusinessException(ex.getUserOperateExMsg(),ex.getMessage(),ex);
		    } 
		    txManager = null;
		    def = null;
	}
   	<%}//END IF%>

	
	/**
	 * 删除
	 */
	@Override
	protected void onDelete(long ids[]) throws BusinessException {
		// TODO Auto-generated method stub
		<%=mapperImport%>
		try{
		   <%=StringHelper.fistChartLowerCase(mapperObjName)%>.delete<%=map.getClazz()%>(<%=map.isAreaDeal()?"this.getDaoAbb(),":""%>ids);
	    }catch(DaoAccessException ex){
	       logger.error(getBusinessName()+"执行记录删除操作后，进行提交时出现数据库异常",ex);
	       throw new BusinessException("删除记录发送异常",ex.getMessage(),ex);
	    }
	}



	
	  /**
	   * 查询结果集合
	   * @param startRow   开始记录的行数
	   * @param pageSize   设置每页显示的记录数
     <%String conParamsStr="";tempStr="";String funcParamsStr="";
   for(FieldItem field:mainFields){ funcParamsStr+=",entry.get"+StringHelper.fistChartUpperCase(field.getName())+"()";if(field.getName().equals(map.getPrimaryKeyItem()!=null?map.getPrimaryKeyItem().getName():null)) continue; %>
	   <%
	       if(field.getType().toLowerCase().indexOf("date")>=0||field.getType().toLowerCase().indexOf("time")>=0){
	    	  out.println("* @param   "+field.getName()+"1   "+field.getDescript()+" （大于或等于开始时间）");
	    	  out.print("           * @param   "+field.getName()+"2   "+field.getDescript()+" （小于或等于结束时间）");
		      tempStr+=","+field.getName()+"1,"+field.getName()+"2";
		      conParamsStr+=",@SearchParameter(name =\""+field.getName()+"1\")"+field.getType()+" "+field.getName()+"1,@SearchParameter(name =\""+field.getName()+"2\")"+field.getType()+" "+field.getName()+"2";
	       }else{
	          out.print("* @param   "+field.getName()+"   "+field.getDescript());
	          tempStr+=","+field.getName();
	          if("long".equals(field.getType().toLowerCase())||"int".equals(field.getType().toLowerCase()))
	        	conParamsStr+=",@SearchParameter(name =\""+field.getName()+"\", defaultValue=\"-1\" )"+field.getType()+" "+field.getName();
	        	  else
	            conParamsStr+=",@SearchParameter(name =\""+field.getName()+"\")"+field.getType()+" "+field.getName();}
	 
   }//end for
   for(FieldItem field:subFields){ funcParamsStr+=",entry.get"+StringHelper.fistChartUpperCase(field.getName())+"()";%>
	   <%
	      if(field.getType().toLowerCase().indexOf("date")>=0||field.getType().toLowerCase().indexOf("time")>=0){
	    	  out.println("* @param   "+field.getName()+"1   "+field.getDescript()+" （大于或等于开始时间）");
	    	  out.print("            * @param   "+field.getName()+"2   "+field.getDescript()+" （小于或等于结束时间）");
		      tempStr+=","+field.getName()+"1,"+field.getName()+"2";
		      conParamsStr+=",@SearchParameter(name =\""+field.getName()+"1\")"+field.getType()+" "+field.getName()+"1,@SearchParameter(name =\""+field.getName()+"2\")"+field.getType()+" "+field.getName()+"2";
	      }else{ 
	          out.print("* @param   "+field.getName()+"   "+field.getDescript());
	          if("long".equals(field.getType().toLowerCase())||"int".equals(field.getType().toLowerCase()))
		          conParamsStr+=",@SearchParameter(name =\""+field.getName()+"\", defaultValue=\"-1\" )"+field.getType()+" "+field.getName();
		      else
	              conParamsStr+=",@SearchParameter(name =\""+field.getName()+"\")"+field.getType()+" "+field.getName();
	          tempStr+=","+field.getName();}
	     }//end for
         funcParamsStr=funcParamsStr.substring(1);
         tempStr=tempStr.substring(1);;
	     conParamsStr = conParamsStr.substring(1);%>
	   * @param orderList  //控制排序
	   * @return List<<%=map.getName()%>>
	   */
	@SeacherFun(nameAlias="<%=map.getName()%>Seacher")
	public List<<%=map.getName()%>> query<%=entityName%>s(@SearchParameter(defaultValue = "1",name = "startRow")int startRow, @SearchParameter(defaultValue = "20",name = "pageSize")int pageSize,
				<%=conParamsStr%>,
				@SearchParameter(name="orderList")List<OrderItem>orderList) throws BusinessException{
		   //实例化List对象		
		   List<<%=map.getName()%>> list = new ArrayList<<%=map.getName()%>>();
		   //查询结果实体
		   <%=mapperImport%>
		  try{ 
		   this.setQeuryRecordTotalNum(<%=StringHelper.fistChartLowerCase(mapperObjName)%>.qeury<%=map.getClazz()%>sRecordCount(this.getDaoAbb(),<%=tempStr%>));
		   List<<%=entryObjName%>> entryList = <%=StringHelper.fistChartLowerCase(mapperObjName)%>.qeury<%=map.getClazz()%>s(startRow,pageSize,this.getDaoAbb(),<%=tempStr%>,orderList);
	       if (entryList != null){
			  for (<%=entryObjName%> entry : entryList) {
				   list.add(new <%=map.getName()%>(entry));
				   entry = null;
			  }
			  entryList = null;
		   }
		  }catch(DaoAccessException ex){
	          logger.error(getBusinessName()+"执行记录查询操作时出现数据库异常",ex);
	          throw new BusinessException("查询记录发生异常",ex.getMessage(),ex);
	      }
		return list;
	}
				
			

	
	
	
	//自定义方法
	//*****************************************************************************************************************
	
	
}
