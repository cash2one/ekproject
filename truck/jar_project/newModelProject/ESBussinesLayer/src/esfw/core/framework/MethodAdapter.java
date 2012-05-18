package esfw.core.framework;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;

import esfw.base.util.DateUtil;
import esfw.core.framework.annotation.ParamsObject;
import esfw.core.framework.annotation.SearchParameter;
import esfw.core.framework.business.enumeration.MatchingType;


/**
 * 通用的查询接口
 * 方法适配调用
 * 被匹配调用调用的方法必须有如下的注解
 * 1、方法的参数必须加上 @SearchParameter
 * 2、注意：目前逻辑层的查询方法中对于“ 字符串的字段” 的查询方式 均以 各个参数对应所配置的 @SearchParameter 中 type 决定 （默认的情况下都以“精确查询”的方式进行查询，即 String)，
 *    根据实际的接口需要，可以在对应的字段的查询方式中进行 重置  @SearchParameter(type="新的匹配方法")，
 *    当重置后，在利用本代理接口 MethodAdapter.invoke() 调用查询方法时，可以再选择是否应用 重置后的 查询方式配置项。
 * @author Ethan.Lam  2011-7-20
 */
public class MethodAdapter {

    static Logger logger = Logger.getLogger(MethodAdapter.class);
	 
	 /**
	  *  
      * @param obj  被调用的对象
      * @param methodName  被调用对象的方法
      * @param mathingTypeOn 是否忽略查询接口中已被配置的查询方式；true：根据方法参数的配置的方式进行查询；false： 只采取精确查询的方法 （查询方法中对于String 类型的字段的数据匹配方式 MatchingType的配置）
      * @param params  被调用方法的参数集合
	  * @return
	  * @throws Exception 
	  */
     public static Object invoker(Object obj,String methodName,boolean mathingTypeOn,Map<String,Object> params){
    	 if(obj==null)
    	   return null;
    	 Method invoker= null;
    	 Method[] methods = obj.getClass().getMethods();
    	 for(Method method:methods){
    		 if(method.getName().toLowerCase().equals(methodName.toLowerCase()))
    			 invoker = method;
    	 }
    	 if(invoker==null){
    		 System.out.println("MethodAdapter 接口异常: "+obj.getClass().getCanonicalName()+"没有定义 "+methodName+"方法!!");
    		 return null;
    	 }
    	 
    	 Annotation[][] parameterAnnotations = invoker.getParameterAnnotations();
    	 Class[] paramsType = invoker.getParameterTypes();
    	 Object[] paramsValue = new Object[paramsType.length];
    	 
    	 int index = 0;
    	 for(int j=0;j<parameterAnnotations.length;j++)
         {
             int length = parameterAnnotations[j].length;
             if(length==0)
             {
                System.out.println(" ");
             }else{
                 for(int k=0;k<length;k++){
                	 
                	 SearchParameter pa = (SearchParameter)parameterAnnotations[j][k];
                	 Object val = params.get(pa.name().toLowerCase());
                	 val = val==null?params.get(pa.name()):val;
                	 val = "".equals(val)?null:val;
                	 
              
                	 //分页的默认记录数做扩大处理
           		     if("pagesize".equals(pa.name().toLowerCase())&&val==null){
           			      val = 5000;  
           		     }
                	 

                	 //处理字符字符串的查询条件
                     if(val!=null&&paramsType[index].getName().toLowerCase().endsWith("string")){
                    	  val = val!=null?val.toString().trim():val;//去掉前后空格
                    	  val = filterAndReplaceParamIllegalChars(val.toString()); //去掉非法的符号
                          val = (pa.type().equals(MatchingType.EXACT))?val:val;  //精确查询
                          
                          //是否应用改配置
                          if(mathingTypeOn){   
	                          val = (pa.type().equals(MatchingType.LEFT_FUZZY))?"%"+((String)val):val;  //左匹配
	                          val = (pa.type().equals(MatchingType.RIGHT_FUZZY))?((String)val)+"%":val; //右匹配
	                          val = (pa.type().equals(MatchingType.ALL_FUZZY))?"%"+((String)val)+"%":val; //模糊查询
                          }
	                          
                     }
                	 
                	 //处理是否需要给查询配置默认的值
                	 if(val==null){
                		 //基本的数据类型，处理整数，
                         if("int".equals(paramsType[index].getName().toLowerCase())){
                        	 val = !pa.defaultValue().toLowerCase().equals("null")?Integer.parseInt(pa.defaultValue()):-1;
                         }else if("long".equals(paramsType[index].getName().toLowerCase())){
                        	 val = !pa.defaultValue().toLowerCase().equals("null")?Long.parseLong(pa.defaultValue()):-1;
                         }else if("double".equals(paramsType[index].getName().toLowerCase())){
                        	 val = !pa.defaultValue().toLowerCase().equals("null")?Double.parseDouble(pa.defaultValue()):-1;
                         }else if("float".equals(paramsType[index].getName().toLowerCase())){
                        	 val = !pa.defaultValue().toLowerCase().equals("null")?Float.parseFloat(pa.defaultValue()):-1;
                         }else
                        	 val = null;
                	 }
                	 
                	 //把对应的参数传递到方法的参数数组上
                	 if(val!=null&&(paramsType[index].getName().toLowerCase().endsWith("date")||paramsType[index].getName().toLowerCase().endsWith("time"))){
                		 //针对时间条件的特殊处理规则
                		 if(val.toString().trim().length()<=10) 
                		    paramsValue[index]=DateUtil.timeStrToDate(val.toString(),"yyyy-MM-dd");
                		 else  if(val.toString().trim().length()<=29) 
                			paramsValue[index]=DateUtil.timeStrToDate(val.toString(),"yyyy-MM-dd HH:mm:ss");
                	 }else{
                		  paramsValue[index]= val;
                	}
                	 index++;
                 }
             }
         }
    	 try {
    		params = null;
			return invoker.invoke(obj,paramsValue);
		} catch ( Exception e) {
			e.printStackTrace();
			logger.error("MethodAdapter方法执行异常...", e);
			return null;
		}
     }
	
     
     /**
      * 该方法已被抛弃使用，详细请用另一个接口
      * @param obj  被调用的对象
      * @param methodName  被调用对象的方法
      * @param params  被调用方法的参数集合
      * @return
      */
     @Deprecated
     public static Object invoker(Object obj,String methodName,Map<String,Object> params){
    	 return invoker(obj,methodName,true,params);
     }
     
     
     /**
      * 该方法已被抛弃使用，详细请用另一个接口
      * @param obj  被调用的对象
      * @param methodName  被调用对象的方法
      * @param args  被调用方法的参数集合
      * @return
      */
     @Deprecated
     public static Object invoker(Object obj,String methodName,Object[]...args){
    	 return invoker(obj,methodName,true,args);
     }
     
     
     
