package web.test.impb;

import java.util.Vector;

import cn.elamzs.common.eimport.core.DataElement;
import cn.elamzs.common.eimport.exception.DataProcessException;
import cn.elamzs.common.eimport.inter.DataProcess;


/**
 * 
 * @author Ethan.Lam   2011-2-7
 *
 */
public class TTestDataProcess implements DataProcess {

	
    Vector<String[]> valueSet = new Vector<String[]>(20);
    
	
	public void afterLoadAllRowsDataProcess() throws DataProcessException {
		// TODO Auto-generated method stub
		System.out.println("finished loaded all imp datas��");
	}

	
	public String[][] createImportResult() throws DataProcessException {
		// TODO Auto-generated method stub
        String [][] impResultDatas = new String[valueSet.size()][5];
	    int index = 0 ;
        if(valueSet.size()>0)
		for(String[] value : valueSet){
			impResultDatas[index] = value;
			index++;
		} 
		return impResultDatas;
	}

	
	public void forEachRowValueProcess(DataElement data)
			throws DataProcessException {
		String[] values = new String[5];
		for(int index=0;index<4;index++){ 
	      values[index] = data.getColumnValue(index);
		  System.out.print(data.getColumnValue(index)+",");
		}
		 System.out.print(data.returnRowValidatorMsg());
		 System.out.println();
		 values[4] = !"".equals(data.returnRowValidatorMsg())?data.returnRowValidatorMsg():"����ɹ�";
		 valueSet.add(values);
	}


	public void freeOpertion() {
		// TODO Auto-generated method stub
		
	}

}
