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
	String packageName = basePackageName+map.getDaoNamespace()+"."+map.getMapperNamespace()+"."+map.getNamespace();
  
	
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
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import <%=basePackageName+map.getDaoNamespace()+"."+map.getEntityNamespace()+"."+map.getNamespace()+"."+map.getClazz()%>Entry;
import <%=basePackageName+map.getDaoNamespace()+"."+map.getMapperNamespace()%>.MyBatisMapper;
import <%=basePackageName+map.getDaoNamespace()+"."+map.getMapperNamespace()%>.OrderItem;
import <%=basePackageName+map.getDaoNamespace()%>.exceptions.DaoException;

/**
 *
 * @description <%=map.getDescription()%> 对应的Mapper（持久化接口类）
 * @version <%=map.getVersion()%>
 * @author <%=map.getAuthor()%>  
 * @CreateTime <%=new Date().toString()%>
 *
 */
@Component("<%=StringHelper.fistChartLowerCase(entityName)%>Mapper")
public interface <%=entityName%>Mapper extends MyBatisMapper {

    
     //自动生成的方法
	 //************************************************************************************************************************
    
   <% if(map.getPrimaryKeyItem()!=null){//有主键才可以有此方法 %>
	/**
	 * 根据主键查询对应的记录
	 <%=areaAbbDealComment%>
	 * @param <%=map.getPrimaryKeyItem().getName()%>  记录对应的主键
	 * @return <%=entityName%>Entry
	 */
	public <%=entityName%>Entry findOne(<%=areaAbbParamStr%>,@Param("<%=map.getPrimaryKeyItem().getName()%>")long <%=map.getPrimaryKeyItem().getName()%>) throws DaoException;
	<% } %>
	
	 /**
	   * 列表查询
	   * @param startRow   开始记录的行数
	   * @param pageSize   设置每页显示的记录数
	   <%=areaAbbDealComment%>
     <%String conParamsStr="";
     for(FieldItem field:mainFields){ if(field.getName().equals(map.getPrimaryKeyItem()!=null?map.getPrimaryKeyItem().getName():null)) continue;
          if(field.getType().toLowerCase().indexOf("date")>=0||field.getType().toLowerCase().indexOf("time")>=0){
	    	 conParamsStr+=",@Param(\""+field.getName()+"1\")"+field.getType()+" "+field.getName()+"1"+",@Param(\""+field.getName()+"2\")"+field.getType()+" "+field.getName()+"2";%>
	   * @param <% out.println(field.getName()+"1   "+field.getDescript()+" （大于或等于开始时间）");out.print("           * @param "+field.getName()+"2   "+field.getDescript()+" （小于或等于结束时间）");
	      }else{%>
	   * @param <%  out.print(field.getName()+"   "+field.getDescript());  
	       	 conParamsStr+=",@Param(\""+field.getName()+"\")"+field.getType()+" "+field.getName();
	      }
	  }
     for(FieldItem field:subFields){
	     if(field.getType().toLowerCase().indexOf("date")>=0||field.getType().toLowerCase().indexOf("time")>=0){
	    	  conParamsStr+=",@Param(\""+field.getName()+"1\")"+field.getType()+" "+field.getName()+"1"+",@Param(\""+field.getName()+"2\")"+field.getType()+" "+field.getName()+"2";%>
	   * @param <% out.println(field.getName()+"1   "+field.getDescript()+" （大于或等于开始时间）");out.print("           * @param "+field.getName()+"2   "+field.getDescript()+" （小于或等于结束时间）");
	        }else{%>
	   * @param <%  out.print(field.getName()+"   "+field.getDescript());  
	          conParamsStr+=",@Param(\""+field.getName()+"\")"+field.getType()+" "+field.getName();
	       }
	     }
	     conParamsStr = conParamsStr.substring(1);%>
	   * @param orderList  //控制排序
	   * @return List<<%=entityName%>Entry>
	   */
	public List<<%=entityName%>Entry> qeury<%=entityName%>s(@Param("startRow")int startRow, @Param("pageSize")int pageSize,<%=areaAbbParamStr%>,
				<%=conParamsStr%>,@Param("orderList")List<OrderItem>orderList) throws DaoException;


