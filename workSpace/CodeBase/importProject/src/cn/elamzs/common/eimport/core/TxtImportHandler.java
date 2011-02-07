package cn.elamzs.common.eimport.core;

import java.io.File;

import cn.elamzs.common.eimport.inter.DataProcess;
import cn.elamzs.common.eimport.inter.DataValidator;

/**
 * 
 * @author Ethan.Lam   2011-2-5
 *
 */
public class TxtImportHandler extends AbstractFileHandler {

	public TxtImportHandler(DataValidator validator,DataProcess dataPro,File file) throws Exception {
		super(validator,dataPro,file);
	}

	
	@Override
	public String[] returnImpDocColumnsName() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public void loadDatas() {
		// TODO Auto-generated method stub
	
		
	}

	
	@Override
	public void createImportResultDocument(String[][] resultDatas)
			throws Exception {
		// TODO Auto-generated method stub

		
	}

	

}
