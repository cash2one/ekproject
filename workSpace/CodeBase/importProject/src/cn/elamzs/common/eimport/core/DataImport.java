package cn.elamzs.common.eimport.core;

import java.io.File;

import cn.elamzs.common.eimport.enums.FileType;
import cn.elamzs.common.eimport.inter.DataValidator;
import cn.elamzs.common.eimport.inter.EImporter;

/**
 * @author Ethan.Lam
 *
 */
public abstract class DataImport implements EImporter {

	protected FileType fileType;

	DataValidator validator = null;  //数据验证器
	
	@Override
	public File getImportedResult(String fileIdentifyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getResourceFile(String fileIdentifyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String upLoadFile(String dataFile) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * @param fileName
	 */
	void appendDataHandler(String fileName){
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		for(FileType t:FileType.values()){
			if(FileType.EXCEL_XLS.suffix().equals(suffix)){
                this.fileType = t;
			}
		}
		
	};
	
	
}
