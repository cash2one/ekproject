<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@ page import="qtone.generator.*" %>
<%@ page import="qtone.generator.util.*" %>

<%
    String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String cfgPath = request.getRealPath()+"/demo.xml";
	BusinessMap map = new BusinessMap(cfgPath);
	String entityName=map.getClazz();
	entityName=StringHelper.fistChartUpperCase(entityName);
	
%>
package qtone.xxt.dao.entity.edu;

import java.io.Serializable;

public class <%=entityName%>Entry implements Serializable {
    <% 
       //输出主要的字段属性
       List<FieldItem> mainFields = map.getMainFields();
       for(FieldItem field:mainFields)%> 
         private <%out.println(field.getType()+"  "+field.getName()+"; //"+field.getDescript());%>
    
    <% 
       //输出连表的字段属性
       List<FieldItem> mainFields = map.getJoinFields();
       for(FieldItem field:mainFields)%> 
          private <%out.println(field.getType()+"  "+field.getName()+"; //"+field.getDescript());%>
    
    

	public <%=entityName%>Entry(){
		
	}
	
	public <%=entityName%>Entry(){
        this.areaAbb = areaAbb;        
	}
	


}