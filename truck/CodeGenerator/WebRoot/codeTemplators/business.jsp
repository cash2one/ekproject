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
	String businessPackageName = basePackageName+map.getBusinessNamespace()+"."+map.getNamespace();
	String entryPackageName = basePackageName+map.getDaoNamespace()+"."+map.getEntityNamespace()+"."+map.getNamespace();
	String mapperPackageName = basePackageName+map.getDaoNamespace()+"."+map.getMapperNamespace()+"."+map.getNamespace();
	
	
	String mapperObjName = StringHelper.fistChartUpperCase(entityName)+"Mapper";
	String entryObjName =  StringHelper.fistChartUpperCase(entityName)+"Entry";
	
	//����Ǹñ��Ƿֱ�ľ͸��ڽӿڷ���(���������¡�ɾ����������ѯ����Ĭ��Ҫ����)�м��϶�Ӧ�Ĳ���
	String areaAbbParamStr = "@SearchParameter(name =\"areaAbb\")String areaAbb";
	String areaAbbDealComment="* @param areaAbb    ������д���ֱ�ǰ׺��:�� ��CS����";
	String isAppendAreaDeal=map.isAreaDeal()?areaAbbParamStr+",":"";
	String appendAreaDealcomment=map.isAreaDeal()?areaAbbDealComment:"";
	
	
	List<FieldItem> mainFields = map.getMainFields();
    List<FieldItem> subFields = map.getJoinFields();
    
    String tempStr="";
%>
package <%=businessPackageName%>;
 
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import <%=basePackageName+map.getBusinessNamespace()%>.annotation.SeacherFun;
import <%=basePackageName+map.getBusinessNamespace()%>.annotation.SearchParameter;
import <%=basePackageName+map.getBusinessNamespace()%>.base.BaseBusiness;
import <%=entryPackageName+"."+map.getClazz()%>Entry;
import <%=basePackageName+map.getDaoNamespace()+"."+map.getMapperNamespace()%>.OrderItem;
import <%=mapperPackageName+"."+map.getClazz()%>Mapper;


/**
 *
 * @description <%=map.getDescription()%> ��Ӧ�ģ�ҵ���߼��ࣩ
 * @version <%=map.getVersion()%>
 * @author <%=map.getAuthor()%>  
 * @CreateTime <%=new Date().toString()%>
 *
 */
@Service("<%=StringHelper.toLowerCase(map.getName())%>")
public class <%=map.getName()%> extends BaseBusiness {
    
    //˽������
	//*****************************************************************************************************************
	<% //�����Ҫ���ֶ�����
       for(FieldItem field:mainFields){
%> 
         private <%out.print(field.getType()+"  "+field.getName()+"; //"+field.getDescript());}%>
    
    <% //����ӱ���ֶ�����
       for(FieldItem field:subFields){%> 
          private <%out.print(field.getType()+"  "+field.getName()+"; //"+field.getDescript());}%>
          
	
      //Spring�Զ�ע����Ӧ�����ݷ��ʲ����
	  //*****************************************************************************************************************
	 @Autowired
	 private <%=mapperObjName%> <%=StringHelper.fistChartLowerCase(mapperObjName)%>;
	 
	 //���캯��
	 //*****************************************************************************************************************
	
