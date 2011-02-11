package cn.elamzs.common.eimport.core;

import java.io.File;

import cn.elamzs.common.eimport.enums.FileType;
import cn.elamzs.common.eimport.inter.DataProcess;
import cn.elamzs.common.eimport.inter.DataValidator;
import cn.elamzs.common.eimport.inter.EImporter;

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
	
	FileHandler handler = null;
	
	public ThreadDataImport(DataValidator validator,DataProcess dataProcess){
	        this.validator = validator;
	        this.dataProcess = dataProcess;
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
	public File downTemplate(FileType type)
			throws Exception {
		// TODO Auto-generated method stub
		Template template = new Template(validator);
		return template.createImpTemplateDoc(type);
	}
	
	@Override
	public String importFile(String dataFile,String storeSubDir) throws Exception {
		// TODO Auto-generated method stub
		
		//�����ļ������ö�Ӧ�Ĵ�����
		appendDataHandler(dataFile,storeSubDir);
		
		//��ʼִ���ļ��������ݴ��������̴߳���
		Thread importThread = new Thread(handler);
        importThread.start(); 
        
		return null;
	}

	
	/**
	 * �����ļ����ͣ�װ���Ӧ�Ĵ�����
	 * @param fileName
	 * @throws Exception 
	 */
	void appendDataHandler(String fileName,String storeSubDir) throws Exception{
		File file = new File(fileName);
		
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		for(FileType t:FileType.values()){
			if(t.suffix().equals(suffix)){
                this.fileType = t;
			}
		}
		
		//
		if(FileType.EXCEL_XLS.equals(fileType)||FileType.EXCEL_XLSX.equals(fileType))
			this.handler = (FileHandler) new ExcelImportHandler(validator,dataProcess,file,storeSubDir,FileType.EXCEL_XLS.equals(fileType)?0:1);
		else if(FileType.TXT.equals(fileType))
			this.handler = new TxtImportHandler(validator,dataProcess,file,storeSubDir);
		
	}
	
	
	
}
