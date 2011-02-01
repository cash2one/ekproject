package cn.elamzs.common.eimport.inter;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Ethan.Lam
 * 
 *
 */
public abstract class DataElement {

	//记录对输入每行数据验证时的提示结果
	private StringBuffer validatResult = new StringBuffer();

	//用来装载每行的数据
	private Map<String,String> valueSets = new HashMap<String,String>(); 
	
	
	protected void appendValidateResult(String reason){
    	validatResult.append("；"+reason);
    }
	
	
	protected void match(){
	    
	}

	public abstract String insertTempTableSql();
	
	
}
