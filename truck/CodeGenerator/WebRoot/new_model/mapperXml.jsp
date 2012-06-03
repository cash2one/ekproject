<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="qtone.generator.*" %>
<%@ page import="qtone.generator.util.*" %>
<%
    String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String cfgPath = BaseCfg.CFG_PATH+"/templates/"+request.getParameter("cfg");
	
	BusinessMap map = new BusinessMap(cfgPath);
	String entityName=map.getClazz();
	
	String basePackageName = BaseCfg.basePackageName;
	String packageName = basePackageName+map.getDaoNamespace()+"."+map.getEntityNamespace()+"."+map.getNamespace()+".";
	String mapperName=basePackageName+map.getDaoNamespace()+"."+map.getNamespace()+"."+StringHelper.fistChartUpperCase(entityName+"Dao");
	String entryName = packageName+StringHelper.fistChartUpperCase(entityName+"Entity");
	String isDefineEntityName = !map.isAreaDeal()?("resultType=\""+entryName)+"\"":"";
	
	String moduleName=StringHelper.fistChartUpperCase(entityName);
	
	entityName=StringHelper.fistChartLowerCase(entityName);
	
	//主表属性
	List<FieldItem> mainFields = map.getMainFields();
    //从表属性
	List<FieldItem> subFields = map.getJoinFields();
    
    FieldItem primaryKey = map.getPrimaryKeyItem();
    
    String allFieldStr =""; 
    String keyCloumnValue = map.getPrimaryKeyItem()!=null?"#{"+entityName+"."+map.getPrimaryKeyItem().getName()+"}":"";
    
	Map<String,String[]> mainFieldSet = new HashMap<String,String[]>();
	for(FieldItem item:mainFields){

		//表别名信息、表字段、实体属性名、数据类型、是否只读,数据库是否可以为空
		if(map.getPrimaryKeyItem()==null||item.getName()!=map.getPrimaryKeyItem().getName()) 
		    mainFieldSet.put(item.getName(),new String[]{StringHelper.toLowerCase(item.getTableAlias()),StringHelper.toLowerCase(item.getSourceField()),StringHelper.fistChartLowerCase(item.getName()),item.getType(),item.getIsReadonly()?"true":"false",item.getAllowedNull()});
		 
		allFieldStr+=","+(StringHelper.toLowerCase(item.getTableAlias())+"."+StringHelper.toLowerCase(item.getSourceField())+" as "+StringHelper.fistChartLowerCase(item.getName()));
	}
	
	Map<String,String[]> subFieldSet = new HashMap<String,String[]>();
	for(FieldItem item:subFields){
		//表别名信息、表字段、实体属性名、数据类型、是否只读 
		subFieldSet.put(item.getName(),new String[]{StringHelper.toLowerCase(item.getTableAlias()),StringHelper.toLowerCase(item.getSourceField()),StringHelper.fistChartLowerCase(item.getName()),item.getType(),item.getIsReadonly()?"true":"false",item.getAllowedNull()});
		allFieldStr+=","+(StringHelper.toLowerCase(item.getTableAlias())+"."+StringHelper.toLowerCase(item.getSourceField())+" as "+StringHelper.fistChartLowerCase(item.getName()));
	}
	allFieldStr=allFieldStr.substring(1);
	
	
	List<JoinItem> joins = map.getJoinTable();
	
 
	String temp="";
%>



