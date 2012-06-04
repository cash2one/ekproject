<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@ page import="java.io.*,qtone.generator.util.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>模版V2</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	</head>

	<body>
		<table width="80%" border="1px" cellspacing="0">
				<tr align="center">
					<th colspan="1" align="left">
					     模版配置文件名
					</th>
					<th colspan="1">
					      实体
					</th>
					<th colspan="2">
					      持久化接口
					</th>
					<th colspan="1">
					     业务对象
					</th>
					<th colspan="1">
					  ViewObject
					</th>
				</tr>
			     
			      <%
			      String cfgPathDir = request.getRealPath("/")+"templates";
			      File file = new File(cfgPathDir);
			      File[] cfgFiles = file.listFiles();
			      if(cfgFiles!=null)
			      for(File fileName:cfgFiles){ if(fileName.getName().indexOf(".xml")<0) continue;%>
			      <tr>
			         <td><a href="<%=(!BaseCfg.APP_CONTEXT.equals("")?"/"+BaseCfg.APP_CONTEXT:"")+"/templates/"+fileName.getName()+"\""%>"><%=fileName.getName() %></a></td>
			         <td><a href="<%=!BaseCfg.APP_CONTEXT.equals("")?"/"+BaseCfg.APP_CONTEXT:""%>/new_model/entity.jsp?cfg=<%out.print(fileName.getName());%>">View Entity</a></td>
			         <td>
			         <a href="<%=!BaseCfg.APP_CONTEXT.equals("")?"/"+BaseCfg.APP_CONTEXT:""%>/new_model/baseDao.jsp?cfg=<%out.print(fileName.getName());%>">View Base</a> |
			         <a href="<%=!BaseCfg.APP_CONTEXT.equals("")?"/"+BaseCfg.APP_CONTEXT:""%>/new_model/daoImp.jsp?cfg=<%out.print(fileName.getName());%>">View DaoImp</a></td>
			         <td><a href="<%=!BaseCfg.APP_CONTEXT.equals("")?"/"+BaseCfg.APP_CONTEXT:""%>/new_model/mapperXml.jsp?cfg=<%out.print(fileName.getName());%>">View Mapper_XML</a></td>
			         <td><a href="<%=!BaseCfg.APP_CONTEXT.equals("")?"/"+BaseCfg.APP_CONTEXT:""%>/new_model/business.jsp?cfg=<%out.print(fileName.getName());%>">View Bussiness</a></td>
			         <td><a href="<%=!BaseCfg.APP_CONTEXT.equals("")?"/"+BaseCfg.APP_CONTEXT:""%>/new_model/viewOject.jsp?cfg=<%out.print(fileName.getName());%>">View PageModel</a></td>
			      </tr>
			      <%}%>
		</table>
	</body>
</html>
