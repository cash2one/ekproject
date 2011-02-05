package cn.elamzs.common.eimport.core;

import java.io.File;

import cn.elamzs.common.eimport.inter.DataValidator;

public abstract class AbstractFileHandler implements FileHandler{

	protected DataValidator validator = null; //数据验证器
	
    public AbstractFileHandler(DataValidator validator,File file){
		this.validator = validator;
	}
	
    public void run(){
    	System.out.println("Create A new Import Thread and start run data import_pro. ");
    	try {
			loadDatas();
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	System.out.println("Create A new Import Thread.....");
    }
    
}
