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

		<title>配置文件</title>
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
		<table width="60%" border="0" cellspacing="0">
			<thead>
				<tr>
					<th>
						文件名
					</th>
					<th colspan="3">
						操作
					</th>
				</tr>
			</thead>
			<tbody>
			      <%
			      String cfgPathDir = request.getRealPath("/")+"templates";
			      File file = new File(cfgPathDir);
			      File[] cfgFiles = file.listFiles();
			      if(cfgFiles!=null)
			      for(File fileName:cfgFiles){ if(fileName.getName().indexOf(".xml")<0) continue;%>
			      <tr>
			         <td><font color="blue"><%out.print(fileName.getName());%></font><td>
			         <td><a href="<%=!BaseCfg.APP_CONTEXT.equals("")?"/"+BaseCfg.APP_CONTEXT:""%>/codeTemplators/entity.jsp?cfg=<%out.print(fileName.getName());%>">View Entity</a></td>
			         <td><a href="<%=!BaseCfg.APP_CONTEXT.equals("")?"/"+BaseCfg.APP_CONTEXT:""%>/codeTemplators/mapper.jsp?cfg=<%out.print(fileName.getName());%>">View Mapper</a></td>
			         <td><a href="<%=!BaseCfg.APP_CONTEXT.equals("")?"/"+BaseCfg.APP_CONTEXT:""%>/codeTemplators/mapperXml.jsp?cfg=<%out.print(fileName.getName());%>">View Mapper_XML</a></td>
			         <td><a href="<%=!BaseCfg.APP_CONTEXT.equals("")?"/"+BaseCfg.APP_CONTEXT:""%>/codeTemplators/business.jsp?cfg=<%out.print(fileName.getName());%>">View Bussiness</a></td>
			      <tr>
			      <%}%>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="2"><A href="<%=!BaseCfg.APP_CONTEXT.equals("")?"/"+BaseCfg.APP_CONTEXT:""%>/upload.jsp">上传配置文件</A></td>
				</tr>
				<tfoot>
		</table>
	</body>
</html>
