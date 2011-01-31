package cn.elamzs.common.eimport.inter;

import java.util.HashMap;
import java.util.Map;

public abstract class DataElement {

	private StringBuffer validatResult = new StringBuffer();
	
	
	private Map<String,String> valueSets = new HashMap<String,String>(); 
	
	
	protected void appendValidateResult(String reason){
    	validatResult.append("£»"+reason);
    }
	
	
	protected void match(){
	    
	}

	public abstract String insertTempTableSql();
	
	
}
