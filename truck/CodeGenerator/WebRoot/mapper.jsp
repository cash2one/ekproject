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
  
	//����Ǹñ��Ƿֱ�ľ͸��ڽӿڷ����м��϶�Ӧ�Ĳ���
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
 * @description <%=map.getDescription()%> Mapper ���־û��ӿ��ࣩ
 * @version <%=map.getVersion()%>
 * @author <%=map.getAuthor()%>  
 * @CreateTime <%=new Date().toString()%>
 *
 */
@Component("<%=StringHelper.toLowerCase(entityName)%>Mapper")
public interface <%=entityName%>Mapper extends MyBatisMapper {

	/**
	 * ����������ѯ��Ӧ�ļ�¼
	 * @param <%=map.getPrimaryKeyItem().getName()%>  ��¼��Ӧ������
	 * @return
	 */
	public <%=entityName%>Entry findOne(<%=areaDeal%>,@Param("<%=map.getPrimaryKeyItem().getName()%>")<%=map.getPrimaryKeyItem().getType()%> <%=map.getPrimaryKeyItem().getName()%>);
	
	
	 /**
	   * �б��ѯ
	   * @param startRow   ��ʼ��¼������
	   * @param pageSize   ����ÿҳ��ʾ�ļ�¼��
	   <%=map.isAreaDeal()?"* @param areaAbb    ������д���ֱ�ǰ׺����":"" %>
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
	   * @param orderList  //��������
	   * @return List<<%=entityName%>Entry>
	   */
	public List<<%=entityName%>Entry> qeury<%=entityName%>s(@Param("startRow")int startRow, @Param("pageSize")int pageSize,<%=areaDeal%>,
				<%=conParamsStr%>,@Param("orderList")List<OrderItem>orderList);


	/**
	 * �б�ļ�¼����ͳ��
	   <%=map.isAreaDeal()?"* @param areaAbb    ������д���ֱ�ǰ׺����":"" %>
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
	   * @return �б�ļ�¼��
	   */
	public int qeury<%=entityName%>sRecordCount(<%=areaDeal%>,<%=conParamsStr%>);
	
	
	/**
	 * ������¼
	 * @param <%=entityName%>
	 * @return
	 */
	public int insert<%=entityName%>(<%=entityName%>Entry <%=StringHelper.toLowerCase(entityName)%>);
	
	
	/**
	 * ���¼�¼ 
	 * @param <%=entityName%>
	 * @return
	 */
	public int update<%=entityName%>(<%=entityName%>Entry <%=StringHelper.toLowerCase(entityName)%>);
	
	
	/**
	 * ɾ����¼
	 * @param id
	 * @return
	 */
	public int delete<%=entityName%>(<%=areaDeal%>,@Param("id") long id);
	
	
	
	//���·��������룩���ֶ���ӵ�
	//************************************************************************************************************************
	
	
	
}
