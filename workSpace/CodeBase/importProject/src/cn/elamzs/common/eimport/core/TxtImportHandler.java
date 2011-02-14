package cn.elamzs.common.eimport.core;

import java.io.File;

import cn.elamzs.common.eimport.inter.DataProcess;
import cn.elamzs.common.eimport.inter.DataValidator;
import cn.elamzs.common.eimport.inter.ImportHandleListener;

/**
 * 
 * @author Ethan.Lam   2011-2-5
 *
 */
public class TxtImportHandler extends AbstractFileHandler {

	public TxtImportHandler(String importTaskId,DataValidator validator,DataProcess dataPro,ImportHandleListener listenner,File file,String storeSubDir) throws Exception {
		super(importTaskId,validator,dataPro,listenner,file,storeSubDir);
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
	public String createImportResultDocument(String[][] resultDatas)
			throws Exception {
		// TODO Auto-generated method stub

		return "";
	}


}
