<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@ page import="qtone.generator.*" %>
<%@ page import="qtone.generator.util.*" %>
<%
    String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String cfgPath = request.getRealPath("/")+"demo.xml";
	BusinessMap map = new BusinessMap(cfgPath);
	String entityName=map.getClazz();
	
	entityName=StringHelper.fistChartUpperCase(entityName);
	String basePackageName = "qtone.xxt.";
	String packageName = basePackageName+map.getDaoNamespace()+"."+map.getMapperNamespace()+"."+map.getNamespace();
  
	//如果是该表是分表的就该在接口方法中加上对应的参数
	String areaDeal=map.isAreaDeal()?"@Param(\"areaAbb\")String areaAbb":"";
	
	List<FieldItem> mainFields = map.getMainFields();
    List<FieldItem> subFields = map.getJoinFields();
	
%>
package <%=packageName%>;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import <%=basePackageName+map.getDaoNamespace()+"."+map.getEntityNamespace()+"."+map.getNamespace()+"."+map.getClazz()%>Entry;
import <%=basePackageName+map.getDaoNamespace()+"."+map.getMapperNamespace()%>.MyBatisMapper;
import <%=basePackageName+map.getDaoNamespace()+"."+map.getMapperNamespace()%>.OrderItem;

/**
 *
 * @description <%=map.getDescription()%> Mapper （持久化接口类）
 * @version <%=map.getVersion()%>
 * @author <%=map.getAuthor()%>  
 * @CreateTime <%=new Date().toString()%>
 *
 */
@Component("<%=StringHelper.toLowerCase(entityName)%>Mapper")
public interface <%=entityName%>Mapper extends MyBatisMapper {

	/**
	 * 根据主键查询对应的记录
	 * @param <%=map.getPrimaryKeyItem().getName()%>  记录对应的主键
	 * @return
	 */
	public <%=entityName%>Entry findOne(<%=areaDeal%>,@Param("<%=map.getPrimaryKeyItem().getName()%>")<%=map.getPrimaryKeyItem().getType()%> <%=map.getPrimaryKeyItem().getName()%>);
	
	
	 /**
	   * 列表查询
	   * @param startRow   开始记录的行数
	   * @param pageSize   设置每页显示的记录数
	   <%=map.isAreaDeal()?"* @param areaAbb    地区缩写（分表前缀名）":"" %>
     <%String conParamsStr="";
   for(FieldItem field:mainFields){%>
	   * @param <%out.print(field.getName()+"   //"+field.getDescript());
	       conParamsStr+=",@Param(\""+field.getName()+"\")"+field.getType()+" "+field.getName();
	  }
   for(FieldItem field:subFields){%>
	   * @param <%out.print(field.getName()+"   //"+field.getDescript());
	       conParamsStr+=",@Param(\""+field.getName()+"\")"+field.getType()+" "+field.getName();
	     }
	     conParamsStr = conParamsStr.substring(1);%>
	   * @param orderList  //控制排序
	   * @return List<<%=entityName%>Entry>
	   */
	public List<<%=entityName%>Entry> qeury<%=entityName%>s(@Param("startRow")int startRow, @Param("pageSize")int pageSize,<%=areaDeal%>,
				<%=conParamsStr%>,@Param("orderList")List<OrderItem>orderList);


	/**
	 * 列表的记录总数统计
	   <%=map.isAreaDeal()?"* @param areaAbb    地区缩写（分表前缀名）":"" %>
     <%conParamsStr="";
   for(FieldItem field:mainFields){%>
	   * @param <%out.print(field.getName()+"   //"+field.getDescript());
	       conParamsStr+=",@Param(\""+field.getName()+"\")"+field.getType()+" "+field.getName();
	  }
   for(FieldItem field:subFields){%>
	   * @param <%out.print(field.getName()+"   //"+field.getDescript());
	       conParamsStr+=",@Param(\""+field.getName()+"\")"+field.getType()+" "+field.getName();
	     }
	     conParamsStr = conParamsStr.substring(1);%>
	   * @return 列表的记录数
	   */
	public int qeury<%=entityName%>sRecordCount(<%=areaDeal%>,<%=conParamsStr%>);
	
	
	/**
	 * 新增记录
	 * @param <%=entityName%>
	 * @return
	 */
	public int insert<%=entityName%>(<%=entityName%>Entry <%=StringHelper.toLowerCase(entityName)%>);
	
	
	/**
	 * 更新记录 
	 * @param <%=entityName%>
	 * @return
	 */
	public int update<%=entityName%>(<%=entityName%>Entry <%=StringHelper.toLowerCase(entityName)%>);
	
	
	/**
	 * 删除记录
	 * @param id
	 * @return
	 */
	public int delete<%=entityName%>(<%=areaDeal%>,@Param("id") long id);
	
	
	
	//以下方法（代码）是手动添加的
	//************************************************************************************************************************
	
	
	
}
