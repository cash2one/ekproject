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
	String packageName = basePackageName+map.getDaoNamespace()+"."+map.getMapperNamespace()+"."+map.getNamespace();
	String interPackageName = basePackageName+map.getDaoNamespace()+"."+map.getNamespace();
	
	//如果是该表是分表的就该在接口方法(新增、更新、删除方法，查询方法默认要保留)中加上对应的参数
	String areaAbbParamStr = "@Param(\""+BaseCfg.AREA_ABB+"\")String "+BaseCfg.AREA_ABB;
	String areaAbbDealComment="* @param "+BaseCfg.AREA_ABB+"    地区缩写（分表前缀名:如 “CS”）";
	String isAppendAreaDeal=map.isAreaDeal()?areaAbbParamStr+",":"";
	String appendAreaDealcomment=map.isAreaDeal()?areaAbbDealComment:"";
	
	
	List<FieldItem> mainFields = map.getMainFields();
    List<FieldItem> subFields = map.getJoinFields();
    
%>
package <%=packageName%>;

import java.util.*;

import org.springframework.stereotype.Component;
import esfw.core.framework.dao.MyBaticGenericDao;

import <%=basePackageName+map.getDaoNamespace()+"."+map.getNamespace()+"."+map.getClazz()%>Dao;
import <%=basePackageName+map.getDaoNamespace()+"."+map.getEntityNamespace()+"."+map.getNamespace()+"."+map.getClazz()%>Entity;
import <%=basePackageName+map.getVoNamespace()+"."+map.getNamespace()+"."+map.getClazz()%>Vo;


/**
 *
 * @description <%=map.getDescription()%> 类 对应的 Dao 实现层
 * @version <%=map.getVersion()%>
 * @author <%=map.getAuthor()%>  
 * @CreateTime <%=new Date().toString()%>
 *
 */
@Component("<%=StringHelper.fistChartLowerCase(entityName)%>Dao")
public class <%=entityName%>DaoImp extends MyBaticGenericDao <%="<"%><%=entityName%>Vo,Long,<%=entityName%>Entity<%=">"%> implements <%=entityName%>Dao{
    
     //自动生成的方法
	 //************************************************************************************************************************

	/**
	 * (non-Javadoc)
	 * @see esfw.core.framework.dao.MyBaticGenericDao#mapNameSpace()
	 * 功能描述：命名空间定义
	 * @author Ethan.Lam
	 * @dateTime 2012-6-3
	 * @return
	 *
	 */
    protected String mapNameSpace(){
    	return "<%=interPackageName+"."+entityName%>Dao";
    }
    
    
	//自定义方法
	//************************************************************************************************************************
	
	
	
}
