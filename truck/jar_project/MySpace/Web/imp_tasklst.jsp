<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="java.util.*,cn.elamzs.common.eimport.item.TaskModel"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>导入任务状态列表</title>
	</head>
	<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="40"
		marginwidth="0" marginheight="0">
	  <div>
	    
		<table width="85%">
		  <%
		      List<TaskModel> tasks =(List<TaskModel> )request.getAttribute("tasks");
		      if(tasks!=null)
		      
		    %>
		      <tr><td width="5%"><font color="green">序列号</font></td>
		      <td width="10%"><font color="green">文件名</font></td>
		      <td width="3%"><font color="green">记录行</font></td>
		      <td width="3%"><font color="green">导入状态</font></td>
		      <td width="3%"><font color="green">耗时(秒)</font></td>
		      <td width="10%"><font color="green">开始时间</font></td>
		      <td width="10%"><font color="green">结束时间</font></td>
		      <td width="10%"><font color="green">操作</font></td>
		      </tr>
		    <% 	  
		      for(TaskModel task:tasks){
		   %> 
		     <tr>
		          <td><%=task.getHanderId()%></td>
		    	  <td><%=task.getFileName()%></td>
		    	  <td><%=task.getRecordNum()%></td>
		    	  <td><%=(task.getState()==1?"已完成":(task.getState()==0?"处理中":"失败"))%></td>
		    	  <td><%=task.getProcTime()%></td>
		    	  <td><%=task.getStartTime()%></td>
		    	  <td><%=task.getFinishedTime()==null?"  --   ":task.getFinishedTime()%></td>
		    	  <td><a href="download?action=result&id=<%=task.getHanderId()%>">下载结果</a>
		    	  &nbsp;&nbsp;<a href="download?action=resource&id=<%=task.getHanderId()%>">原文件</a>
		    	  &nbsp;&nbsp;<a href="download?action=template">模版</a>
		    	  </td>
             </tr>
          <%         
		      }
		  %>
		  <tr>
		  </tr>
		</table>
		</div>
	</body>
</html>
