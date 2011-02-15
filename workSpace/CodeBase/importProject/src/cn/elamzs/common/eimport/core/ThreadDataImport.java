package cn.elamzs.common.eimport.core;

import java.io.File;

import cn.elamzs.common.base.files.util.FileOperateUtil;
import cn.elamzs.common.eimport.enums.FileType;
import cn.elamzs.common.eimport.inter.DataProcess;
import cn.elamzs.common.eimport.inter.DataValidator;
import cn.elamzs.common.eimport.inter.EImporter;
import cn.elamzs.common.eimport.inter.ImportHandleListener;

/**
 * 
 * @author Ethan.Lam   2011-2-5
 * ������Ӧ���߳� ִ��  �ļ����ݵ��� ����
 * 
 * 
 */
public class ThreadDataImport implements EImporter {

	protected FileType fileType;

	DataValidator validator = null;  //������֤��
	
	DataProcess dataProcess = null;  //���ݴ�����
	
	FileHandler handler = null; //������ļ����������
	
	ImportHandleListener listenner = null; //�����¼�������
	
	
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
		
		//��ʼִ���ļ��������ݴ��������̴߳���
		Thread importThread = new Thread(handler);
        importThread.start(); 
        
		return null;
	}

	
    /**
     * ��ʽִ���ļ�����ǰ�Ĺ������
     * ��Դ�ļ��ı��ݡ����䵼������ID��������Χ�¼�������  ��	
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
		String _cpyObj =  ConfigControl.DIR_IMPORT_SRC+"/"+(subDirName!=null?subDirName:"")+"/"+_newFileName; 
		
		//copy�����ļ���ָ������Ŀ¼
		FileOperateUtil.copyFile(_src, new File(_cpyObj));
		
		//�����¼�����
		invokeImportListenner(importTaskSeqId,fileAlias,_cpyObj,subDirName);
		
		//�����ļ������ö�Ӧ�Ĵ�����
		appendDataHandler(importTaskSeqId,_cpyObj,subDirName);
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
