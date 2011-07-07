<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@ page import="qtone.generator.*" %>
<%@ page import="qtone.generator.util.*" %>

<%
    String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String cfgPath = request.getRealPath("/")+"templates/"+request.getParameter("cfg");
	BusinessMap map = new BusinessMap(cfgPath);
	String entityName=map.getClazz();
	
	
	String basePackageName = "qtone.xxt.";
	String packageName = basePackageName+map.getDaoNamespace()+"."+map.getEntityNamespace()+"."+map.getNamespace()+".";
	String mapperName=basePackageName+map.getDaoNamespace()+"."+map.getMapperNamespace()+"."+map.getNamespace()+"."+StringHelper.fistChartUpperCase(entityName+"Mapper");
	String entryName = packageName+StringHelper.fistChartUpperCase(entityName+"Entry");
	String isDefineEntityName = !map.isAreaDeal()?("resultType=\""+entryName)+"\"":"";
	
	String moduleName=StringHelper.fistChartUpperCase(entityName);
	
	entityName=StringHelper.fistChartLowerCase(entityName);
	
	//��������
	List<FieldItem> mainFields = map.getMainFields();
    //�ӱ�����
	List<FieldItem> subFields = map.getJoinFields();
    
    FieldItem primaryKey = map.getPrimaryKeyItem();
    
    String allFieldStr =""; 
	Map<String,String[]> mainFieldSet = new HashMap<String,String[]>();
	for(FieldItem item:mainFields){
		
		//�������Ϣ�����ֶΡ�ʵ��������
		if(item.getName()!=map.getPrimaryKeyItem().getName()) 
		    mainFieldSet.put(item.getName(),new String[]{StringHelper.toLowerCase(item.getTableAlias()),StringHelper.toLowerCase(item.getSourceField()),StringHelper.fistChartLowerCase(item.getName()),item.getType()});
		 
		allFieldStr+=","+(StringHelper.toLowerCase(item.getTableAlias())+"."+StringHelper.toLowerCase(item.getSourceField())+" as "+StringHelper.fistChartLowerCase(item.getName()));
	}
	
	Map<String,String[]> subFieldSet = new HashMap<String,String[]>();
	for(FieldItem item:subFields){
		//�������Ϣ�����ֶΡ�ʵ��������
		subFieldSet.put(item.getName(),new String[]{StringHelper.toLowerCase(item.getTableAlias()),StringHelper.toLowerCase(item.getSourceField()),StringHelper.fistChartLowerCase(item.getName()),item.getType()});
		allFieldStr+=","+(StringHelper.toLowerCase(item.getTableAlias())+"."+StringHelper.toLowerCase(item.getSourceField())+" as "+StringHelper.fistChartLowerCase(item.getName()));
	}
	allFieldStr=allFieldStr.substring(1);
	
	
	List<JoinItem> joins = map.getJoinTable();
	
%>



