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
 * ���ݷ�װ�࣬�������ݶ���
 * 
 * ���������ã�1��У�������ݣ����Ӷ�Ӧ��У�����������ض�Ӧ��У������Ϣ
 *            2���ɼ��ض�Ӧ��ģ����Ϣ ��
 *            3�����浱ǰ�������ݡ�
 */
public class DataElement {

	//��¼������ÿ��������֤ʱ����ʾ���
	private StringBuffer validatResult = new StringBuffer();

	
	//������¼   �����-������  �� ����--�����   ��ֵ�� ���ݼ���   
	private Map<String,String> valueKeySets = null; 

	
	//������¼   �����-����������   ��ֵ�� ���ݼ���   
	private Map<Integer,String> cnColumnsName = null;
	
	//������¼   �����-�п�   ��ֵ�� ���ݼ���   
	private Map<Integer,Integer> columnsWidth = null;
	
	//��¼��ǰ�е����ݣ���ʱ�ڴ棩
	private Map<String,String> currentRowValuesMap = new HashMap<String,String>();
	
	
	//��֤����Ӧ�ķ���
	private Map<Integer,Method> validatorMethodSet = null;
	
	
	//����У��������
	private DataValidator validatorObject;
	
	
	public DataElement(DataValidator validator) throws Exception{
		initValidatorMethods(validator);
	}
	
	
	/**
	 * 
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
		    cnColumnsName = new HashMap<Integer,String>();
		    columnsWidth = new HashMap<Integer,Integer>();
		    
    	    for(Method method:methods){
		    	 ValidatorRule rule = method.getAnnotation(ValidatorRule.class);		    	
		    	 if(rule!=null){
		    		 
		    		 if(valueKeySets.containsKey("cs_"+rule.columnSeq()))
		    			 throw new Exception("ģ���ж����ظ��������! DataValidator Class:"+validator.getClass().getName()+",column:"+rule.columnSeq());
		    		 
		    		 valueKeySets.put("cs_"+rule.columnSeq(),rule.columnSeq()+"");
		    		 valueKeySets.put("cn_"+rule.ename(), rule.columnSeq()+"");
		    		 
		    		 cnColumnsName.put(rule.columnSeq(),rule.showName());
		    		 columnsWidth.put(rule.columnSeq(), rule.width());
		    		 
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
	 * 
	 * ִ�ж�Ӧ��У��������֤�ж�Ӧ��ֵ�Ƿ�Ϸ������Ϸ��ķ���
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
			if(validatResult!=null)
			   validatResult.delete(0, validatResult.length());
			
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
	 * 
	 * У�鵼���ļ��� ������  �Ƿ���   ��Ӧģ����   ��������Ҫ���һ��
	 * @param columns
	 * @return ���ƥ��ķ��� true /��ƥ��ķ��� false
	 */
	public boolean checkImpColumnsMatch(String[] columns){
		if(columns==null||columns.length==0||cnColumnsName.size()>columns.length)
			return false;
        
		int columnSeq = 0;
 		
		//�жϵ����е�˳���Ƿ�һ��
		for(String cname:columns){
			if(cnColumnsName.containsKey(columnSeq)&&!StringUtil.isEqual(cnColumnsName.get(columnSeq),cname))
				return false;
			columnSeq++;
 		}
		return true;
	}
	
	
	/**
	 * 
	 * �����ж�Ӧ��ֵ
	 * @param columnSeq �����
	 * @return
	 */
	public String getColumnValue(int columnSeq){
		if(this.valueKeySets.containsKey("cs_"+columnSeq))
			return this.currentRowValuesMap.get(this.valueKeySets.get("cs_"+columnSeq));
		return "";
	}
	
	/**
	 * �����ж�Ӧ��ֵ
	 * @param columnName ����
	 * @return
	 */
	public String getColumnValue(String columnName){
		if(this.valueKeySets.containsKey("cn_"+columnName))
			return this.currentRowValuesMap.get(this.valueKeySets.get("cn_"+columnName));
		return "";
	}
	
	
	/**
	 * ����������֤�����ʾ��Ϣ
	 * @return
	 */
    public String returnRowValidatorMsg(){
	    if(this.validatResult!=null&&this.validatResult.length()>0)
	    	return this.validatResult.substring(1);
	    return "";
	}

   
    /**
     * 
     * ���ص����ļ������������� ��,�� ���
     * @return
     */
    public String getImpColumnsName(){
    	String columnsNameStr = "";
    	for(int seq = 0;seq<this.cnColumnsName.size();seq++)
    		columnsNameStr+=","+this.cnColumnsName.get(seq);
    	return columnsNameStr.substring(1);
    }
    
    /**
     * ���ص����ļ��п������
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
