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
       //�����Ҫ���ֶ�����
       List<FieldItem> mainFields = map.getMainFields();
       for(FieldItem field:mainFields)%> 
         private <%out.println(field.getType()+"  "+field.getName()+"; //"+field.getDescript());%>
    
    <% 
       //���������ֶ�����
       List<FieldItem> mainFields = map.getJoinFields();
       for(FieldItem field:mainFields)%> 
          private <%out.println(field.getType()+"  "+field.getName()+"; //"+field.getDescript());%>
    
    

	public <%=entityName%>Entry(){
		
	}
	
	public <%=entityName%>Entry(){
        this.areaAbb = areaAbb;        
	}
	


}