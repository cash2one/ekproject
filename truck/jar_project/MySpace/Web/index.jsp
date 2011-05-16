<%@ page language="java" contentType="text/html; charset=gb2312"
	pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>文件上传实例</title>
	</head>
	<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="40"
		marginwidth="0" marginheight="0">
		<center>
			<h1>
				文件上传演示
			</h1>
			<form name="/upload" method="POST" action="upload.do"
				ENCTYPE="multipart/form-data">
				<table border="1" width="450" cellpadding="4" cellspacing="2"
					bordercolor="#9BD7FF">
					<tr>
						<td width="100%" colspan="2">
							文件一：
							<input name="file1" size="40" type="file">
						</td>
					</tr>
					<tr>
						<td width="100%" colspan="2">
							文件名2：
							<input name="file2" size="40" type="file">
						</td>
					</tr>
					<tr>
						<td width="100%" colspan="2">
							文件3：
							<input name="file3" size="40" type="file">
						</td>
					</tr>
				</table>
				<br />
				<br />
				<table>
					<tr>
						<td align="center">
							<input name="upload" type="submit" value="开始上传" />
						</td>
						<td align="center">
							<a href="listTask">查看导入状态</a>
						</td>
					</tr>
				</table>
			</form>
		</center>
	</body>
</html>