     /**
     * Ĭ�Ϲ��캯��
     */
	 public <%=map.getName()%>() {
	
	 }
	
 
    /**�������������Ĺ��캯��
     <%String conParamsStr="";
   for(FieldItem field:mainFields){%>
	   * @param <%out.print(field.getName()+"   //"+field.getDescript());
	       conParamsStr+=","+field.getType()+" "+field.getName();
	  }%>
 
	*/
	public <%=map.getName()%>(<%=conParamsStr%>) {
		   <% //�����Ҫ���ֶ�����
	       for(FieldItem field:mainFields){%> 
	            this.<%out.print(field.getName()+"="+field.getName()+";");}%>
	}
   
 
	
	
	/**��������ȫ�����Ĺ��캯��
     <%conParamsStr="";
   for(FieldItem field:mainFields){%>
	   * @param <%out.print(field.getName()+"   //"+field.getDescript());
	       conParamsStr+=","+field.getType()+" "+field.getName();
	  }
   for(FieldItem field:subFields){%>
	   * @param <%out.print(field.getName()+"   //"+field.getDescript());
	       conParamsStr+=","+field.getType()+" "+field.getName();
	     }
	     conParamsStr = conParamsStr.substring(1);%>
	*/
	public <%=map.getName()%>(<%=conParamsStr%>) {
		   <% //�����Ҫ���ֶ�����
	       for(FieldItem field:mainFields){%> 
	            this.<%out.print(field.getName()+"="+field.getName()+";");}%>
	       <% //����ӱ���ֶ�����
	       for(FieldItem field:subFields){%> 
	            this.<%out.print(field.getName()+"="+field.getName()+";");}%>
	}
	
	
	//���Զ�Ӧ��get �� set ����
	//*****************************************************************************************************************
	
    <% //���ɶ�Ӧ��getting �� setting ����
     for(FieldItem field:mainFields){%> 
         * @param <%=field.getName()+" //"+field.getDescript()%>
         public void set<%out.print(StringHelper.fistChartUpperCase(field.getName())+"("+field.getType()+" "+field.getName()+"){");%>
	        this.<%out.print(field.getName()+"="+field.getName()+";");%>    
         }
         