     /**
      * 该方法已被抛弃使用，详细请用另一个接口
      * @param obj  被调用的对象
      * @param methodName  被调用对象的方法
      * @param voObj  被调用方法的参数域对象
      * @return
      */
     @Deprecated
     public static Object invoker(Object obj,String methodName,ParamsObject voObj){
    	 return invoker(obj,methodName,true,voObj);
     }
     
     
     
     
     /**
      * 新的 接口代理调用方法（与之前的方法有区别，多一个参数  mathingTypeOn）
      * 
      * @param obj  被调用的对象
      * @param methodName  被调用对象的方法
      * @param mathingTypeOn 是否忽略查询接口中已被配置的查询方式；true：根据配置的方式；false： 只采取精确查询的方法 （查询方法中对于String 类型的字段的数据匹配方式 MatchingType的配置）
      * @param args  被调用方法的参数集合
      * @return
      */
     public static Object invoker(Object obj,String methodName,boolean mathingTypeOn,Object[]...args) {
    	 if(obj==null)
    		 return null;
    	 Map<String,Object> params = new HashMap<String,Object>();
    	 if(args!=null)
    	 for(Object[] objs :args ){
    		 if(objs[0]!=null)
    			 params.put(objs[0].toString(),objs[1]);
    	 }
		 return invoker(obj,methodName,mathingTypeOn,params);
		 
     }
     
     
     
