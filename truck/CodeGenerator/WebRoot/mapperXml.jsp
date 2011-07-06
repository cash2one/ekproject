<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@ page import="qtone.generator.*" %>
<%@ page import="qtone.generator.util.*" %>

<%
    String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String cfgPath = request.getRealPath("/")+"demo.xml";
	BusinessMap map = new BusinessMap(cfgPath);
	String entityName=map.getClazz();
	
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
%>



<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" 
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="qtone.xxt.dao.mapper.edu.StudentMapper">
   <cache />

  <!-- ����������ϵͳ�Զ����ɵ� -->

  <!-- ��ʾ�ļ�¼��Ӧ����-->
  <sql id="studentColumns" >
		    <%=allFieldStr%>
  </sql>
  
  
  <!-- �б��Ӧ�Ĳ�ѯ�������  -->
  <sql id="queryOptions">
       <where><% String items[] = null,insertFieldStr="",valuesStr="";
               for(String key:mainFieldSet.keySet()){
            	  items = mainFieldSet.get(key);%>
            	  <%out.print(XmlCreator.appendWhereOptions(key,items[0],items[1],items[3]));  
            	    insertFieldStr+=","+items[1];
 	        	    valuesStr+=",#{"+entityName+"."+items[2]+"}";
               }
               for(String key:subFieldSet.keySet()){
             	  items = subFieldSet.get(key);%>         
                  <%out.print(XmlCreator.appendWhereOptions(key,items[0],items[1],items[3]));}%> 
        </where> 
  </sql>


  <!-- �б��ѯ��Ӧ������ѡ��  -->
  <sql id="orderControl">
       <if test="orderList!=null">
		  <trim prefix="ORDER BY" prefixOverrides=",">
				<foreach collection="orderList" item="order" open=""  separator="," close="" >
				   <choose><%for(String key:mainFieldSet.keySet()){
				    	   items =  mainFieldSet.get(key);%> 
				       <%out.print(XmlCreator.appendOrderOptions(key,items[0],items[1]));}%>
				       <when test="order.columnName=='schoolId'"> sch.id ${order.type} </when>
				  </choose>
			 </foreach>
		 </trim>
	   </if>
  </sql>

 
  <!-- �б��ѯ��Ӧ�ı��ϵSQL  -->
  <sql id="querySqlMain">
			  ${areaAbb}_XJ_STUDENT stu 
			  LEFT JOIN ${areaAbb}_XJ_STU_CLASS sc  on stu.stu_sequence = sc.stu_sequence
			  LEFT JOIN XJ_CLASS clas  on sc.class_id = clas.id
			  LEFT JOIN XJ_SCHOOL sch  on sch.id = sc.school_id
			  <include refid="queryOptions"/>
  </sql>
  
 
   <!-- ����ID��ѯ��¼ -->
   <select id="findOne" resultType="qtone.xxt.dao.entity.edu.StudentEntry">
	     SELECT  <include refid="studentColumns"/>              
		    FROM <include refid="querySqlMain"/>
			where <%=primaryKey.getTableAlias()%>.<%=primaryKey.getName()%>=<%="#{"+primaryKey.getName()+"}"%>
   </select>


   <!-- ���ؼ�¼��������� -->
   <select id="qeuryStudentsRecordCount" resultType="int">
        SELECT count(*) num  FROM  <include refid="querySqlMain"/> 
   </select>
   
   
    <!-- ��ҳ��ѯ��Ӧ�ļ�¼ -->
   <select id="qeuryStudents" resultType="qtone.xxt.dao.entity.edu.StudentEntry">
      SELECT * FROM (  
	        SELECT A.*,ROWNUM RN FROM (
	               SELECT <include refid="studentColumns"/>   
		           FROM <include refid="querySqlMain"/>
		           <include refid="orderControl"/>
		    ) A WHERE ROWNUM &lt;=<%="${startRow}+${pageSize}"%> ) 
		 WHERE RN &gt;=<%="#{startRow}"%>
   </select>


   <!-- ������¼ -->
   <insert id="insertStudent" useGeneratedKeys="false" keyProperty="id">
       <selectKey resultType="long" order="AFTER" keyProperty="<%=entityName%>.<%=primaryKey.getName()%>">
	       SELECT <%=map.getSequence()%>.currval FROM  DUAL
	   </selectKey>
             INSERT INTO <%=map.getTable()%> (<%=insertFieldStr.substring(1)%>)
             VALUES(<%=valuesStr.substring(1)%>) 
   </insert>
   
   
   <!-- ���¼�¼ -->       
   <update id="updateStudent">
           UPDATE <%=map.getTable()%> 
           <SET><% for(String[] infos:mainFieldSet.values()){%>
                <%out.print(infos[1]+"=#{"+entityName+"."+infos[2]+"},");}%>
	       </SET>
	       WHERE <%=primaryKey.getName()%> = <%="#{"+entityName%>.<%=primaryKey.getName()+"}"%>
   </update> 
 
 
   <!-- ɾ����¼ -->
   <delete id="deleteStudent">
          DELETE FROM <%=map.getTable()%>
          WHERE <%=primaryKey.getName()%> in 
          <foreach collection="<%=primaryKey.getName()+"s"%>" item="<%=primaryKey.getName()%>" open="("  separator="," close=")" >
                <%="#{"+primaryKey.getName()+"}"%>
          </foreach>
   </delete>
   
   
   <!-- �������Զ����������Ϣ -->
   
         
         
</mapper>


<%!

    public static class XmlCreator{
	
	
	public static String appendWhereOptions(String name,String tableAlias,String sourceField,String dataType){
		if("string".equals(dataType.toLowerCase()))
			return "<if test=\""+name+"!=null\"> AND "+tableAlias+"."+sourceField+" like #{"+name+"}</if>";
		else if("long".equals(dataType.toLowerCase())||"int".equals(dataType.toLowerCase()))
			return "<if test=\""+name+">0\"> AND "+tableAlias+"."+sourceField+" = #{"+name+"}</if>";
		return "";		
	}
	
	public static String appendOrderOptions(String name,String tableAlias,String sourceField){
	    return "<when test=\"order.columnName=='"+name+"'\"> "+tableAlias+"."+sourceField+" ${order.type} </when>";
	}
	
}

%>