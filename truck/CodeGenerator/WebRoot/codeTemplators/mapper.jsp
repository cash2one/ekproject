<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@ page import="qtone.generator.*" %>
<%@ page import="qtone.generator.util.*" %>
<%
    String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String cfgPath = request.getRealPath("/")+"templates/"+request.getParameter("cfg");
	BusinessMap map = new BusinessMap(cfgPath);
	String entityName=map.getClazz();
	
	entityName=StringHelper.fistChartUpperCase(entityName);
	String basePackageName = "qtone.xxt.";
	String packageName = basePackageName+map.getDaoNamespace()+"."+map.getMapperNamespace()+"."+map.getNamespace();
  
	
	//如果是该表是分表的就该在接口方法(新增、更新、删除方法，查询方法默认要保留)中加上对应的参数
	String areaAbbParamStr = "@Param(\"areaAbb\")String areaAbb";
	String areaAbbDealComment="* @param areaAbb    地区缩写（分表前缀名:如 “CS”）";
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

/**
 *
 * @description <%=map.getDescription()%> 对应的Mapper（持久化接口类）
 * @version <%=map.getVersion()%>
 * @author <%=map.getAuthor()%>  
 * @CreateTime <%=new Date().toString()%>
 *
 */
@Component("<%=StringHelper.toLowerCase(entityName)%>Mapper")
public interface <%=entityName%>Mapper extends MyBatisMapper {

    
        //自动生成的方法
	    //************************************************************************************************************************

	/**
	 * 根据主键查询对应的记录
	 <%=areaAbbDealComment%>
	 * @param <%=map.getPrimaryKeyItem().getName()%>  记录对应的主键
	 * @return <%=entityName%>Entry
	 */
	public <%=entityName%>Entry findOne(<%=areaAbbParamStr%>,@Param("<%=map.getPrimaryKeyItem().getName()%>")<%=map.getPrimaryKeyItem().getType()%> <%=map.getPrimaryKeyItem().getName()%>);
	
	
	 /**
	   * 列表查询
	   * @param startRow   开始记录的行数
	   * @param pageSize   设置每页显示的记录数
	   <%=areaAbbDealComment%>
     <%String conParamsStr="";
     for(FieldItem field:mainFields){ if(field.getName().equals(map.getPrimaryKeyItem().getName())) continue;
          if(field.getType().toLowerCase().indexOf("date")>=0||field.getType().toLowerCase().indexOf("time")>=0){
	    	 conParamsStr+=",@Param(\""+field.getName()+"1\")"+field.getType()+" "+field.getName()+"1"+",@Param(\""+field.getName()+"2\")"+field.getType()+" "+field.getName()+"2";%>
	   * @param <% out.println(field.getName()+"1   //"+field.getDescript()+" 开始时间");out.print("           * @param "+field.getName()+"2   //"+field.getDescript()+" 结束时间");
	      }else{%>
	   * @param <%  out.print(field.getName()+"   //"+field.getDescript());  
	       	 conParamsStr+=",@Param(\""+field.getName()+"\")"+field.getType()+" "+field.getName();
	      }
	  }
     for(FieldItem field:subFields){
	     if(field.getType().toLowerCase().indexOf("date")>=0||field.getType().toLowerCase().indexOf("time")>=0){
	    	  conParamsStr+=",@Param(\""+field.getName()+"1\")"+field.getType()+" "+field.getName()+"1"+",@Param(\""+field.getName()+"2\")"+field.getType()+" "+field.getName()+"2";%>
	   * @param <% out.println(field.getName()+"1   //"+field.getDescript()+" 开始时间");out.print("           * @param "+field.getName()+"2   //"+field.getDescript()+" 结束时间");
	        }else{%>
	   * @param <%  out.print(field.getName()+"   //"+field.getDescript());  
	          conParamsStr+=",@Param(\""+field.getName()+"\")"+field.getType()+" "+field.getName();
	       }
	     }
	     conParamsStr = conParamsStr.substring(1);%>
	   * @param orderList  //控制排序
	   * @return List<<%=entityName%>Entry>
	   */
	public List<<%=entityName%>Entry> qeury<%=entityName%>s(@Param("startRow")int startRow, @Param("pageSize")int pageSize,<%=areaAbbParamStr%>,
				<%=conParamsStr%>,@Param("orderList")List<OrderItem>orderList);


	/**
	 * 列表的记录总数统计
       <%=appendAreaDealcomment%>
<%conParamsStr="";
for(FieldItem field:mainFields){ if(field.getName().equals(map.getPrimaryKeyItem().getName())) continue;
    if(field.getType().toLowerCase().indexOf("date")>=0||field.getType().toLowerCase().indexOf("time")>=0){
  	 conParamsStr+=",@Param(\""+field.getName()+"1\")"+field.getType()+" "+field.getName()+"1"+",@Param(\""+field.getName()+"2\")"+field.getType()+" "+field.getName()+"2";%>
 	 * @param <% out.println(field.getName()+"1   //"+field.getDescript()+" 开始时间");out.print("         * @param "+field.getName()+"2   //"+field.getDescript()+" 结束时间");
    }else{%>
 	 * @param <%  out.print(field.getName()+"   //"+field.getDescript());  
     	 conParamsStr+=",@Param(\""+field.getName()+"\")"+field.getType()+" "+field.getName();
    }
}
 for(FieldItem field:subFields){
   if(field.getType().toLowerCase().indexOf("date")>=0||field.getType().toLowerCase().indexOf("time")>=0){
  	  conParamsStr+=",@Param(\""+field.getName()+"1\")"+field.getType()+" "+field.getName()+"1"+",@Param(\""+field.getName()+"2\")"+field.getType()+" "+field.getName()+"2";%>
	 * @param <% out.println(field.getName()+"1   //"+field.getDescript()+" 开始时间");out.print("         * @param "+field.getName()+"2   //"+field.getDescript()+" 结束时间");
      }else{%>
 	 * @param <%  out.print(field.getName()+"   //"+field.getDescript());  
        conParamsStr+=",@Param(\""+field.getName()+"\")"+field.getType()+" "+field.getName();
     }
    }conParamsStr = conParamsStr.substring(1);%>
	 * @return 列表的记录数
	 */
	public int qeury<%=entityName%>sRecordCount(<%=areaAbbParamStr%>,<%=conParamsStr%>);
	
	
	/**
	 * 新增记录
	 <%=appendAreaDealcomment%>
	 * @param <%=StringHelper.fistChartLowerCase(entityName)%>
	 * @return
	 */
	public int insert<%=entityName%>(<%=isAppendAreaDeal%> <%=entityName%>Entry <%=StringHelper.toLowerCase(entityName)%>);
	
	
	/**
	 * 更新记录 
	 <%=appendAreaDealcomment%>
	 * @param <%=StringHelper.fistChartLowerCase(entityName)%>
	 * @return
	 */
	public int update<%=entityName%>(<%=isAppendAreaDeal%> <%=entityName%>Entry <%=StringHelper.toLowerCase(entityName)%>);
	
	
	/**
	 * 删除记录
	 <%=appendAreaDealcomment%>
	 * @param <%=map.getPrimaryKeyItem().getName()%>s  记录对应的主键
	 * @return
	 */
	public int delete<%=entityName%>(<%=isAppendAreaDeal%> @Param("<%=map.getPrimaryKeyItem().getName()%>s") String[] <%=map.getPrimaryKeyItem().getName()%>s);
	
	
	
	//自定义方法
	//************************************************************************************************************************
	
	
	
}