	/**
	 * 列表的记录总数统计
       <%=appendAreaDealcomment%>
<%conParamsStr="";
for(FieldItem field:mainFields){ if(field.getName().equals(map.getPrimaryKeyItem()!=null?map.getPrimaryKeyItem().getName():null)) continue;
    if(field.getType().toLowerCase().indexOf("date")>=0||field.getType().toLowerCase().indexOf("time")>=0){
  	 conParamsStr+=",@Param(\""+field.getName()+"1\")"+field.getType()+" "+field.getName()+"1"+",@Param(\""+field.getName()+"2\")"+field.getType()+" "+field.getName()+"2";%>
 	 * @param <% out.println(field.getName()+"1   "+field.getDescript()+"  （大于或等于开始时间）");out.print("         * @param "+field.getName()+"2   "+field.getDescript()+" （小于或等于结束时间）");
    }else{%>
 	 * @param <%  out.print(field.getName()+"   "+field.getDescript());  
     	 conParamsStr+=",@Param(\""+field.getName()+"\")"+field.getType()+" "+field.getName();
    }
}
 for(FieldItem field:subFields){
   if(field.getType().toLowerCase().indexOf("date")>=0||field.getType().toLowerCase().indexOf("time")>=0){
  	  conParamsStr+=",@Param(\""+field.getName()+"1\")"+field.getType()+" "+field.getName()+"1"+",@Param(\""+field.getName()+"2\")"+field.getType()+" "+field.getName()+"2";%>
	 * @param <% out.println(field.getName()+"1   "+field.getDescript()+"  （大于或等于开始时间）");out.print("         * @param "+field.getName()+"2   "+field.getDescript()+" （小于或等于结束时间）");
      }else{%>
 	 * @param <%  out.print(field.getName()+"   "+field.getDescript());  
        conParamsStr+=",@Param(\""+field.getName()+"\")"+field.getType()+" "+field.getName();
     }
    }conParamsStr = conParamsStr.substring(1);%>
	 * @return 列表的记录数
	 */
	public int qeury<%=entityName%>sRecordCount(<%=areaAbbParamStr%>,<%=conParamsStr%>) throws DaoException ;
	
	
	/**
	 * 新增记录
	 <%=appendAreaDealcomment%>
	 * @param <%=StringHelper.fistChartLowerCase(entityName)%>
	 * @return
	 */
	public int insert<%=entityName%>(<%=isAppendAreaDeal%> @Param("<%=StringHelper.fistChartLowerCase(entityName)%>")<%=entityName%>Entry <%=StringHelper.fistChartLowerCase(entityName)%>) throws DaoException;
	
	
	<% if(map.getPrimaryKeyItem()!=null){//有主键才可以有此方法 %>
	/**
	 * 更新记录 
	 <%=appendAreaDealcomment%>
	 * @param <%=StringHelper.fistChartLowerCase(entityName)%>
	 * @return
	 */
	public int update<%=entityName%>(<%=isAppendAreaDeal%> @Param("<%=StringHelper.fistChartLowerCase(entityName)%>") <%=entityName%>Entry <%=StringHelper.fistChartLowerCase(entityName)%>) throws DaoException;
    <%}%>
	
	<% if(map.getPrimaryKeyItem()!=null){//有主键才可以有此方法 %>
	/**
	 * 删除记录
	 <%=appendAreaDealcomment%>
	 * @param <%=map.getPrimaryKeyItem().getName()%>s  记录对应的主键
	 * @return
	 */
	public int delete<%=entityName%>(<%=isAppendAreaDeal%> @Param("<%=map.getPrimaryKeyItem().getName()%>s") long[] <%=map.getPrimaryKeyItem().getName()%>s) throws DaoException ;
	<%}%>
	
	
	//自定义方法
	//************************************************************************************************************************
	
	
	
}
