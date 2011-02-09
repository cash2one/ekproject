package cn.elamzs.common.eimport.sample;

import cn.elamzs.common.eimport.core.DataElement;
import cn.elamzs.common.eimport.exception.DataProcessException;
import cn.elamzs.common.eimport.inter.DataProcess;


/**
 * 
 * @author Ethan.Lam   2011-2-7
 *
 */
public class TestDataProcess implements DataProcess {

	
	@Override
	public void afterLoadAllRowsDataProcess() throws DataProcessException {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public String[][] createImportResult() throws DataProcessException {
		// TODO Auto-generated method stub
		return new String[][]{{"a_1","123456","11","","test"},{"a_1","123456","11","","test"},{"a_1","123456","11","","test"},{"a_1","123456","11","","test"},{"a_1","123456","11","","test"}};
	}

	
	@Override
	public void forEachRowValueProcess(DataElement data)
			throws DataProcessException {
		for(int index=0;index<4;index++){  
		  System.out.print(data.getColumnValue(index)+",");
		}
		
		 System.out.print(data.returnRowValidatorMsg());
		 System.out.println();
	}

}