<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" 
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="<%=mapperName%>">

  <!-- 以下配置是系统自动生成的 -->

  <!-- 显示的记录对应的列-->
  <sql id="columns" >
	  <![CDATA[  
	     <%=allFieldStr%>  ]]>
  </sql>
  
  
  <!-- 列表对应的查询条件组合  -->
  <sql id="queryOptions">
       <where><% String items[] = null,insertFieldStr="",valuesStr="";
               for(String key:mainFieldSet.keySet()){
            	  items = mainFieldSet.get(key);%>
            	  <%out.print(SqlXmlCreator.appendWhereOptions(key,items[0],items[1],items[3]));  
            	    insertFieldStr+=","+items[1];
 	        	     // valuesStr+=",#{"+entityName+"."+items[2]+"}";
            	    valuesStr+=",#{"+SqlXmlCreator.columnTran(items[2],items[3],items[5])+"}";
               }
               for(String key:subFieldSet.keySet()){
             	  items = subFieldSet.get(key);%>         
                  <%out.print(SqlXmlCreator.appendWhereOptions(key,items[0],items[1],items[3]));}%> 
        </where> 
  </sql>



  <!-- 列表查询对应的排序选项  -->
  <sql id="orderControl">
       <if test="orderList!=null">
		  <trim prefix="ORDER BY" prefixOverrides=",">
				<foreach collection="orderList" item="order" open=""  separator="," close="" >
				   <choose><%for(String key:mainFieldSet.keySet()){ //主表的排序条件
				    	   items =  mainFieldSet.get(key);%> 
				       <%out.print(SqlXmlCreator.appendOrderOptions(key,items[0],items[1]));} 
				       for(String key:subFieldSet.keySet()){  //从表的排序条件  
				           items =  subFieldSet.get(key);%>   
				       <%out.print(SqlXmlCreator.appendOrderOptions(key,items[0],items[1]));}%>
                       <%if(primaryKey!=null){ %>
				       <when test="order.columnName=='<%=primaryKey.getName()%>'"> <%=primaryKey.getTableAlias()+"."+primaryKey.getName()+"  ${order.type}"%>  </when> 
				       <%} %>
				  </choose>
			 </foreach>
		 </trim>
	   </if>
  </sql>

 
 
  <!-- 列表查询对应的表关系SQL  -->
  <sql id="querySqlMain">
	 <![CDATA[   
	      <% out.print(SqlXmlCreator.wrapperMainQuerySql(map.getTable(),map.getTableAlias(),map.getTopJoinTable())); %>  ]]>
  </sql>

  
   <% if(map.getPrimaryKeyItem()!=null){//有主键才可以有此方法 %>
   <!-- 根据ID查询记录 -->
   <select id="loadById" resultType="<%=entryName%>">
	     SELECT  <include refid="columns"/>              
		       FROM <include refid="querySqlMain"/>
			   where <%=primaryKey.getTableAlias()%>.<%=primaryKey.getSourceField()%>=<%="#{"+primaryKey.getName()+"}"%>
   </select>
   <%}//END IF%>


   <!-- 返回记录总数的语句 -->
   <select id="count" resultType="int">
         SELECT count(*) num   
			  FROM  <include refid="querySqlMain"/>
				    <include refid="queryOptions"/> 
   </select>
   
   
    <!-- 分页查询对应的记录 -->
   <select id="qeury" resultType="<%=entryName%>">
	      SELECT <include refid="columns"/>   
		      FROM <include refid="querySqlMain"/>
		      <include refid="queryOptions"/>
		      <include refid="orderControl"/>
   </select>


   <!-- 新增记录 -->
   <insert id="insert" useGeneratedKeys="false" >
       <% if(map.getPrimaryKeyItem()!=null&&map.getSequence()!=null&&!"".equals(map.getSequence())){//已设置序列到主键上 %>
       <selectKey resultType="long" order="AFTER" keyProperty="<%=primaryKey.getName()%>">
	       <![CDATA[    SELECT <%=map.getSequence()%>.currval FROM  DUAL    ]]> 
	   </selectKey> 
             INSERT INTO <%=map.getTable()%> (<%=insertFieldStr.substring(1)%>)
             VALUES(<%=valuesStr.substring(1)%>)  
       <%}else{//没设置序列%>   
             INSERT INTO <%=map.getTable()%> (<%=map.getPrimaryKeyItem()!=null?(map.getPrimaryKeyItem().getName()+","+insertFieldStr.substring(1)):insertFieldStr.substring(1)%>)
             VALUES(<%=map.getPrimaryKeyItem()!=null?(keyCloumnValue+","+valuesStr.substring(1)):valuesStr.substring(1)%>) 
       <%}//END ELSE%>     
   </insert>
   
   
   <% if(map.getPrimaryKeyItem()!=null){//有主键才可以有此方法 %>
   <!-- 更新记录 -->       
   <update id="update" >
           UPDATE <%=map.getTable()%> 
           <set>
            <![CDATA[ <% for(String[] infos:mainFieldSet.values()){%>
                <%  if("false".equals(infos[4])){ out.print(infos[1]+"=#{"+ SqlXmlCreator.columnTran(infos[2],infos[3],infos[5])+"},");  }}%>   ]]>
	   </set>
	       WHERE <%=primaryKey.getSourceField()%> = <%="#{"%><%=primaryKey.getName()+"}"%>
   </update> 
   <%}//END IF%>


   <% if(map.getPrimaryKeyItem()!=null){//有主键才可以有此方法 %>
   <!-- 删除记录 -->
   <delete id="delete">
          DELETE FROM <%=map.getTable()%>
          WHERE <%=primaryKey.getSourceField()%> in    
          <foreach collection="array" item="<%=primaryKey.getName()%>" open="("  separator="," close=")" >
                <%="#{"+primaryKey.getName()+"}"%>
          </foreach>
   </delete>
  <%}//END IF%>
   
   <!-- 以下是自定义的配置信息 -->
   
   
   
   

