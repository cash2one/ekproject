package cn.elamzs.common.eimport.inter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import cn.elamzs.common.base.files.util.StringUtil;
import cn.elamzs.common.eimport.Anotation.ValidatorRule;

/**
 * 
 * @author Ethan.Lam   2011-2-5
 * ���ݷ�װ�࣬��������
 * 
 */
public class DataElement {

	//��¼������ÿ��������֤ʱ����ʾ���
	private StringBuffer validatResult = new StringBuffer();

	//������¼ �����-������  ��ֵ�� ����
	private Map<String,String> valueKeySets = null; 
	
	//��¼��ǰ�е����ݣ���ʱ�ڴ棩
	private Map<String,String> currentRowValuesMap = new HashMap<String,String>();
	
	//��֤����Ӧ�ķ���
	private Map<Integer,Method> validatorMethodSet = null;
	
	
	private DataValidator validatorObject;
	
	public DataElement(DataValidator validator) throws Exception{
		initValidatorMethods(validator);
	}
	
	/**
	 * ��ʼ������֤���ĸ�������
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
		    			 throw new Exception("ģ���ж����ظ��������! DataValidator Class:"+validator.getClass().getName()+",column:"+rule.columnSeq());
		    		 
		    		 valueKeySets.put(rule.columnSeq()+"", rule.ename());
		    		 valueKeySets.put(rule.ename(), rule.columnSeq()+"");
		    		 
		    		 //�����������ִ����֤�ķ������ͱ����¼����֤�ķ���
		    		 if(rule.check())
		    		    validatorMethodSet.put(rule.columnSeq(), method); 	    		 
		    	 }
		    }
    }
	
	/**
	 * 
	 * ��¼������֤���  ����֤��ʾ��������
	 * @param reason
	 */
	protected void appendValidateResult(String reason){
    	validatResult.append(";"+reason);
    }
	
	
	/**
	 * ��֤�ж�Ӧ��ֵ�Ƿ�Ϸ������Ϸ��ķ���
	 * @param rowValues
	 * @return ������֤�����Ϣ
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
				//��¼��ǰ�е�����
				this.currentRowValuesMap.put(columnSeq+"", rowValues[columnSeq]);
                //�Ƿ���Ҫ��֤���е�����				
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
	 * �����ж�Ӧ��ֵ
	 * @param columnSeq �����
	 * @return
	 */
	public String getColumnValue(int columnSeq){
		if(this.valueKeySets.containsKey(columnSeq+""))
			return this.currentRowValuesMap.get(this.valueKeySets.get(columnSeq+""));
		return "";
	}
	
	/**
	 * �����ж�Ӧ��ֵ
	 * @param columnName ����
	 * @return
	 */
	public String getColumnValue(String columnName){
		if(this.valueKeySets.containsKey(columnName))
			return this.currentRowValuesMap.get(this.valueKeySets.get(columnName));
		return "";
	}
	
	
	/**
	 * ����������֤�����ʾ��Ϣ
	 * @return
	 */
    String returnRowValidatorMsg(){
	    if(this.validatResult!=null&&this.validatResult.length()>0)
	    	return this.validatResult.substring(1);
	    return "";
	}
	
}
