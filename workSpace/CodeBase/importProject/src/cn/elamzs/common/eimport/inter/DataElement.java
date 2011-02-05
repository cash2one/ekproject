package cn.elamzs.common.eimport.inter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import cn.elamzs.common.base.files.util.StringUtil;
import cn.elamzs.common.eimport.Anotation.ValidatorRule;

/**
 * 
 * @author Ethan.Lam   2011-2-5
 * 数据封装类，行列数据
 * 
 */
public class DataElement {

	//记录对输入每行数据验证时的提示结果
	private StringBuffer validatResult = new StringBuffer();

	//用来记录 列序号-列命名  键值对 数据
	private Map<String,String> valueKeySets = null; 
	
	//记录当前行的数据（临时内存）
	private Map<String,String> currentRowValuesMap = new HashMap<String,String>();
	
	//验证器对应的方法
	private Map<Integer,Method> validatorMethodSet = null;
	
	
	private DataValidator validatorObject;
	
	public DataElement(DataValidator validator) throws Exception{
		initValidatorMethods(validator);
	}
	
	/**
	 * 初始化改验证器的各个方法
	 * @param validator
	 * @throws Exception 
	 */
    void initValidatorMethods(DataValidator validator) throws Exception{
    	this.validatorObject = validator;
    	Method[] methods = validator.getClass().getMethods();
		    if(methods==null)
		    	return;
		    validatorMethodSet = new HashMap<Integer,Method>();
		    valueKeySets = new HashMap<String,String>();
    	    for(Method method:methods){
		    	 ValidatorRule rule = method.getAnnotation(ValidatorRule.class);		    	
		    	 if(rule!=null){
		    		 
		    		 if(valueKeySets.containsKey(rule.columnSeq()))
		    			 throw new Exception("模版中定义重复的列序号! DataValidator Class:"+validator.getClass().getName()+",column:"+rule.columnSeq());
		    		 
		    		 valueKeySets.put(rule.columnSeq()+"", rule.ename());
		    		 valueKeySets.put(rule.ename(), rule.columnSeq()+"");
		    		 
		    		 //假如该列配置执行验证的方法，就必须记录该验证的方法
		    		 if(rule.check())
		    		    validatorMethodSet.put(rule.columnSeq(), method); 	    		 
		    	 }
		    }
    }
	
	/**
	 * 
	 * 记录数据验证后的  “验证提示（出错）”
	 * @param reason
	 */
	protected void appendValidateResult(String reason){
    	validatResult.append(";"+reason);
    }
	
	
	/**
	 * 验证列对应的值是否合法，不合法的返回
	 * @param rowValues
	 * @return 返回验证后的信息
	 */
	public String match(String[] rowValues){
	    String errorMsg = "";
	    Method validator = null;
	    
	    if(rowValues==null||rowValues.length==0){
	        this.currentRowValuesMap.clear();
	    	return "";
	    }
		try{
			for(int columnSeq = 0;columnSeq<rowValues.length;columnSeq++){ 
				//记录当前行的数据
				this.currentRowValuesMap.put(columnSeq+"", rowValues[columnSeq]);
                //是否需要验证该列的数据				
				if(this.validatorMethodSet.containsKey(columnSeq)){
				    validator = this.validatorMethodSet.get(columnSeq);
				    errorMsg = (String) validator.invoke(validatorObject,rowValues[columnSeq]);  
	                if(!StringUtil.isNull(errorMsg))
	                	this.appendValidateResult(errorMsg);
	            }	
				errorMsg = "";
		    }
			return returnRowValidatorMsg();
		}catch(Exception e){
           e.printStackTrace();
           return "";
		}
	}
	
	/**
	 * 返回列对应的值
	 * @param columnSeq 列序号
	 * @return
	 */
	public String getColumnValue(int columnSeq){
		if(this.valueKeySets.containsKey(columnSeq+""))
			return this.currentRowValuesMap.get(this.valueKeySets.get(columnSeq+""));
		return "";
	}
	
	/**
	 * 返回列对应的值
	 * @param columnName 列名
	 * @return
	 */
	public String getColumnValue(String columnName){
		if(this.valueKeySets.containsKey(columnName))
			return this.currentRowValuesMap.get(this.valueKeySets.get(columnName));
		return "";
	}
	
	
	/**
	 * 返回数据验证后的提示信息
	 * @return
	 */
    String returnRowValidatorMsg(){
	    if(this.validatResult!=null&&this.validatResult.length()>0)
	    	return this.validatResult.substring(1);
	    return "";
	}
	
}
