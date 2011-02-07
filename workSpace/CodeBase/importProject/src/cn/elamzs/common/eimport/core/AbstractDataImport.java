package cn.elamzs.common.eimport.core;

import java.io.File;

import cn.elamzs.common.eimport.enums.FileType;
import cn.elamzs.common.eimport.inter.DataProcess;
import cn.elamzs.common.eimport.inter.DataValidator;
import cn.elamzs.common.eimport.inter.EImporter;

/**
 * 
 * @author Ethan.Lam   2011-2-5
 *
 */
public abstract class AbstractDataImport implements EImporter {

	protected FileType fileType;

	protected String fileName;
	
	DataValidator validator = null;  //数据验证器
	
	DataProcess dataProcess = null;  //数据处理器
	
	FileHandler handler = null;
	
	public AbstractDataImport(DataValidator validator,String fileName){
	        this.validator = validator;
	        this.fileName = fileName;
	}

	@Override
	public File getImportedResult(String fileIdentifyId) throws Exception{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getResourceFile(String fileIdentifyId) throws Exception{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String importFile(String dataFile) throws Exception {
		// TODO Auto-generated method stub
		
		//根据文件，配置对应的处理类
		appendDataHandler(dataFile);
		
		//开始执行文件导入数据处理，启动线程处理
		Thread importThread = new Thread(handler);
        importThread.start(); 
        
		return null;
	}

	
	/**
	 * 根据文件类型，装配对应的处理类
	 * @param fileName
	 * @throws Exception 
	 */
	void appendDataHandler(String fileName) throws Exception{
		File file = new File(fileName);
		
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		for(FileType t:FileType.values()){
			if(FileType.EXCEL_XLS.suffix().equals(suffix)){
                this.fileType = t;
			}
		}
		
		//
		if(FileType.EXCEL_XLS.equals(fileType)||FileType.EXCEL_XLSX.equals(fileType))
			this.handler = (FileHandler) new ExcelImportHandler(validator,dataProcess,file,FileType.EXCEL_XLS.equals(fileType)?0:1);
		else if(FileType.TXT.equals(fileType))
			this.handler = new TxtImportHandler(validator,dataProcess,file);
		
	};
	
	
	
	
}
