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
	
	//如果是该表是分表的就该在接口方法(新增、更新、删除方法，查询方法默认要保留)中加上对应的参数
	String areaAbbParamStr = "@SearchParameter(name =\"areaAbb\")String areaAbb";
	String areaAbbDealComment="* @param areaAbb    地区缩写（分表前缀名:如 “CS”）";
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
 * @description <%=map.getDescription()%> 对应的（业务逻辑类）
 * @version <%=map.getVersion()%>
 * @author <%=map.getAuthor()%>  
 * @CreateTime <%=new Date().toString()%>
 *
 */
@Service("<%=StringHelper.toLowerCase(map.getName())%>")
public class <%=map.getName()%> extends BaseBusiness {
    
    //私有属性
	//*****************************************************************************************************************
	<% //输出主要的字段属性
       for(FieldItem field:mainFields){
%> 
         private <%out.print(field.getType()+"  "+field.getName()+"; //"+field.getDescript());}%>
    
    <% //输出从表的字段属性
       for(FieldItem field:subFields){%> 
          private <%out.print(field.getType()+"  "+field.getName()+"; //"+field.getDescript());}%>
          
	
      //Spring自动注入相应的数据访问层对象
	  //*****************************************************************************************************************
	 @Autowired
	 private <%=mapperObjName%> <%=StringHelper.fistChartLowerCase(mapperObjName)%>;
	 
	 //构造函数
	 //*****************************************************************************************************************
	
     /**
     * 默认构造函数
     */
	 public <%=map.getName()%>() {
	
	 }
	
 
    /**带参数（主表）的构造函数
     <%String conParamsStr="";
   for(FieldItem field:mainFields){%>
	   * @param <%out.print(field.getName()+"   //"+field.getDescript());
	       conParamsStr+=","+field.getType()+" "+field.getName();
	  }%>
 
	*/
	public <%=map.getName()%>(<%=conParamsStr%>) {
		   <% //输出主要的字段属性
	       for(FieldItem field:mainFields){%> 
	            this.<%out.print(field.getName()+"="+field.getName()+";");}%>
	}
   
 
	
	
	/**带参数（全部）的构造函数
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
		   <% //输出主要的字段属性
	       for(FieldItem field:mainFields){%> 
	            this.<%out.print(field.getName()+"="+field.getName()+";");}%>
	       <% //输出从表的字段属性
	       for(FieldItem field:subFields){%> 
	            this.<%out.print(field.getName()+"="+field.getName()+";");}%>
	}
	
	
	//属性对应的get 和 set 方法
	//*****************************************************************************************************************
	
    <% //生成对应的getting 和 setting 方法
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
    
     <% //生成从表的getting 方法
     for(FieldItem field:subFields){%>          
         * @return <%=field.getName()+" //"+field.getDescript()%>
         public <%=field.getType() %> get<%out.print(StringHelper.fistChartUpperCase(field.getName())+"(  ){ ");%>
	        return this.<%out.print(field.getName()+";");%>    
         }
    <%}%>
    
    
    //子类必须要实现父类的抽象方法（模块）。
	//******************************************************************************************************************************
	
	/**
	 * 设置业务逻辑名称
	 */
	@Override
	public String getBusinessName(){
	   // TODO Auto-generated method stub
		return "";
	}


	/**
	 * 设置业务逻辑功能标识 对应数据表的功能标识
	 */
	@Override
	public String getFunctionFlag() {
		// TODO Auto-generated method stub
		return "";
	}

	/**
	 * 设置获取模块名称
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
	
	
	
	//实现对应具体的业务功能
	//*****************************************************************************************************************
	
	
	/**
	 * 新增
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
	 * 修改
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
	 * 删除
	 */
	@Override
	protected void onDelete(String ids[]) {
		// TODO Auto-generated method stub
		<%=StringHelper.fistChartLowerCase(mapperObjName)%>.delete<%=map.getClazz()%>(<%=map.isAreaDeal()?"this.getAbb(),":""%>ids);
	}

	
	  /**
	   * 查询
	   * @param startRow   开始记录的行数
	   * @param pageSize   设置每页显示的记录数
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
	   * @param orderList  //控制排序
	   * @return List<<%=map.getName()%>>
	   */
	@SeacherFun(nameAlias="<%=entityName%>List")
	@Transactional
	public List<<%=map.getName()%>> qeury<%=entityName%>s(@SearchParameter(defaultValue = "1",name = "startRow")int startRow, @SearchParameter(defaultValue = "20",name = "pageSize")int pageSize,<%=areaAbbParamStr%>,
				<%=""%> <%=conParamsStr%>,
				@SearchParameter(name="orderList")List<OrderItem>orderList){
		   //实例化List对象		
		   List<<%=map.getName()%>> list = new ArrayList<<%=map.getName()%>>();
		   //查询结果实体
		   List<<%=entryObjName%>> entryList = <%=StringHelper.fistChartLowerCase(mapperObjName)%>.qeury<%=map.getClazz()%>s(startRow,pageSize,areaAbb,<%=tempStr%>,orderList);
	       if (entryList != null)
		   for (<%=entryObjName%> entry : entryList) {
				list.add(new <%=map.getName()%>(<%=funcParamsStr%>));
		   }
		return list;
	}
				
			
	 /**
     * 根据主键（<%=map.getPrimaryKeyItem().getName()%>）返回单条记录
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
	
	//自定义方法
	//*****************************************************************************************************************
	
	
	
}
