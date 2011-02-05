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

	protected DataValidator validator = null; //数据验证器
	
	protected DataProcess dataPro = null;
	
    public AbstractFileHandler(DataValidator validator,DataProcess dataProcess,File file){
		this.validator = validator;
		this.dataPro = dataProcess;
	}
	
    public void run(){
    	System.out.println("Create A new Import Thread and start run data import_pro. ");
    	try {
    		
    		//开始读取文档中的数据，转换为行列数据，并当读取完每行数据后的 调用  DataProcess 接口中的 currentRowValueProcess 方法。
			loadDatas();
			
			//当完成文档中所有数据的读取过程后，调用  DataProcess 接口中的 afterLoadAllRowsDataProcess 方法。
			dataPro.afterLoadAllRowsDataProcess();
			
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	System.out.println("Create A new Import Thread.....");
    }
    
}
