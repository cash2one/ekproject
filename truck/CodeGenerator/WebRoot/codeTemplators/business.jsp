<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="qtone.generator.*" %>
<%@ page import="qtone.generator.util.*" %>
<%
    String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String cfgPath = BaseCfg.CFG_PATH+"/templates/"+request.getParameter("cfg");
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
	String areaAbbParamStr = "@SearchParameter(name =\""+BaseCfg.AREA_ABB+"\")String "+BaseCfg.AREA_ABB;
	String areaAbbDealComment="* @param "+BaseCfg.AREA_ABB+"    地区缩写（分表前缀名:如 “CS”）";
	String isAppendAreaDeal=map.isAreaDeal()?areaAbbParamStr+",":"";
	String appendAreaDealcomment=map.isAreaDeal()?areaAbbDealComment:"";
	
	
	List<FieldItem> mainFields = map.getMainFields();
    List<FieldItem> subFields = map.getJoinFields();
    
    String tempStr="";
%>
package <%=businessPackageName%>;
 
import java.util.*;
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
@Service("<%=StringHelper.fistChartLowerCase(map.getName())%>")
public class <%=map.getName()%> extends BaseBusiness {
    
    //私有属性
	//*****************************************************************************************************************
	<%for(FieldItem field:mainFields){//输出主要的字段属性%> 
         private <%out.print(field.getType()+"  "+field.getName()+"; //"+field.getDescript());}%>
    
    <% for(FieldItem field:subFields){ //输出从表的字段属性 %> 
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
	   * @param <%out.print(field.getName()+"   "+field.getDescript());
	       conParamsStr+=","+field.getType()+" "+field.getName();
	  }
        conParamsStr=conParamsStr.substring(1);
	  %>
	*/
	public <%=map.getName()%>(<%=conParamsStr%>) {
		   <%for(FieldItem field:mainFields){ //输出主要的字段属性%> 
	            this.<%out.print(field.getName()+"="+field.getName()+";");}%>
	}
   
 
	<% if(subFields!=null&&subFields.size()>0){ %>
	/**带参数（全部）的构造函数
     <%conParamsStr="";
   for(FieldItem field:mainFields){%>
	   * @param <%out.print(field.getName()+"   "+field.getDescript());
	       conParamsStr+=","+field.getType()+" "+field.getName();
	  }
   for(FieldItem field:subFields){%>
	   * @param <%out.print(field.getName()+"   "+field.getDescript());
	       conParamsStr+=","+field.getType()+" "+field.getName();
	     }
	     conParamsStr = conParamsStr.substring(1);%>
	*/
	public <%=map.getName()%>(<%=conParamsStr%>) {
		   <%for(FieldItem field:mainFields){ //输出主要的字段属性%> 
	            this.<%out.print(field.getName()+"="+field.getName()+";");}%>
	       <%for(FieldItem field:subFields){ //输出从表的字段属性%> 
	            this.<%out.print(field.getName()+"="+field.getName()+";");}%>
	}<%}//End IF%>
	
	
	//属性对应的get 和 set 方法
	//*****************************************************************************************************************
	
