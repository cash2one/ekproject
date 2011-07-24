<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="qtone.generator.*" %>
<%@ page import="qtone.generator.util.*" %>
<%
    String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String cfgPath = BaseCfg.CFG_PATH+"/templates/"+request.getParameter("cfg");
	BusinessMap map = new BusinessMap(cfgPath);
	String entityName=map.getClazz();
	entityName=StringHelper.fistChartLowerCase(entityName);
	
	List<FieldItem> mainFields = map.getMainFields();
    List<FieldItem> subFields = map.getJoinFields();
    FieldItem keyItem = map.getPrimaryKeyItem();
    String columnName="";
    
%>
<table width="100%" border="0" cellspacing="0" >
   <thead>
        <tr>
          <th ><input onclick="" type="checkbox" />
          <%for(FieldItem item:mainFields){if(item.getName().equals(keyItem.getName())) continue; columnName=item.getDescript().length()>8?item.getDescript().substring(0,8):item.getDescript();
                 %><th><%=columnName%></th>
          <%}%>
          <%for(FieldItem item:subFields){  columnName=item.getDescript().length()>8?item.getDescript().substring(0,8):item.getDescript();
                 %><th><%=columnName%></th>
          <%}%>
        </tr>
      </thead>
   <tbody>
      #foreach($<%=entityName%> in $page.List)
        <tr>
          <td align="left"><input name="<%=keyItem.getName()%>" value="$!<%=entityName+"."+keyItem.getName()%>" type="checkbox"/></td>
          <%for(FieldItem item:mainFields){ if(item.getName().equals(keyItem.getName())) continue;
          %><td >$!<%=entityName+"."+item.getName()%></td>
          <%}%>
          <%for(FieldItem item:subFields){
          %><td>$!<%=entityName+"."+item.getName()%></td>
          <%}%>
        </tr>
       #end
    </tbody>
    </table>
<div class="mpage"> <span class="mpage_nav">$page.roll</span></div>