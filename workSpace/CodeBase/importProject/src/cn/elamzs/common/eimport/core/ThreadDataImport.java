package cn.elamzs.common.eimport.core;

import java.io.File;

import cn.elamzs.common.eimport.enums.FileType;
import cn.elamzs.common.eimport.inter.DataProcess;
import cn.elamzs.common.eimport.inter.DataValidator;
import cn.elamzs.common.eimport.inter.EImporter;
import cn.elamzs.common.eimport.inter.Template;

/**
 * 
 * @author Ethan.Lam   2011-2-5
 * ������Ӧ���߳� ִ��  �ļ����ݵ��� ����
 * 
 * 
 */
public class ThreadDataImport implements EImporter {

	protected FileType fileType;

	protected String fileName;
	
	DataValidator validator = null;  //������֤��
	
	DataProcess dataProcess = null;  //���ݴ�����
	
	FileHandler handler = null;
	
	public ThreadDataImport(DataValidator validator,String fileName){
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
	public File downTemplate(Class<? extends DataValidator> validator,FileType type)
			throws Exception {
		// TODO Auto-generated method stub
		Object obj = validator.getClass().newInstance();
		Template template = new Template((DataValidator) obj);
		return template.createImpTemplateDoc(type);
	}
	
	@Override
	public String importFile(String dataFile) throws Exception {
		// TODO Auto-generated method stub
		
		//�����ļ������ö�Ӧ�Ĵ�����
		appendDataHandler(dataFile);
		
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
		
	}
	
	
	
}
