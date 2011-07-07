<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@ page import="qtone.generator.*" %>
<%@ page import="qtone.generator.util.*" %>
<%
    String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String cfgPath = request.getRealPath("/")+"templates/"+request.getParameter("cfg");
	BusinessMap map = new BusinessMap(cfgPath);
	String entityName=map.getClazz();
	
	entityName=StringHelper.fistChartUpperCase(entityName);
	String basePackageName = "qtone.xxt.";
	String packageName = basePackageName+map.getDaoNamespace()+"."+map.getMapperNamespace()+"."+map.getNamespace();
  
	
	//����Ǹñ��Ƿֱ�ľ͸��ڽӿڷ���(���������¡�ɾ����������ѯ����Ĭ��Ҫ����)�м��϶�Ӧ�Ĳ���
	String areaAbbParamStr = "@Param(\"areaAbb\")String areaAbb";
	String areaAbbDealComment="* @param areaAbb    ������д���ֱ�ǰ׺��:�� ��CS����";
	String isAppendAreaDeal=map.isAreaDeal()?areaAbbParamStr+",":"";
	String appendAreaDealcomment=map.isAreaDeal()?areaAbbDealComment:"";
	
	
	List<FieldItem> mainFields = map.getMainFields();
    List<FieldItem> subFields = map.getJoinFields();
    
%>
package <%=packageName%>;

import java.util.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import <%=basePackageName+map.getDaoNamespace()+"."+map.getEntityNamespace()+"."+map.getNamespace()+"."+map.getClazz()%>Entry;
import <%=basePackageName+map.getDaoNamespace()+"."+map.getMapperNamespace()%>.MyBatisMapper;
import <%=basePackageName+map.getDaoNamespace()+"."+map.getMapperNamespace()%>.OrderItem;

/**
 *
 * @description <%=map.getDescription()%> ��Ӧ��Mapper���־û��ӿ��ࣩ
 * @version <%=map.getVersion()%>
 * @author <%=map.getAuthor()%>  
 * @CreateTime <%=new Date().toString()%>
 *
 */
@Component("<%=StringHelper.toLowerCase(entityName)%>Mapper")
public interface <%=entityName%>Mapper extends MyBatisMapper {

    
        //�Զ����ɵķ���
	    //************************************************************************************************************************

	/**
	 * ����������ѯ��Ӧ�ļ�¼
	 <%=areaAbbDealComment%>
	 * @param <%=map.getPrimaryKeyItem().getName()%>  ��¼��Ӧ������
	 * @return <%=entityName%>Entry
	 */
	public <%=entityName%>Entry findOne(<%=areaAbbParamStr%>,@Param("<%=map.getPrimaryKeyItem().getName()%>")<%=map.getPrimaryKeyItem().getType()%> <%=map.getPrimaryKeyItem().getName()%>);
	
	
	 /**
	   * �б��ѯ
	   * @param startRow   ��ʼ��¼������
	   * @param pageSize   ����ÿҳ��ʾ�ļ�¼��
	   <%=areaAbbDealComment%>
     <%String conParamsStr="";
     for(FieldItem field:mainFields){ if(field.getName().equals(map.getPrimaryKeyItem().getName())) continue;
          if(field.getType().toLowerCase().indexOf("date")>=0||field.getType().toLowerCase().indexOf("time")>=0){
	    	 conParamsStr+=",@Param(\""+field.getName()+"1\")"+field.getType()+" "+field.getName()+"1"+",@Param(\""+field.getName()+"2\")"+field.getType()+" "+field.getName()+"2";%>
	   * @param <% out.println(field.getName()+"1   //"+field.getDescript()+" ��ʼʱ��");out.print("           * @param "+field.getName()+"2   //"+field.getDescript()+" ����ʱ��");
	      }else{%>
	   * @param <%  out.print(field.getName()+"   //"+field.getDescript());  
	       	 conParamsStr+=",@Param(\""+field.getName()+"\")"+field.getType()+" "+field.getName();
	      }
	  }
     for(FieldItem field:subFields){
	     if(field.getType().toLowerCase().indexOf("date")>=0||field.getType().toLowerCase().indexOf("time")>=0){
	    	  conParamsStr+=",@Param(\""+field.getName()+"1\")"+field.getType()+" "+field.getName()+"1"+",@Param(\""+field.getName()+"2\")"+field.getType()+" "+field.getName()+"2";%>
	   * @param <% out.println(field.getName()+"1   //"+field.getDescript()+" ��ʼʱ��");out.print("           * @param "+field.getName()+"2   //"+field.getDescript()+" ����ʱ��");
	        }else{%>
	   * @param <%  out.print(field.getName()+"   //"+field.getDescript());  
	          conParamsStr+=",@Param(\""+field.getName()+"\")"+field.getType()+" "+field.getName();
	       }
	     }
	     conParamsStr = conParamsStr.substring(1);%>
	   * @param orderList  //��������
	   * @return List<<%=entityName%>Entry>
	   */
	public List<<%=entityName%>Entry> qeury<%=entityName%>s(@Param("startRow")int startRow, @Param("pageSize")int pageSize,<%=areaAbbParamStr%>,
				<%=conParamsStr%>,@Param("orderList")List<OrderItem>orderList);


