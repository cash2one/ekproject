<%@ page language="java" import="java.util.*,java.io.PrintWriter" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

    PrintWriter outs = response.getWriter();
    String time = new Date().toGMTString();
    int value =(int)(Math.random()*10000);
    //'{ "foo": [ "bar", 1, { "baz": "thud" } ] }'
	outs.write("{\"time\":\""+time+"\",\"val\":\""+value+"\",\"list\":{\"time\":\""+time+"\",\"val\":\""+value+"\"}}");
    outs.close();
%>
 