    <%for(FieldItem field:mainFields){ //生成对应的getting 和 setting 方法%> 
        /**
         * @param <%=field.getName()+" "+field.getDescript()%>
         */
         public void set<%out.print(StringHelper.fistChartUpperCase(field.getName())+"("+field.getType()+" "+field.getName()+"){");%>
	        this.<%out.print(field.getName()+"="+field.getName()+";");%>    
         }
        /**
         * @return <%=field.getName()+" "+field.getDescript()%>
         */
         public <%=field.getType() %> get<%out.print(StringHelper.fistChartUpperCase(field.getName())+"( ){ ");%>
	        return this.<%out.print(field.getName()+";");%>    
         }
    <%}%>
    
     <%for(FieldItem field:subFields){ //生成从表的getting 方法%>          
        /**
         * @param <%=field.getName()+" "+field.getDescript()%>
         */
         private void set<%out.print(StringHelper.fistChartUpperCase(field.getName())+"("+field.getType()+" "+field.getName()+"){");%>
	        this.<%out.print(field.getName()+"="+field.getName()+";");%>    
         }
         
        /**
          * @return <%=field.getName()+" "+field.getDescript()%>
         */
         public <%=field.getType() %> get<%out.print(StringHelper.fistChartUpperCase(field.getName())+"( ){ ");%>
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
	    
	       <%=StringHelper.fistChartLowerCase(mapperObjName)%>.insert<%=map.getClazz()%>(<%=map.isAreaDeal()?"this.getDaoAbb(),":""%><%=StringHelper.fistChartLowerCase(entryObjName)%>);
	       <%=StringHelper.fistChartLowerCase(entryObjName)%> = null;
	}

	
   <% if(map.getPrimaryKeyItem()!=null){//有主键才可以有此方法 %>
    /**
     * 根据主键（<%=map.getPrimaryKeyItem().getName()%>）返回单条记录
     * @param <%=map.getPrimaryKeyItem().getName()%>
     * @return 
     */
	@Transactional
	public void findOne(<%=map.getPrimaryKeyItem().getType()+" "+map.getPrimaryKeyItem().getName()%>){
		<%=entryObjName%> entry = <%=StringHelper.fistChartLowerCase(mapperObjName)%>.findOne(this.getDaoAbb(),<%=map.getPrimaryKeyItem().getName()%>);
		if (entry != null){<% //赋值
	     for(FieldItem field:mainFields){%>
	           this.set<%out.print(StringHelper.fistChartUpperCase(field.getName())+"(entry.get"+StringHelper.fistChartUpperCase(field.getName())+"());");
	     }
         for(FieldItem field:subFields){%>
                   this.set<%out.print(StringHelper.fistChartUpperCase(field.getName())+"(entry.get"+StringHelper.fistChartUpperCase(field.getName())+"());");}%>
		}
		entry = null;  
	}
	 <%}//END IF%>
	
	
	
	/**
	 * 修改
	 */
	@Override
	protected void onModify() {
		// TODO Auto-generated method stub
		<%=entryObjName%> <%=StringHelper.fistChartLowerCase(entryObjName)%> = new <%=entryObjName%>();
	    <%for(FieldItem field:mainFields){ tempStr=StringHelper.fistChartUpperCase(field.getName());%> 
	        <%out.print(StringHelper.fistChartLowerCase(entryObjName)+".set"+tempStr+"(this.get"+tempStr+"());");}%>
	        
		<%=StringHelper.fistChartLowerCase(mapperObjName)%>.update<%=map.getClazz()%>(<%=map.isAreaDeal()?"this.getDaoAbb(),":""%><%=StringHelper.fistChartLowerCase(entryObjName)%>);
	    <%=StringHelper.fistChartLowerCase(entryObjName)%> = null;
	}

	
	/**
	 * 删除
	 */
	@Override
	protected void onDelete(String ids[]) {
		// TODO Auto-generated method stub
		<%=StringHelper.fistChartLowerCase(mapperObjName)%>.delete<%=map.getClazz()%>(<%=map.isAreaDeal()?"this.getDaoAbb(),":""%>ids);
	}

	
	  /**
	   * 查询
	   * @param startRow   开始记录的行数
	   * @param pageSize   设置每页显示的记录数
     <%conParamsStr="";tempStr="";String funcParamsStr="";
   for(FieldItem field:mainFields){ funcParamsStr+=",entry.get"+StringHelper.fistChartUpperCase(field.getName())+"()";if(field.getName().equals(map.getPrimaryKeyItem()!=null?map.getPrimaryKeyItem().getName():null)) continue; %>
	   <%
	       if(field.getType().toLowerCase().indexOf("date")>=0||field.getType().toLowerCase().indexOf("time")>=0){
	    	  out.println("* @param   "+field.getName()+"1   "+field.getDescript()+" （大于或等于开始时间）");
	    	  out.print("           * @param   "+field.getName()+"2   "+field.getDescript()+" （小于或等于结束时间）");
		      tempStr+=","+field.getName()+"1,"+field.getName()+"2";
		      conParamsStr+=",@SearchParameter(name =\""+field.getName()+"1\")"+field.getType()+" "+field.getName()+"1,@SearchParameter(name =\""+field.getName()+"2\")"+field.getType()+" "+field.getName()+"2";
	       }else{
	          out.print("* @param   "+field.getName()+"   "+field.getDescript());
	          tempStr+=","+field.getName();
	          if("long".equals(field.getType().toLowerCase())||"int".equals(field.getType().toLowerCase()))
	        	conParamsStr+=",@SearchParameter(name =\""+field.getName()+"\", defaultValue=\"-1\" )"+field.getType()+" "+field.getName();
	        	  else
	            conParamsStr+=",@SearchParameter(name =\""+field.getName()+"\")"+field.getType()+" "+field.getName();}
	 
   }//end for
   for(FieldItem field:subFields){ funcParamsStr+=",entry.get"+StringHelper.fistChartUpperCase(field.getName())+"()";%>
	   <%
	      if(field.getType().toLowerCase().indexOf("date")>=0||field.getType().toLowerCase().indexOf("time")>=0){
	    	  out.println("* @param   "+field.getName()+"1   "+field.getDescript()+" （大于或等于开始时间）");
	    	  out.print("            * @param   "+field.getName()+"2   "+field.getDescript()+" （小于或等于结束时间）");
		      tempStr+=","+field.getName()+"1,"+field.getName()+"2";
		      conParamsStr+=",@SearchParameter(name =\""+field.getName()+"1\")"+field.getType()+" "+field.getName()+"1,@SearchParameter(name =\""+field.getName()+"2\")"+field.getType()+" "+field.getName()+"2";
	      }else{ 
	          out.print("* @param   "+field.getName()+"   "+field.getDescript());
	          if("long".equals(field.getType().toLowerCase())||"int".equals(field.getType().toLowerCase()))
		          conParamsStr+=",@SearchParameter(name =\""+field.getName()+"\", defaultValue=\"-1\" )"+field.getType()+" "+field.getName();
		      else
	              conParamsStr+=",@SearchParameter(name =\""+field.getName()+"\")"+field.getType()+" "+field.getName();
	          tempStr+=","+field.getName();}
	     }//end for
         funcParamsStr=funcParamsStr.substring(1);
         tempStr=tempStr.substring(1);;
	     conParamsStr = conParamsStr.substring(1);%>
	   * @param orderList  //控制排序
	   * @return List<<%=map.getName()%>>
	   */
	@SeacherFun(nameAlias="<%=map.getName()%>Seacher")
	@Transactional
	public List<<%=map.getName()%>> qeury<%=entityName%>s(@SearchParameter(defaultValue = "1",name = "startRow")int startRow, @SearchParameter(defaultValue = "20",name = "pageSize")int pageSize,
				<%=conParamsStr%>,
				@SearchParameter(name="orderList")List<OrderItem>orderList){
		   //实例化List对象		
		   List<<%=map.getName()%>> list = new ArrayList<<%=map.getName()%>>();
		   //查询结果实体
		   List<<%=entryObjName%>> entryList = <%=StringHelper.fistChartLowerCase(mapperObjName)%>.qeury<%=map.getClazz()%>s(startRow,pageSize,this.getDaoAbb(),<%=tempStr%>,orderList);
	       if (entryList != null){
			  for (<%=entryObjName%> entry : entryList) {
				   list.add(new <%=map.getName()%>(<%=funcParamsStr%>));
				   entry = null;
			  }
			  entryList.clear();
			  entryList = null;
		   }
		return list;
	}
				
			

	
	
	
	//自定义方法
	//*****************************************************************************************************************
	
	
}
