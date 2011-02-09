package cn.elamzs.common.eimport.core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import cn.elamzs.common.base.files.util.StringUtil;
import cn.elamzs.common.eimport.Anotation.ValidatorRule;
import cn.elamzs.common.eimport.inter.DataValidator;

/**
 * 
 * @author Ethan.Lam   2011-2-5
 * 数据封装类，行列数据对象
 * 
 * 描述：作用：1、校验行数据（连接对应的校验器），返回对应的校验结果信息
 *            2、可加载对应的模版信息 。
 *            3、保存当前行列数据。
 */
public class DataElement {

	//记录对输入每行数据验证时的提示结果
	private StringBuffer validatResult = new StringBuffer();

	
	//用来记录   列序号-列命名  与 列名--列序号   键值对 数据集合   
	private Map<String,String> valueKeySets = null; 

	
	//用来记录   列序号-列中文命名   键值对 数据集合   
	private Map<Integer,String> cnColumnsName = null;
	
	//用来记录   列序号-列宽   键值对 数据集合   
	private Map<Integer,Integer> columnsWidth = null;
	
	//记录当前行的数据（临时内存）
	private Map<String,String> currentRowValuesMap = new HashMap<String,String>();
	
	
	//验证器对应的方法
	private Map<Integer,Method> validatorMethodSet = null;
	
	
	//数据校验器对象
	private DataValidator validatorObject;
	
	
	public DataElement(DataValidator validator) throws Exception{
		initValidatorMethods(validator);
	}
	
	
	/**
	 * 
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
		    cnColumnsName = new HashMap<Integer,String>();
		    columnsWidth = new HashMap<Integer,Integer>();
		    
    	    for(Method method:methods){
		    	 ValidatorRule rule = method.getAnnotation(ValidatorRule.class);		    	
		    	 if(rule!=null){
		    		 
		    		 if(valueKeySets.containsKey("cs_"+rule.columnSeq()))
		    			 throw new Exception("模版中定义重复的列序号! DataValidator Class:"+validator.getClass().getName()+",column:"+rule.columnSeq());
		    		 
		    		 valueKeySets.put("cs_"+rule.columnSeq(),rule.columnSeq()+"");
		    		 valueKeySets.put("cn_"+rule.ename(), rule.columnSeq()+"");
		    		 
		    		 cnColumnsName.put(rule.columnSeq(),rule.showName());
		    		 columnsWidth.put(rule.columnSeq(), rule.width());
		    		 
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
	 * 
	 * 执行对应的校验器，验证列对应的值是否合法，不合法的返回
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
			if(validatResult!=null)
			   validatResult.delete(0, validatResult.length());
			
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
	 * 
	 * 校验导入文件的 导入列  是否与   对应模版类   中所设置要求的一致
	 * @param columns
	 * @return 完成匹配的返回 true /不匹配的返回 false
	 */
	public boolean checkImpColumnsMatch(String[] columns){
		if(columns==null||columns.length==0||cnColumnsName.size()>columns.length)
			return false;
        
		int columnSeq = 0;
 		
		//判断导入列的顺序是否一致
		for(String cname:columns){
			if(cnColumnsName.containsKey(columnSeq)&&!StringUtil.isEqual(cnColumnsName.get(columnSeq),cname))
				return false;
			columnSeq++;
 		}
		return true;
	}
	
	
	/**
	 * 
	 * 返回列对应的值
	 * @param columnSeq 列序号
	 * @return
	 */
	public String getColumnValue(int columnSeq){
		if(this.valueKeySets.containsKey("cs_"+columnSeq))
			return this.currentRowValuesMap.get(this.valueKeySets.get("cs_"+columnSeq));
		return "";
	}
	
	/**
	 * 返回列对应的值
	 * @param columnName 列名
	 * @return
	 */
	public String getColumnValue(String columnName){
		if(this.valueKeySets.containsKey("cn_"+columnName))
			return this.currentRowValuesMap.get(this.valueKeySets.get("cn_"+columnName));
		return "";
	}
	
	
	/**
	 * 返回数据验证后的提示信息
	 * @return
	 */
    public String returnRowValidatorMsg(){
	    if(this.validatResult!=null&&this.validatResult.length()>0)
	    	return this.validatResult.substring(1);
	    return "";
	}

   
    /**
     * 
     * 返回导入文件中文列名，用 “,” 相隔
     * @return
     */
    public String getImpColumnsName(){
    	String columnsNameStr = "";
    	for(int seq = 0;seq<this.cnColumnsName.size();seq++)
    		columnsNameStr+=","+this.cnColumnsName.get(seq);
    	return columnsNameStr.substring(1);
    }
    
    /**
     * 返回导入文件列宽的设置
     * @return
     */
    public int[] getImpColumnsWidthSet(){
    	int[] columnsWidths = new int[columnsWidth.size()];
    	for(int seq = 0;seq<this.columnsWidth.size();seq++)
    		columnsWidths[seq]=columnsWidth.get(seq);
    	return columnsWidths;
    }
    
    
	protected void free() throws Throwable {
		
		this.cnColumnsName.clear();
		this.cnColumnsName = null;
		
		this.currentRowValuesMap.clear();
		this.currentRowValuesMap = null;
		
		this.validatorMethodSet.clear();
		this.validatorMethodSet = null;
		
		this.validatorObject = null;
		
		this.validatResult = null;
		
		this.valueKeySets.clear();
		this.valueKeySets = null;
		
	}
	
    
    
    
}
