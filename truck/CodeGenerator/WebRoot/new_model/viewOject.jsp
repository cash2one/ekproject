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
	String packageName = BaseCfg.basePackageName+map.getVoNamespace()+"."+map.getNamespace();
	String entryPackageName = BaseCfg.basePackageName+map.getDaoNamespace()+"."+map.getEntityNamespace()+"."+map.getNamespace();
	
%>
package <%=packageName%>;

import java.util.*;

import <%=entryPackageName+"."+map.getClazz()%>Entity;

import esfw.core.framework.controller.PageVo;
import esfw.core.framework.controller.ViewObject;
import esfw.core.framework.dao.mapper.OrderItem;

/**
 * @description <%=map.getDescription()%> 对应的 ViewOject 实体 vo
 * @version <%=map.getVersion()%>
 * @author <%=map.getAuthor()%>  
 * @CreateTime <%=new Date().toString()%>
 */
public class   <%=entityName%>Vo  extends <%=entityName%>Entity implements ViewObject{
   
    <% //输出主要的字段属性
       List<FieldItem> mainFields = map.getMainFields();
       for(FieldItem field:mainFields){   if(field.getType().toLowerCase().trim().indexOf("date")<0) continue;  %> 
         private <%out.print(field.getType()+"  "+field.getName()+"1; //"+field.getDescript()+" -- 开始时间 "); %>
         private <%out.print(field.getType()+"  "+field.getName()+"2; //"+field.getDescript()+" -- 结束时间 ");
       }%>
    
    <% //输出从表的字段属性
       List<FieldItem> subFields = map.getJoinFields();
       for(FieldItem field:subFields){ if(field.getType().toLowerCase().trim().indexOf("date")<0) continue;  %> 
          private <%out.print(field.getType()+"  "+field.getName()+"1; //"+field.getDescript()+" -- 开始时间 ");%>
          private <%out.print(field.getType()+"  "+field.getName()+"1; //"+field.getDescript()+" -- 结束时间 ");}%>
          
      
      private List<OrderItem> orderList; //排序控制
      
      //默认空构造函数
	  public <%=entityName%>Vo(){
	
	   <% //输出主要的字段属性
       for(FieldItem field:mainFields){   if(field.getType().toLowerCase().trim().indexOf("long")<0 && field.getType().toLowerCase().trim().indexOf("int")<0 ) continue;%> 
               this.set<%out.println(StringHelper.fistChartUpperCase(field.getName())+"(-1);");}%>
  
    
    <% //输出从表的字段属性
       for(FieldItem field:subFields){ if(field.getType().toLowerCase().trim().indexOf("long")<0 && field.getType().toLowerCase().trim().indexOf("int")<0 )  continue;%> 
             this.set<%out.println(StringHelper.fistChartUpperCase(field.getName())+"(-1);");}%>
	
	  }
		
	
	<% //生成对应的getting 和 setting 方法
     for(FieldItem field:mainFields){ if(field.getType().toLowerCase().trim().indexOf("date")<0) continue; %> 
         /**
          * @param <%=field.getName()+"1 "+field.getDescript()+" -- 开始时间 "%>
          */
         public void set<%out.print(StringHelper.fistChartUpperCase(field.getName())+"1("+field.getType()+" "+field.getName()+"){");%>
	        this.<%out.print(field.getName()+"1="+field.getName()+";");%>    
         }
         /**
          * @return <%=field.getName()+"1 "+field.getDescript()+" -- 结束时间 "%>
          */
         public <%=field.getType() %> get<%out.print(StringHelper.fistChartUpperCase(field.getName())+"1( ){ ");%>
	        return this.<%out.print(field.getName()+"1;");%>    
         }
         
         /**
          * @param <%=field.getName()+"2 "+field.getDescript()+" -- 开始时间 "%>
          */
         public void set<%out.print(StringHelper.fistChartUpperCase(field.getName())+"2("+field.getType()+" "+field.getName()+"){");%>
	        this.<%out.print(field.getName()+"2="+field.getName()+";");%>    
         }
         
         /**
          * @return <%=field.getName()+"2 "+field.getDescript()+" -- 结束时间 "%>
          */
         public <%=field.getType() %> get<%out.print(StringHelper.fistChartUpperCase(field.getName())+"2( ){ ");%>
	        return this.<%out.print(field.getName()+"2;");%>    
         }
        
    <%}%>
    
    
     <% //生成从表的getting 和 setting 方法
     for(FieldItem field:subFields){ if(field.getType().toLowerCase().trim().indexOf("date")<0) continue;%> 
         /**
          * @param <%=field.getName()+"1 "+field.getDescript()+" -- 开始时间 "%>
          */
         public void set<%out.print(StringHelper.fistChartUpperCase(field.getName())+"1("+field.getType()+" "+field.getName()+"){");%>
	        this.<%out.print(field.getName()+"1="+field.getName()+";");%>    
         }
         /**
          * @return <%=field.getName()+"1 "+field.getDescript()+" -- 结束时间 "%>
          */
         public <%=field.getType() %> get<%out.print(StringHelper.fistChartUpperCase(field.getName())+"1( ){ ");%>
	        return this.<%out.print(field.getName()+"1;");%>    
         }
         
         /**
          * @param <%=field.getName()+"2 "+field.getDescript()+" -- 开始时间 "%>
          */
         public void set<%out.print(StringHelper.fistChartUpperCase(field.getName())+"2("+field.getType()+" "+field.getName()+"){");%>
	        this.<%out.print(field.getName()+"2="+field.getName()+";");%>    
         }
         
         /**
          * @return <%=field.getName()+"2 "+field.getDescript()+" -- 结束时间 "%>
          */
         public <%=field.getType() %> get<%out.print(StringHelper.fistChartUpperCase(field.getName())+"2( ){ ");%>
	        return this.<%out.print(field.getName()+"2;");%>    
         }
 
    <%}%>
    
    //页面的分页 Vo 信息
    public PageVo getPageVo() {
	    return pageVo;
	}
    
    //列表查询的排序控制
	public List<OrderItem> getOrderList() {
		return orderList;
	}

    //设置列表查询的排序
	public void setOrderList(List<OrderItem> orderList) {
		this.orderList = orderList;
	}

}