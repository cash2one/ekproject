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

	//��¼������ÿ��������֤ʱ����ʾ���
	private StringBuffer validatResult = new StringBuffer();

	//����װ��ÿ�е�����
	private Map<String,String> valueSets = new HashMap<String,String>(); 
	
	
	protected void appendValidateResult(String reason){
    	validatResult.append("��"+reason);
    }
	
	
	protected void match(){
	    
	}

	public abstract String insertTempTableSql();
	
	
}
