package cn.elamzs.common.eimport.core;

import java.io.File;

import cn.elamzs.common.base.files.util.FileOperateUtil;
import cn.elamzs.common.eimport.config.ConfigSetting;
import cn.elamzs.common.eimport.core.handler.ExcelImportHandler;
import cn.elamzs.common.eimport.core.handler.TxtImportHandler;
import cn.elamzs.common.eimport.enums.FileType;
import cn.elamzs.common.eimport.inter.DataProcess;
import cn.elamzs.common.eimport.inter.DataValidator;
import cn.elamzs.common.eimport.inter.EImporter;
import cn.elamzs.common.eimport.inter.ImportHandleListener;

/**
 * 
 * @author Ethan.Lam   2011-2-5
 * 创建线程完成文件导入的 对应的操作过程
 * 
 * 
 */
public class ThreadDataImport implements EImporter {

	protected FileType fileType;

	DataValidator validator = null;  //数据验证器
	
	DataProcess dataProcess = null;  //数据处理器
	
	FileHandler handler = null; //具体的文件导入分析器
	
	ImportHandleListener listenner = null; //导入事件监听器
	
	public ThreadDataImport(DataValidator validator,DataProcess dataProcess){
	        this.validator = validator;
	        this.dataProcess = dataProcess;
	}

	@Override
	public File getImportedResult(String importTaskSeq) throws Exception{
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public File getResourceFile(String importTaskSeq) throws Exception{
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public File downTemplate(FileType type)
			throws Exception {
		// TODO Auto-generated method stub
		Template template = new Template(validator);
		return template.createImpTemplateDoc(type);
	}
	
	@Override
	public String importFile(String dataFile,String alias,String storeSubDir) throws Exception {
		// TODO Auto-generated method stub
		
		importTaskPrepare(alias,dataFile,storeSubDir);
		
		//开始执行文件导入数据处理，启动线程处理
		Thread importThread = new Thread(handler);
        importThread.start(); 
        
		return null;
	}

	
    /**
     * 正式执行文件导入前的管理操作
     * 对源文件的备份、分配导入任务ID、调用外围事件监听器  等	
     * @param fileAlias
     * @param srcFile
     * @param subDirName
     * @throws Exception
     */
	void importTaskPrepare(String fileAlias,String srcFile,String subDirName) throws Exception{
		File _src = new File(srcFile);
		String suffix = srcFile.substring(srcFile.lastIndexOf("."));
		String importTaskSeqId = reNameFileId(srcFile);
		
		String _newFileName  = importTaskSeqId+suffix;
		String _cpyObj =  ConfigSetting.DIR_IMPORT_SRC+"/"+(subDirName!=null?subDirName:"")+"/"+_newFileName; 
		
		//copy导入文件到指定保存目录
		FileOperateUtil.copyFile(_src, new File(_cpyObj));
		
		//调用事件监听
		invokeImportListenner(importTaskSeqId,fileAlias,_cpyObj,subDirName);
		
		//根据文件，配置对应的处理类
		appendDataHandler(importTaskSeqId,_cpyObj,subDirName);
	}
	
	
	/**
	 * 
	 * 为导入文件重新命名，保证唯一的文件名
	 * @param srcFile
	 * @return
	 */
	String reNameFileId(String srcFile){
		return System.currentTimeMillis()+"";
	}
	
	
	/**
	 * 
	 * @param alias      文件显示的名称
	 * @param srcFile    导入文件的全路径
	 * @param subDirName 定位在那个子文件夹中
	 * @return
	 */
	void invokeImportListenner(final String importTaskSeqId,final  String alias,final  String srcFile,final String subDirName){
		//保存这个任务信息记录,
		if(listenner!=null)
		   listenner.beforeImportData(importTaskSeqId, alias, srcFile);
		else{
		   //
			
		}
	}
	
	
	/**
	 * 根据文件类型，装配对应的处理类
	 * @param fileName
	 * @throws Exception 
	 */
	void appendDataHandler(String importTaskId,String fileName,String storeSubDir) throws Exception{
		File file = new File(fileName);
		
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		for(FileType t:FileType.values()){
			if(t.suffix().equals(suffix)){
                this.fileType = t;
			}
		}
		
		if(FileType.EXCEL_XLS.equals(fileType)||FileType.EXCEL_XLSX.equals(fileType))
			this.handler = (FileHandler) new ExcelImportHandler(importTaskId,validator,dataProcess,listenner,file,storeSubDir,FileType.EXCEL_XLS.equals(fileType)?0:1);
		else if(FileType.TXT.equals(fileType))
			this.handler = new TxtImportHandler(importTaskId,validator,dataProcess,listenner,file,storeSubDir);
		
	}
	
	
	
}
