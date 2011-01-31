package cn.elamzs.common.eimport.sample;

/**
 * 
 * 定义了针对中间表数据的 处理过程
 * 中间表的数据作为数据源
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