     /**
      * 
      * 新的 接口代理调用方法（与之前的方法有区别，多一个参数  mathingTypeOn）
      * 
      * @param obj  被调用的对象
      * @param methodName  被调用对象的方法
      * @param mathingTypeOn 是否忽略查询接口中已被配置的查询方式；true：根据配置的方式；false： 只采取精确查询的方法 （查询方法中对于String 类型的字段的数据匹配方式 MatchingType的配置）
      * @param voObj  被调用方法的参数域对象
      * @return
      */
     public static Object invoker(Object obj,String methodName,boolean mathingTypeOn,ParamsObject voObj){
    	 if(obj==null)
    		 return null;
    	 Map<String,Object> params = new HashMap<String,Object>();
    	 try{
    		 Method[] methods = voObj.getClass().getMethods();
        	 Map<String,Integer> methodMap = new HashMap<String,Integer>();
        	 for(Method method:methods){
        		 if(method.getName().startsWith("get")){
        			 methodMap.put(method.getName().substring(3).toLowerCase(),1);
        		 }
        	 }
        	 methods = null;
        	 Vector<Field> fieldSet = getAllDeclaredFields(voObj.getClass());
        	 for(Field field:fieldSet){
        		 field.setAccessible(true);
        		 if(methodMap.containsKey(field.getName().toLowerCase())){
        			 if(field.get(voObj)!=null&&field.getType()==String.class){
        				 //手动转码
        				 params.put(field.getName(),URLDecoder.decode((String)field.get(voObj),"utf-8"));
        			 }else 
        				 params.put(field.getName(),field.get(voObj)); 			 
        		 }
        	 }
        	 fieldSet.clear();
        	 fieldSet = null;
    	 }catch(Exception e){
    		 e.printStackTrace();
    		 logger.error("MethodAdapter方法执行异常...", e);
    	 }
	     return invoker(obj,methodName,mathingTypeOn,params);
		 
     }
     
     
     
     
    /**
     * 
     * 枚举所有属性
     * @param clazz
     * @return
     */
 	static Vector<Field> getAllDeclaredFields(Class clazz) {
		Vector<Field> fileds = new Vector<Field>(5);
		Vector<Field> temps = null;
		if (clazz.getSuperclass() != null) {
			temps = getAllDeclaredFields(clazz.getSuperclass());
		}
		if (temps != null)
			for (Field field : temps)
				fileds.add(field);
		temps = null;
		Field[] fs = clazz.getDeclaredFields();
		if (fs != null)
			for (Field field : fs)
				fileds.add(field);
		fs = null;
		return fileds;
	}
	
     
 	/**
 	 * 
 	 * 处理输入参数中出现的非法符号，例如  “ % ”
 	 * @param paramsValue
 	 */
 	static String filterAndReplaceParamIllegalChars(String paramsValue){
 		if(paramsValue==null)
 			return null;
 		paramsValue=paramsValue.replace("%","");
 		return paramsValue;
 	}
 	
 	
     
     public static void main(String...args) throws Exception{
    	 Test2 info = new Test2();
    	 Map<String,Object> params  = new HashMap<String,Object>();
    	 params.put("param2", "param2");
    	 params.put("param1", 11);
    	 System.out.println(invoker(info,"find",params));
    	 System.out.println(invoker(info,"find",new Object[][]{{"param2","param2"},{"param1",11}}));
    	 System.out.println(invoker(info,"find",new Object[]{"param1",11}));
    	 System.out.println(invoker(info,"find",info));
    	 
    	 
     }
	 
 
	 
	
}


class Test {
	 
	private String param2="params200";

	public String getParam2() {
		return param2;
	}


	public String find(@SearchParameter(defaultValue = "1000",name = "param1")long param1,@SearchParameter(defaultValue = "3",name = "param2")String param2){
		 System.out.println("params1: "+param1);
		 System.out.println("params2: "+param2);
		 return "test";
	 }
	 
	
}


class Test2 extends Test implements ParamsObject{
	
	private int param1=400;
	
	public int getParam1() {
		return param1;
	}
	
}