	/**
	 * �б�ļ�¼����ͳ��
       <%=appendAreaDealcomment%>
<%conParamsStr="";
for(FieldItem field:mainFields){ if(field.getName().equals(map.getPrimaryKeyItem().getName())) continue;
    if(field.getType().toLowerCase().indexOf("date")>=0||field.getType().toLowerCase().indexOf("time")>=0){
  	 conParamsStr+=",@Param(\""+field.getName()+"1\")"+field.getType()+" "+field.getName()+"1"+",@Param(\""+field.getName()+"2\")"+field.getType()+" "+field.getName()+"2";%>
 	 * @param <% out.println(field.getName()+"1   //"+field.getDescript()+" ��ʼʱ��");out.print("         * @param "+field.getName()+"2   //"+field.getDescript()+" ����ʱ��");
    }else{%>
 	 * @param <%  out.print(field.getName()+"   //"+field.getDescript());  
     	 conParamsStr+=",@Param(\""+field.getName()+"\")"+field.getType()+" "+field.getName();
    }
}
 for(FieldItem field:subFields){
   if(field.getType().toLowerCase().indexOf("date")>=0||field.getType().toLowerCase().indexOf("time")>=0){
  	  conParamsStr+=",@Param(\""+field.getName()+"1\")"+field.getType()+" "+field.getName()+"1"+",@Param(\""+field.getName()+"2\")"+field.getType()+" "+field.getName()+"2";%>
	 * @param <% out.println(field.getName()+"1   //"+field.getDescript()+" ��ʼʱ��");out.print("         * @param "+field.getName()+"2   //"+field.getDescript()+" ����ʱ��");
      }else{%>
 	 * @param <%  out.print(field.getName()+"   //"+field.getDescript());  
        conParamsStr+=",@Param(\""+field.getName()+"\")"+field.getType()+" "+field.getName();
     }
    }conParamsStr = conParamsStr.substring(1);%>
	 * @return �б�ļ�¼��
	 */
	public int qeury<%=entityName%>sRecordCount(<%=areaAbbParamStr%>,<%=conParamsStr%>);
	
	
	/**
	 * ������¼
	 <%=appendAreaDealcomment%>
	 * @param <%=StringHelper.fistChartLowerCase(entityName)%>
	 * @return
	 */
	public int insert<%=entityName%>(<%=isAppendAreaDeal%> <%=entityName%>Entry <%=StringHelper.toLowerCase(entityName)%>);
	
	
	/**
	 * ���¼�¼ 
	 <%=appendAreaDealcomment%>
	 * @param <%=StringHelper.fistChartLowerCase(entityName)%>
	 * @return
	 */
	public int update<%=entityName%>(<%=isAppendAreaDeal%> <%=entityName%>Entry <%=StringHelper.toLowerCase(entityName)%>);
	
	
	/**
	 * ɾ����¼
	 <%=appendAreaDealcomment%>
	 * @param <%=map.getPrimaryKeyItem().getName()%>s  ��¼��Ӧ������
	 * @return
	 */
	public int delete<%=entityName%>(<%=isAppendAreaDeal%> @Param("<%=map.getPrimaryKeyItem().getName()%>s") String[] <%=map.getPrimaryKeyItem().getName()%>s);
	
	
	
	//�Զ��巽��
	//************************************************************************************************************************
	
	
	
}