         * @return <%=field.getName()+" //"+field.getDescript()%>
         public <%=field.getType() %> get<%out.print(StringHelper.fistChartUpperCase(field.getName())+"(  ){ ");%>
	        return this.<%out.print(field.getName()+";");%>    
         }
    <%}%>
    
     <% //���ɴӱ��getting ����
     for(FieldItem field:subFields){%>          
         * @return <%=field.getName()+" //"+field.getDescript()%>
         public <%=field.getType() %> get<%out.print(StringHelper.fistChartUpperCase(field.getName())+"(  ){ ");%>
	        return this.<%out.print(field.getName()+";");%>    
         }
    <%}%>
    
    
    //�������Ҫʵ�ָ���ĳ��󷽷���ģ�飩��
	//******************************************************************************************************************************
	
	/**
	 * ����ҵ���߼�����
	 */
	@Override
	public String getBusinessName(){
	   // TODO Auto-generated method stub
		return "";
	}


	/**
	 * ����ҵ���߼����ܱ�ʶ ��Ӧ���ݱ�Ĺ��ܱ�ʶ
	 */
	@Override
	public String getFunctionFlag() {
		// TODO Auto-generated method stub
		return "";
	}

	/**
	 * ���û�ȡģ������
	 */
	@Override
	public String getModel() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public void filterContent() throws Exception {
		// TODO Auto-generated method stub
	}
	
	
	
	//ʵ�ֶ�Ӧ�����ҵ����
	//*****************************************************************************************************************
	
	
	/**
	 * ����
	 */
	@Override
	protected void onAdd() {
		// TODO Auto-generated method stub
	       <%=entryObjName%> <%=StringHelper.fistChartLowerCase(entryObjName)%> = new <%=entryObjName%>();
		   <%for(FieldItem field:mainFields){ tempStr=StringHelper.fistChartUpperCase(field.getName());%> 
	       <%out.print(StringHelper.fistChartLowerCase(entryObjName)+".set"+tempStr+"(this.get"+tempStr+"());");}%>
	    
	       <%=StringHelper.fistChartLowerCase(mapperObjName)%>.insert<%=map.getClazz()%>(<%=map.isAreaDeal()?"this.getAbb(),":""%><%=StringHelper.fistChartLowerCase(entryObjName)%>);
	}

	
	
	/**
	 * �޸�
	 */
	@Override
	protected void onModify() {
		// TODO Auto-generated method stub
		<%=entryObjName%> <%=StringHelper.fistChartLowerCase(entryObjName)%> = new <%=entryObjName%>();
	    <%for(FieldItem field:mainFields){ tempStr=StringHelper.fistChartUpperCase(field.getName());%> 
	        <%out.print(StringHelper.fistChartLowerCase(entryObjName)+".set"+tempStr+"(this.get"+tempStr+"());");}%>
	        
		<%=StringHelper.fistChartLowerCase(mapperObjName)%>.update<%=map.getClazz()%>(<%=map.isAreaDeal()?"this.getAbb(),":""%><%=StringHelper.fistChartLowerCase(entryObjName)%>);
	}

	
	/**
	 * ɾ��
	 */
	@Override
	protected void onDelete(String ids[]) {
		// TODO Auto-generated method stub
		<%=StringHelper.fistChartLowerCase(mapperObjName)%>.delete<%=map.getClazz()%>(<%=map.isAreaDeal()?"this.getAbb(),":""%>ids);
	}

	
	  /**
	   * ��ѯ
	   * @param startRow   ��ʼ��¼������
	   * @param pageSize   ����ÿҳ��ʾ�ļ�¼��
	   <%=areaAbbDealComment%>
     <%conParamsStr="";tempStr="";String funcParamsStr="";
   for(FieldItem field:mainFields){ funcParamsStr+=",entry.get"+StringHelper.fistChartUpperCase(field.getName())+"()";if(field.getName().equals(map.getPrimaryKeyItem().getName())) continue; %>
	   * @param <%out.print(field.getName()+"   //"+field.getDescript());
	       tempStr+=","+field.getName();
	       conParamsStr+=",@SearchParameter(name =\""+field.getName()+"\")"+field.getType()+" "+field.getName();
	  }
   for(FieldItem field:subFields){ funcParamsStr+=",entry.get"+StringHelper.fistChartUpperCase(field.getName())+"()";%>
	   * @param <%out.print(field.getName()+"   //"+field.getDescript());
	       conParamsStr+=",@SearchParameter(name =\""+field.getName()+"\")"+field.getType()+" "+field.getName();
	       tempStr+=","+field.getName();
	     }
         funcParamsStr=funcParamsStr.substring(1);
         tempStr=tempStr.substring(1);;
	     conParamsStr = conParamsStr.substring(1);%>
	   * @param orderList  //��������
	   * @return List<<%=map.getName()%>>
	   */
	@SeacherFun(nameAlias="<%=entityName%>List")
	@Transactional
	public List<<%=map.getName()%>> qeury<%=entityName%>s(@SearchParameter(defaultValue = "1",name = "startRow")int startRow, @SearchParameter(defaultValue = "20",name = "pageSize")int pageSize,<%=areaAbbParamStr%>,
				<%=""%> <%=conParamsStr%>,
				@SearchParameter(name="orderList")List<OrderItem>orderList){
		   //ʵ����List����		
		   List<<%=map.getName()%>> list = new ArrayList<<%=map.getName()%>>();
		   //��ѯ���ʵ��
		   List<<%=entryObjName%>> entryList = <%=StringHelper.fistChartLowerCase(mapperObjName)%>.qeury<%=map.getClazz()%>s(startRow,pageSize,areaAbb,<%=tempStr%>,orderList);
	       if (entryList != null)
		   for (<%=entryObjName%> entry : entryList) {
				list.add(new <%=map.getName()%>(<%=funcParamsStr%>));
		   }
		return list;
	}
				
			
	 /**
     * ����������<%=map.getPrimaryKeyItem().getName()%>�����ص�����¼
     * @param <%=map.getPrimaryKeyItem().getName()%>
     * @return 
     */
	@Transactional
	public <%=map.getName()%> findOne(<%=map.getPrimaryKeyItem().getType()+" "+map.getPrimaryKeyItem().getName()%>){
		 <%=map.getName()%>  valueObj = null;
		 <%=entryObjName%> entry = <%=StringHelper.fistChartLowerCase(mapperObjName)%>.findOne(<%=map.isAreaDeal()?"this.getAbb(),":""%><%=StringHelper.fistChartLowerCase(entryObjName)%>);
		if (entry != null){
			valueObj = new <%=map.getName()%>(<%=funcParamsStr%>);
		}
		return valueObj;
	}
	
	//�Զ��巽��
	//*****************************************************************************************************************
	
	
	
}
