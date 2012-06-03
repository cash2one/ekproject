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
package <%=interPackageName%>;

import java.util.*;

import esfw.core.framework.dao.GenericDao;

import <%=basePackageName+map.getDaoNamespace()+"."+map.getEntityNamespace()+"."+map.getNamespace()+"."+map.getClazz()%>Entity;
import <%=basePackageName+map.getVoNamespace()+"."+map.getNamespace()+"."+map.getClazz()%>Vo;


/**
 *
 * @description <%=map.getDescription()%> 对应的Dao 接口层定义
 * @version <%=map.getVersion()%>
 * @author <%=map.getAuthor()%>  
 * @CreateTime <%=new Date().toString()%>
 *
 */
public interface <%=entityName%>Dao extends GenericDao<%="<"%><%=entityName%>Vo,Long,<%=entityName%>Entity<%=">"%> {    
 
	//自定义接口方法
	//************************************************************************************************************************
	
	
	
}