<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" 
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="<%=mapperName%>">
   <cache />

  <!-- ����������ϵͳ�Զ����ɵ� -->

  <!-- ��ʾ�ļ�¼��Ӧ����-->
  <sql id="<%=moduleName%>Columns" >
		    <%=allFieldStr%>
  </sql>
  
  
  <!-- �б��Ӧ�Ĳ�ѯ�������  -->
  <sql id="queryOptions">
       <where><% String items[] = null,insertFieldStr="",valuesStr="";
               for(String key:mainFieldSet.keySet()){
            	  items = mainFieldSet.get(key);%>
            	  <%out.print(SqlXmlCreator.appendWhereOptions(key,items[0],items[1],items[3]));  
            	    insertFieldStr+=","+items[1];
 	        	      valuesStr+=",#{"+entityName+"."+items[2]+"}";
               }
               for(String key:subFieldSet.keySet()){
             	  items = subFieldSet.get(key);%>         
                  <%out.print(SqlXmlCreator.appendWhereOptions(key,items[0],items[1],items[3]));}%> 
        </where> 
  </sql>



  <!-- �б��ѯ��Ӧ������ѡ��  -->
  <sql id="orderControl">
       <if test="orderList!=null">
		  <trim prefix="ORDER BY" prefixOverrides=",">
				<foreach collection="orderList" item="order" open=""  separator="," close="" >
				   <choose><%for(String key:mainFieldSet.keySet()){
				    	   items =  mainFieldSet.get(key);%> 
				       <%out.print(SqlXmlCreator.appendOrderOptions(key,items[0],items[1]));}%>
				  </choose>
			 </foreach>
		 </trim>
	   </if>
  </sql>

 
 
  <!-- �б��ѯ��Ӧ�ı��ϵSQL  -->
  <sql id="querySqlMain">
			  <% out.print(SqlXmlCreator.wrapperMainQuerySql(map.getTable(),map.getTableAlias(),map.getTopJoinTable())); %>
			  <include refid="queryOptions"/>
  </sql>
  
 
   <!-- ����ID��ѯ��¼ -->
   <select id="findOne" resultType="<%=entryName%>">
	     SELECT  <include refid="<%=moduleName%>Columns"/>              
		    FROM <include refid="querySqlMain"/>
			where <%=primaryKey.getTableAlias()%>.<%=primaryKey.getName()%>=<%="#{"+primaryKey.getName()+"}"%>
   </select>


   <!-- ���ؼ�¼��������� -->
   <select id="qeury<%=moduleName%>sRecordCount" resultType="int">
        SELECT count(*) num  FROM  <include refid="querySqlMain"/> 
   </select>
   
   
    <!-- ��ҳ��ѯ��Ӧ�ļ�¼ -->
   <select id="qeury<%=moduleName%>s" resultType="<%=entryName%>">
      SELECT * FROM (  
	        SELECT A.*,ROWNUM RN FROM (
	               SELECT <include refid="<%=moduleName%>Columns"/>   
		           FROM <include refid="querySqlMain"/>
		           <include refid="orderControl"/>
		    ) A WHERE ROWNUM &lt;=<%="${startRow}+${pageSize}"%> ) 
		 WHERE RN &gt;=<%="#{startRow}"%>
   </select>


   <!-- ������¼ -->
   <insert id="insert<%=moduleName%>" useGeneratedKeys="false" >
       <selectKey resultType="long" order="AFTER" keyProperty="<%=entityName%>.<%=primaryKey.getName()%>">
	       SELECT <%=map.getSequence()%>.currval FROM  DUAL
	   </selectKey>
             INSERT INTO <%=map.getTable()%> (<%=insertFieldStr.substring(1)%>)
             VALUES(<%=valuesStr.substring(1)%>) 
   </insert>
   
   
   
   <!-- ���¼�¼ -->       
   <update id="update<%=moduleName%>" >
           UPDATE <%=map.getTable()%> 
           <set><% for(String[] infos:mainFieldSet.values()){%>
                <%  out.print(infos[1]+"=#{"+entityName+"."+infos[2]+"},");   }%>
	   </set>
	       WHERE <%=primaryKey.getName()%> = <%="#{"+entityName%>.<%=primaryKey.getName()+"}"%>
   </update> 
 


   <!-- ɾ����¼ -->
   <delete id="delete<%=moduleName%>">
          DELETE FROM <%=map.getTable()%>
          WHERE <%=primaryKey.getName()%> in 
          <foreach collection="<%=primaryKey.getName()+"s"%>" item="<%=primaryKey.getName()%>" open="("  separator="," close=")" >
                <%="#{"+primaryKey.getName()+"}"%>
          </foreach>
   </delete>
   
   
   <!-- �������Զ����������Ϣ -->
   
   
   
   

</mapper>


<%!
    public static class SqlXmlCreator{
	
	//��Ӧwhere����
	public static String appendWhereOptions(String name,String tableAlias,String sourceField,String dataType){
		if("string".equals(dataType.toLowerCase()))
			return "<if test=\""+name+"!=null\"> AND "+tableAlias+"."+sourceField+" like #{"+name+"}</if>";
		else if("long".equals(dataType.toLowerCase())||"int".equals(dataType.toLowerCase()))
			return "<if test=\""+name+">0\"> AND "+tableAlias+"."+sourceField+" = #{"+name+"}</if>";
		else if(dataType.toLowerCase().indexOf("date")>=0||dataType.toLowerCase().indexOf("time")>=0) //ʱ�������Ĵ���
			return "<if test=\""+name+"1!=null\"> AND "+tableAlias+"."+sourceField+" &gt; #{"+name+"1}</if><if test=\""+name+"2!=null\"> AND "+tableAlias+"."+sourceField+" &lt; #{"+name+"2}</if>";
		return "";		
	}
	
	public static String appendOrderOptions(String name,String tableAlias,String sourceField){
	    return "<when test=\"order.columnName=='"+name+"'\"> "+tableAlias+"."+sourceField+" ${order.type} </when>";
	}
	
	
	public static String wrapperMainQuerySql(String mainTable,String mainTableAlias,List<JoinItem> joinTables){
		String sql = mainTable+"  "+mainTableAlias;
		sql+=wrapperLeftJoinSql(mainTableAlias,joinTables);
		return sql.toString();
	}
	
	
	public static String wrapperLeftJoinSql(String tableAlias,List<JoinItem> joinTables){
		String sql ="";
		if(joinTables!=null)
		for(JoinItem join:joinTables){
			sql+= " LEFT JOIN "+join.getTable()+" "+join.getAlias()+" ON "+ tableAlias+"."+join.getPrimaryTableKey()+" = " +join.getAlias()+"."+join.getJoinTableKey();
		    sql+= wrapperLeftJoinSql(join.getAlias(),join.getJoinItems());
		}
		return sql.toString();
	}
	
	
}

%>