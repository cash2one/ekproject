<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@ page import="qtone.generator.*" %>
<%@ page import="qtone.generator.util.*" %>
<%
    String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String cfgPath = request.getRealPath("/")+"demo.xml";
	BusinessMap map = new BusinessMap(cfgPath);
	String entityName=map.getClazz();
	entityName=StringHelper.fistChartUpperCase(entityName);
	String packageName = "qtone.xxt."+map.getDaoNamespace()+"."+map.getEntityNamespace()+"."+map.getNamespace();
%>
package <%=packageName%>;

import java.io.Serializable;

/**
 * @description <%=map.getDescription()%> ��Ӧ��ʵ��
 * @version <%=map.getVersion()%>
 * @author <%=map.getAuthor()%>  
 * @CreateTime <%=new Date().toString()%>
 */
public class <%=entityName%>Entry implements Serializable {
   
    <% //�����Ҫ���ֶ�����
       List<FieldItem> mainFields = map.getMainFields();
       for(FieldItem field:mainFields){%> 
         private <%out.print(field.getType()+"  "+field.getName()+"; //"+field.getDescript());}%>
    
    <% //����ӱ���ֶ�����
       List<FieldItem> subFields = map.getJoinFields();
       for(FieldItem field:subFields){%> 
          private <%out.print(field.getType()+"  "+field.getName()+"; //"+field.getDescript());}%>
     
    //Ĭ�Ͽչ��캯��
	public <%=entityName%>Entry(){
	
	}
    <% 
        String conParamsStr="";
        for(FieldItem field:mainFields)
          conParamsStr+=","+field.getType()+" "+field.getName();
        for(FieldItem field:subFields)
          conParamsStr+=","+field.getType()+" "+field.getName();
        conParamsStr = conParamsStr.substring(1);
    %>
    
    //�������Ĺ��캯��
	public <%=entityName%>Entry(<%=conParamsStr%>){
       <% //�����Ҫ���ֶ�����
       for(FieldItem field:mainFields){%> 
            this.<%out.print(field.getName()+"="+field.getName()+";");}%>
       <% //����ӱ���ֶ�����
       for(FieldItem field:subFields){%> 
            this.<%out.print(field.getName()+"="+field.getName()+";");}%>
              
	}
	
	<% //���ɶ�Ӧ��getting �� setting ����
     for(FieldItem field:mainFields){%> 
         public void set<%out.print(StringHelper.fistChartUpperCase(field.getName())+"("+field.getType()+" "+field.getName()+"){");%>
	        this.<%out.print(field.getName()+"="+field.getName()+";");%>    
         }
    <%}%>
    
     <% //���ɴӱ��getting �� setting ����
     for(FieldItem field:subFields){%> 
         public void set<%out.print(StringHelper.fistChartUpperCase(field.getName())+"("+field.getType()+" "+field.getName()+"){");%>
	       this.<%out.print(field.getName()+"="+field.getName()+";");%>    
         }
    <%}%>

}