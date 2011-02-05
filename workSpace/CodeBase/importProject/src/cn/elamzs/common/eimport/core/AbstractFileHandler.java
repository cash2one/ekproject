package cn.elamzs.common.eimport.core;

import java.io.File;

import cn.elamzs.common.eimport.inter.DataProcess;
import cn.elamzs.common.eimport.inter.DataValidator;

/**
 * 
 * @author Ethan.Lam   2011-2-5
 *
 */
public abstract class AbstractFileHandler implements FileHandler{

	protected DataValidator validator = null; //������֤��
	
	protected DataProcess dataPro = null;
	
    public AbstractFileHandler(DataValidator validator,DataProcess dataProcess,File file){
		this.validator = validator;
		this.dataPro = dataProcess;
	}
	
    public void run(){
    	System.out.println("Create A new Import Thread and start run data import_pro. ");
    	try {
    		
    		//��ʼ��ȡ�ĵ��е����ݣ�ת��Ϊ�������ݣ�������ȡ��ÿ�����ݺ�� ����  DataProcess �ӿ��е� currentRowValueProcess ������
			loadDatas();
			
			//������ĵ����������ݵĶ�ȡ���̺󣬵���  DataProcess �ӿ��е� afterLoadAllRowsDataProcess ������
			dataPro.afterLoadAllRowsDataProcess();
			
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	System.out.println("Create A new Import Thread.....");
    }
    
}
