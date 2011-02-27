package cn.elamzs.common.eimport.core;

import java.io.File;
import java.util.Date;

import cn.elamzs.common.eimport.config.ConfigSetting;
import cn.elamzs.common.eimport.inter.DataProcess;
import cn.elamzs.common.eimport.inter.DataValidator;
import cn.elamzs.common.eimport.inter.ImportHandleListener;

/**
 * 
 * @author Ethan.Lam   2011-2-5
 * �ļ���������
 * 
 *
 */
public abstract class AbstractFileHandler implements FileHandler{

	protected DataValidator validator = null; //������֤��
	
	protected DataProcess dataPro = null;

	protected File importFile = null;
	
	protected DataElement dataElement = null;
	
	protected String storeSubDir ="";
	
	protected String importTaskId;
	
	protected ImportHandleListener listenner=null;
	
    public AbstractFileHandler(String importTaskId,DataValidator validator,DataProcess dataProcess,ImportHandleListener listenner,File file,String storeSubDir) throws Exception{
		this.validator = validator;
		this.dataPro = dataProcess;
		this.importTaskId = importTaskId;
		importFile = file;
		this.listenner = listenner;
		this.storeSubDir = storeSubDir!=null?storeSubDir:"";
		dataElement = new DataElement(validator);
	}

    
	/**
	 * 
	 * ��֤�����ĵ��Ƿ��Ƿ���ģ��Ҫ��
	 * @return
	 * @throws Exception
	 */
	public boolean validateDoc()throws Exception{
		return dataElement.checkImpColumnsMatch(returnImpDocColumnsName());
	}
    
	/**
	 * ���ص������ļ��Ĵ��·��
	 * @return
	 */
	protected String resultSavDir(){
		File _dir = new File(ConfigSetting.DIR_IMPORT_RESULT+"/"+storeSubDir+"/");
		if(!_dir.exists())
			_dir.mkdirs();
		return ConfigSetting.DIR_IMPORT_RESULT+"/"+storeSubDir+"/";
	}
	
	
    /**
     * 
     * ���ݵ�����̿����߼�����
     */
    public void run(){
    	System.out.println("Create start run data import_pro_"+importTaskId+".["+validator.getClass()+"] At "+new Date().toLocaleString());
    	try {
    		if(validateDoc()){
	    		
    			//��ʼ��ȡ�ĵ��е����ݣ�ת��Ϊ�������ݣ�������ȡ��ÿ�����ݺ�� ����  DataProcess �ӿ��е� forEachRowValueProcess ������
				loadDatas();
				
				//������ĵ����������ݵĶ�ȡ���̺󣬵���  DataProcess �ӿ��е� afterLoadAllRowsDataProcess ������
				dataPro.afterLoadAllRowsDataProcess();
				
				//��������ݵ���󣬲�����Ӧ����ĵ�
				String[][] _datas = dataPro.createImportResult();
				
				//�����ɵĽ�����ݴ�ŵ���Ӧ���ļ���
				String fileLocation = createImportResultDocument(_datas);
	          
				if(listenner!=null){
					listenner.afterImportData(importTaskId,fileLocation);
				}
				_datas = null;
				
    		}else{
    			System.out.println("����ģ��汾����ȷ��������������ģ�棡");
    		}
    	} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dataElement.free();
			dataElement = null;
			dataPro = null;
		}
    	System.out.println("Import Thread import_pro_"+importTaskId+" Finished ["+validator.getClass()+"] At "+new Date().toLocaleString());
    }
    
    
    
}
