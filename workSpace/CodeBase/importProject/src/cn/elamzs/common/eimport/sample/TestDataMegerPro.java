package cn.elamzs.common.eimport.sample;

/**
 * 
 * ����������м�����ݵ� �������
 * �м���������Ϊ����Դ
 *  
 * @author Ethan.Lam
 *
 */
public interface TestDataMegerPro {

	 StringBuffer insertSql = new StringBuffer();
	
	 public void setInsertTempSql();
	     
     public void filterUnligealData();
     
     public void insertData();
     
     public void updateData();
     
	 public void updateInsert();
 
	 
	 
}