</mapper>


<%!
    public static class SqlXmlCreator{
	
	//答应where条件
	public static String appendWhereOptions(String name,String tableAlias,String sourceField,String dataType){
		if("string".equals(dataType.toLowerCase()))
			return "<if test=\""+name+"!=null\"> AND "+tableAlias+"."+sourceField+" like #{"+name+"}</if>";
		else if("long".equals(dataType.toLowerCase())||"int".equals(dataType.toLowerCase()))
			return "<if test=\""+name+">=0\"> AND "+tableAlias+"."+sourceField+" = #{"+name+"}</if>";
		else if(dataType.toLowerCase().indexOf("date")>=0||dataType.toLowerCase().indexOf("time")>=0) //时间条件的处理
			return "<if test=\""+name+"1!=null\"> AND "+tableAlias+"."+sourceField+" &gt; #{"+name+"1}</if><if test=\""+name+"2!=null\"> AND "+tableAlias+"."+sourceField+" &lt; #{"+name+"2}</if>";
		return "";		
	}
	
	public static String appendOrderOptions(String name,String tableAlias,String sourceField){
	    return "<when test=\"order.columnName=='"+name+"'\"> "+tableAlias+"."+sourceField+" ${order.type} </when>";
	}
	
	//SQL 组装
	public static String wrapperMainQuerySql(String mainTable,String mainTableAlias,List<JoinItem> joinTables){
		String sql = mainTable+"  "+mainTableAlias;
		sql+=wrapperLeftJoinSql(mainTableAlias,joinTables);
		return sql.toString();
	}
	
	
	//进行表连接
	public static String wrapperLeftJoinSql(String tableAlias,List<JoinItem> joinTables){
		String sql ="";
		String joinType="";
		if(joinTables!=null)
		for(JoinItem join:joinTables){
			joinType=join.getJoinType().equals("left")?" LEFT JOIN ":(join.getJoinType().equals("rigth")?" RIGHT JOIN ":" JOIN ");
			sql+= joinType + join.getTable()+" "+join.getAlias()+" ON "+ tableAlias+"."+join.getPrimaryTableKey()+" = " +join.getAlias()+"."+join.getJoinTableKey();
		    sql+= wrapperLeftJoinSql(join.getAlias(),join.getJoinItems());
		}
		return sql.toString();
	}

	
	//表字段 是否允许为空的设置
	public static String columnTran(String columnName,String javaTypeName,String isAllowedNull){
		if("false".equals(isAllowedNull))
			return columnName; 
        String cfg ="";
        if(javaTypeName.toLowerCase().equals("string"))
        	return columnName+",jdbcType=VARCHAR";
        else if(javaTypeName.toLowerCase().equals("date"))
        	return columnName+",jdbcType=DATE";
        else if(javaTypeName.toLowerCase().equals("long")||javaTypeName.toLowerCase().equals("int"))
            return columnName+",jdbcType=NUMERIC";
        else 
        	return columnName;
	}
	
	
}

%>