<%@ page language="java" import="java.util.*,java.io.PrintWriter" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

    PrintWriter outs = response.getWriter();
    StringBuffer jsonString  = new StringBuffer();
    String str = "{"+ 
     "identifier: 'sn',"+
     "items: ["+
     "{sn: '092334', name: ' 刘君 ', sex: ' 男 ' ,dateOfBirth: '1966-9-29', dept:' 研发 ',"+ 
     " onboard: '1999-2-20', reportMgr: '082322'},"+
      "{ sn: '099871', name: ' 李丽 ', sex: ' 女 ' ,dateOfBirth: '1979-8-19', dept: ' 销售 ',"+ 
      "onboard: '2000-1-22', reportMgr: '072121'},"+
    	"{sn: '099890', name: ' 周晶 ', sex: ' 女 ' ,dateOfBirth: '1976-8-8', dept: ' 研发 ',"+ 
    	"onboard: '2003-5-10', reportMgr: '092334'},"+ 
    	"{ sn: '105632', name: ' 张涛 ', sex: ' 男 ' ,dateOfBirth: '1984-9-29', dept: ' 研发 ',"+ 
    	"onboard: '2007-2-20', reportMgr: '099890'},"+ 
     "]"+
    "}";
    jsonString.append(str);
    str=str.replace(" ","");
    str=str.replace("'","\"");
	outs.write(jsonString.toString());
    outs.close();
%>
 