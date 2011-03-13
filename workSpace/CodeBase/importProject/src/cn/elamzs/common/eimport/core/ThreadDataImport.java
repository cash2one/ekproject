package cn.elamzs.common.eimport.core;

import java.io.File;

import cn.elamzs.common.base.files.util.FileOperateUtil;
import cn.elamzs.common.eimport.config.ConfigSetting;
import cn.elamzs.common.eimport.core.handler.ExcelImportHandler;
import cn.elamzs.common.eimport.core.handler.TxtImportHandler;
import cn.elamzs.common.eimport.enums.FileType;
import cn.elamzs.common.eimport.enums.TaskState;
import cn.elamzs.common.eimport.inter.DataProcess;
import cn.elamzs.common.eimport.inter.DataValidator;
import cn.elamzs.common.eimport.inter.EImporter;
import cn.elamzs.common.eimport.inter.ImportHandleListener;
import cn.elamzs.common.eimport.item.TaskModel;

/**
 * 
 * @author Ethan.Lam   2011-2-5
 * �����߳�����ļ������ ��Ӧ�Ĳ�������
 * 
 */
public class ThreadDataImport implements EImporter {

	protected FileType fileType;  //�ļ�����

	DataValidator validator = null;  //������֤��
	
	DataProcess dataProcess = null;  //���ݴ�����
	
	FileHandler handler = null; //������ļ����������
	
	ImportHandleListener listenner = null; //�����¼�������
	
	FileStorePersisService srv;
	
	
	/**
	 * @param validator
	 * @param dataProcess
	 */
	public ThreadDataImport(DataValidator validator,DataProcess dataProcess){
	        this.validator = validator;
	        this.dataProcess = dataProcess;
	        srv = new FileStorePersisService();
	}

	
    /**
     * ���ص������ݺ��γɵĴ�����
	 * @param importTaskSeq
     */
	public File getImportedResult(String importTaskSeq) throws Exception{
		// TODO Auto-generated method stub
		TaskModel task = srv.getTaskInfo(importTaskSeq);
		if(task!=null&&task.getState()!=1)
			return null;
		else{
			File file = new File(task.getResultPath());
		    if(file.exists())
		    	return file;
		    else
		    	return null;
		}
	}

	
	/**
	 * 
	 * ����Դ�������ݵ��ļ�
	 * @param importTaskSeq  ��������ID
	 * 
	 */
	public File getResourceFile(String importTaskSeq) throws Exception{
		// TODO Auto-generated method stub
		TaskModel task = srv.getTaskInfo(importTaskSeq);
		if(task!=null)
			return null;
		else{
			File file = new File(task.getSrcPath());
		    if(file.exists())
		    	return file;
		    else
		    	return null;
		}
	}

	
	
	/**
	 * 
	 * ���ص���ģ��
	 * @param type �ļ�����
	 *  
	 */
	public File downTemplate(FileType type)
			throws Exception {
		// TODO Auto-generated method stub
		Template template = new Template(validator);
		return template.createImpTemplateDoc(type);
	}
	
	
	
	/**
	 * 
	 * �����ļ��������
	 * @param  dataFile     �ļ�ȫ·��
	 * @param  alias        �ļ����ƣ�������
	 * @param  storeSubDir  ��Ŀ¼
	 * @param  taskType  ��������ע��
	 * 
	 */
	public String importFile(String dataFile,String alias,String storeSubDir,String taskType) throws Exception {
		// TODO Auto-generated method stub
		
		String taskId = importTaskPrepare(alias,dataFile,storeSubDir,taskType);
		
		//��ʼִ���ļ��������ݴ��������̴߳���
		Thread importThread = new Thread(handler);
        importThread.start(); 
        
		return taskId;
	}

	
    /**
     * 
     * ��ʽִ���ļ�����ǰ�Ĺ������
     * ��Դ�ļ��ı��ݡ����䵼������ID��������Χ�¼�������  ��	
     * @param fileAlias
     * @param srcFile
     * @param subDirName
     * @throws Exception
     */
	String importTaskPrepare(String fileAlias,String srcFile,String subDirName,String taskType) throws Exception{
		File _src = new File(srcFile);
		String suffix = srcFile.substring(srcFile.lastIndexOf("."));
		String importTaskSeqId = reNameFileId(srcFile);
		
		String _newFileName  = importTaskSeqId+suffix;
		String _cpyObj =  ConfigSetting.DIR_IMPORT_SRC+"/"+(subDirName!=null?subDirName:"")+"/"+_newFileName; 
		
		//copy�����ļ���ָ������Ŀ¼
		FileOperateUtil.copyFile(_src, new File(_cpyObj));
		
		//�����¼�����
		invokeImportListenner(importTaskSeqId,fileAlias,_cpyObj,subDirName);
		
		//�����ļ������ö�Ӧ�Ĵ�����
		appendDataHandler(importTaskSeqId,_cpyObj,subDirName);
		
		
		//�־û�������Ϣ
		TaskModel task = new TaskModel();
		task.setHanderId(importTaskSeqId);
		task.setFileName(fileAlias);
		task.setSrcPath(_cpyObj);
		task.setState(TaskState.IMP_READY);
		task.setTaskType(taskType);
        srv.crateTask(task);

		return importTaskSeqId;
	}
	
	
	/**
	 * 
	 * Ϊ�����ļ�������������֤Ψһ���ļ���
	 * @param srcFile
	 * @return
	 */
	String reNameFileId(String srcFile){
		return System.currentTimeMillis()+"";
	}
	
	
	/**
	 * 
	 * @param alias      �ļ���ʾ������
	 * @param srcFile    �����ļ���ȫ·��
	 * @param subDirName ��λ���Ǹ����ļ�����
	 * @return
	 */
	void invokeImportListenner(final String importTaskSeqId,final  String alias,final  String srcFile,final String subDirName){
		//�������������Ϣ��¼,
		if(listenner!=null)
		   listenner.beforeImportData(importTaskSeqId, alias, srcFile);
		else{
		   //
			
		}
	}
	
	
	/**
	 * �����ļ����ͣ�װ���Ӧ�Ĵ�����
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
