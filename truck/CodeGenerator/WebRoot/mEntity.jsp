<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
    String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String entityName="Student";
    
	
	
%>
package qtone.xxt.dao.entity.edu;

import java.io.Serializable;

public class <%=entityName%>Entry implements Serializable {

	private long id;

	public <%=entityName%>Entry(){
		
	}
	
	public <%=entityName%>Entry(){
        this.areaAbb = areaAbb;        
	}
	


}