package esfw.core.framework.dao.extend.interceptor;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.DefaultParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;

import esfw.core.framework.dao.extend.dialect.Dialect;
import esfw.core.framework.dao.extend.dialect.OracleDialect;

/**
 * 
 * 描述：连接处理Sql 实现物理分页的功能
 * @author Ethan.Lam
 * @dateTime 2012-6-2
 *
 */
@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class})})    
public class PaginationInterceptor implements Interceptor {    
    
	//日志服务对象
    static Logger log = Logger.getLogger(PaginationInterceptor.class);

    
    public Object intercept(Invocation invocation) throws Throwable {  
    	  	
    	    System.out.println("...................PaginationInterceptor...............");
    	
		    StatementHandler statementHandler = (StatementHandler)invocation.getTarget();    
		    MetaObject metaStatementHandler = MetaObject.forObject(statementHandler);    
		    
		    
//		    statementHandler.
//		    ((HashMap)statementHandler.getParameterHandler().getParameterObject()).put("schoolId",1000);
//		    ((HashMap)statementHandler.getParameterHandler().getParameterObject()).put("schoolName","fdsfdsfds");
//		    ((HashMap)statementHandler.getParameterHandler().getParameterObject()).put("siId",1000);
		    
		    
		    Object t = statementHandler.getParameterHandler().getParameterObject();
		    
		    PreparedStatement st = null;
		    
//		    statementHandler.getParameterHandler().setParameters(st);
		    
//		    metaStatementHandler.setValue("schoolName","fdf");
		   
		    
//		    statementHandler.getParameterHandler().setParameters(arg0)
		    
		    RowBounds rowBounds = (RowBounds)metaStatementHandler.getValue("delegate.rowBounds");    
		    if(rowBounds == null || rowBounds == RowBounds.DEFAULT){    
		        return invocation.proceed();    
		    }    
		        
		    DefaultParameterHandler defaultParameterHandler = (DefaultParameterHandler)metaStatementHandler.getValue("delegate.parameterHandler");    
//		    Map parameterMap = (Map)defaultParameterHandler.getParameterObject();    
//		    Object sidx = parameterMap.get("_sidx");    
//		    Object sord = parameterMap.get("_sord");    
//		    
		  
		    
		    String originalSql = (String)metaStatementHandler.getValue("delegate.boundSql.sql");    
		        
//		    if(sidx != null && sord != null){    
//		        originalSql = originalSql + " order by " + sidx + " " + sord;    
//		    }    
		        
		    Configuration configuration = (Configuration)metaStatementHandler.getValue("delegate.configuration");    
		        
		    Dialect.Type databaseType  = null;    
		    try{    
		        databaseType = Dialect.Type.valueOf(configuration.getVariables().getProperty("dialect").toUpperCase());    
		    } catch(Exception e){    
		        //ignore    
		    }    
		    if(databaseType == null){    
		        throw new RuntimeException("the value of the dialect property in configuration.xml is not defined : " + configuration.getVariables().getProperty("dialect"));    
		    }    
		    Dialect dialect = null;    
		    switch(databaseType){    
		        case ORACLE:    
		            dialect = new OracleDialect();    
		            break;    
		        case MYSQL://需要实现MySQL的分页逻辑    
		            break;    
		                
		    }    
		        
		    metaStatementHandler.setValue("delegate.boundSql.sql", dialect.getLimitString(originalSql, rowBounds.getOffset(), rowBounds.getLimit()) );    
		    metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET );    
		    metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT );    
		    if(log.isDebugEnabled()){    
		        BoundSql boundSql = statementHandler.getBoundSql();    
//		        log.debug("生成分页SQL : " + boundSql.getSql());    
		    }    
		    return invocation.proceed();    
    }
    
    
    /* (non-Javadoc)  
     * @see org.apache.ibatis.plugin.Interceptor#plugin(java.lang.Object)  
     */    
    public Object plugin(Object target) {    
        return Plugin.wrap(target, this);    
    }
    
    
    
    /* (non-Javadoc)  
     * @see org.apache.ibatis.plugin.Interceptor#setProperties(java.util.Properties)  
     */    
    public void setProperties(Properties arg0) {    
        // TODO Auto-generated method stub    
            
    }    
    
}    