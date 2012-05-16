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
	String packageName = "qtone.xxt."+map.getDaoNamespace()+"."+map.getEntityNamespace()+"."+map.getNamespace();
%>
package <%=packageName%>;

import java.io.Serializable;
import java.util.*;

/**
 * @description <%=map.getDescription()%> 对应的实体
 * @version <%=map.getVersion()%>
 * @author <%=map.getAuthor()%>  
 * @CreateTime <%=new Date().toString()%>
 */
public class <%=entityName%>Entry implements Serializable {
   
    <% //输出主要的字段属性
       List<FieldItem> mainFields = map.getMainFields();
       for(FieldItem field:mainFields){%> 
         private <%out.print(field.getType()+"  "+field.getName()+"; //"+field.getDescript());}%>
    
    <% //输出从表的字段属性
       List<FieldItem> subFields = map.getJoinFields();
       for(FieldItem field:subFields){%> 
          private <%out.print(field.getType()+"  "+field.getName()+"; //"+field.getDescript());}%>
     
        //默认空构造函数
	  public <%=entityName%>Entry(){
	
	  }
	
	<%if(false){%>
     /**带参数的构造函数
    <% String conParamsStr="";
        for(FieldItem field:mainFields){%>
 	* @param <%out.print(field.getName()+"   "+field.getDescript());
          conParamsStr+=","+field.getType()+" "+field.getName();}
          
        for(FieldItem field:subFields){%>
  	* @param <%out.print(field.getName()+"   "+field.getDescript());
          conParamsStr+=","+field.getType()+" "+field.getName();}
        conParamsStr = conParamsStr.substring(1);
    %>*/
	 public <%=entityName%>Entry(<%=conParamsStr%>){
       <% //输出主要的字段属性
       for(FieldItem field:mainFields){%> 
            this.<%out.print(field.getName()+"="+field.getName()+";");}%>
       <% //输出从表的字段属性
       for(FieldItem field:subFields){%> 
            this.<%out.print(field.getName()+"="+field.getName()+";");}%>
              
	}
	<%}%>
	
	
	<% //生成对应的getting 和 setting 方法
     for(FieldItem field:mainFields){%> 
         /**
          * @param <%=field.getName()+" "+field.getDescript()%>
          */
         public void set<%out.print(StringHelper.fistChartUpperCase(field.getName())+"("+field.getType()+" "+field.getName()+"){");%>
	        this.<%out.print(field.getName()+"="+field.getName()+";");%>    
         }
         /**
          * @return <%=field.getName()+" "+field.getDescript()%>
          */
         public <%=field.getType() %> get<%out.print(StringHelper.fistChartUpperCase(field.getName())+"(  ){ ");%>
	        return this.<%out.print(field.getName()+";");%>    
         }
    <%}%>
    
     <% //生成从表的getting 和 setting 方法
     for(FieldItem field:subFields){%> 
         /**
         * @param <%=field.getName()+" "+field.getDescript()%>
         */
         public void set<%out.print(StringHelper.fistChartUpperCase(field.getName())+"("+field.getType()+" "+field.getName()+"){");%>
	       this.<%out.print(field.getName()+"="+field.getName()+";");%>    
         }
         /**
         * @return <%=field.getName()+" "+field.getDescript()%>
         */
         public <%=field.getType() %> get<%out.print(StringHelper.fistChartUpperCase(field.getName())+"(  ){ ");%>
	        return this.<%out.print(field.getName()+";");%>    
         }
    <%}%>

}