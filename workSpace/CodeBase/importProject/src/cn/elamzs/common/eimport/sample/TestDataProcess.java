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
		return null;
	}

	@Override
	public void forEachRowValueProcess(DataElement data)
			throws DataProcessException {
		// TODO Auto-generated method stub
		
	}

}
