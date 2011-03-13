package cn.elamzs.common.eimport.core;

import java.io.File;
import java.util.Date;

import cn.elamzs.common.eimport.config.ConfigSetting;
import cn.elamzs.common.eimport.core.FileStorePersisService.UPADTE_OPERATION;
import cn.elamzs.common.eimport.enums.TaskState;
import cn.elamzs.common.eimport.inter.DataProcess;
import cn.elamzs.common.eimport.inter.DataValidator;
import cn.elamzs.common.eimport.inter.ImportHandleListener;
import cn.elamzs.common.eimport.item.TaskModel;

/**
 * 
 * @author Ethan.Lam   2011-2-5
 * ��������ļ������������ӿ�
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
	
	/**
	 * 
	 * @param importTaskId
	 * @param validator
	 * @param dataProcess
	 * @param listenner
	 * @param file
	 * @param storeSubDir
	 * @throws Exception
	 */
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
     * ���ݵ�����̿����߼�����
     */
    public void run(){
    	System.out.println("�����߳�ִ�������ļ�������� ,ID:"+importTaskId+".["+validator.getClass()+"] ʱ�䣺 "+new Date().toLocaleString());
    	
    	FileStorePersisService srv = new FileStorePersisService();
		TaskModel task = new TaskModel();
		task.setHanderId(importTaskId);
    	
		try {
    		if(validateDoc()){
	    		
    			//��ʼ��ȡ�ĵ��е����ݣ�ת��Ϊ�������ݣ�������ȡ��ÿ�����ݺ�� ����  DataProcess �ӿ��е� forEachRowValueProcess ������
				task.setRecordNum(loadDatas());
				
				//������ĵ����������ݵĶ�ȡ���̺󣬵���  DataProcess �ӿ��е� afterLoadAllRowsDataProcess ������
				dataPro.afterLoadAllRowsDataProcess();
				
				//��������ݵ���󣬲�����Ӧ����ĵ�
				String[][] _datas = dataPro.createImportResult();
				
				
				//�����ɵĽ�����ݴ�ŵ���Ӧ���ļ���
				String fileLocation = createImportResultDocument(_datas);
				
				if(listenner!=null){
					listenner.afterImportData(importTaskId,fileLocation);
				}
                				
				//���������ݳɹ�ִ�����ʱ,���¶�Ӧ�����״̬��Ϣ,
				task.setResultPath(fileLocation);
				task.setState(TaskState.IMP_SUC);
			
				task = null;
				srv = null;
				_datas = null;
				
    		}else{
    			System.out.println("����ģ��汾����ȷ��������������ģ�棡");
    			task.setState(TaskState.IMP_TEMPATE_NOMATCH);
    		}
    	} catch (Exception e) {
			e.printStackTrace();
			task.setState(TaskState.IMP_ERROR);
		}finally{
			dataElement.free();
			dataElement = null;
			dataPro = null;
		}
		
		try {
			//����������Ϣ
			srv.updateTask(task, UPADTE_OPERATION.IMP_FINISH_STATE);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	System.out.println("���ݵ�����̴�����ϣ�ID��"+importTaskId+"��["+validator.getClass()+"] ʱ�䣺"+new Date().toLocaleString());
    }
    
    
    
}
