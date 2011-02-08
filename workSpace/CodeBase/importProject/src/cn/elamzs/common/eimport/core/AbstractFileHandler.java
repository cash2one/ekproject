package cn.elamzs.common.eimport.core;

import java.io.File;
import java.util.Date;

import cn.elamzs.common.eimport.inter.DataProcess;
import cn.elamzs.common.eimport.inter.DataValidator;

/**
 * 
 * @author Ethan.Lam   2011-2-5
 * 文件解析操作
 * 
 *
 */
public abstract class AbstractFileHandler implements FileHandler{

	protected DataValidator validator = null; //数据验证器
	
	protected DataProcess dataPro = null;

	protected File importFile = null;
	
	protected DataElement dataElement = null;
	
	
    public AbstractFileHandler(DataValidator validator,DataProcess dataProcess,File file) throws Exception{
		this.validator = validator;
		this.dataPro = dataProcess;
		importFile = file;
		dataElement = new DataElement(validator);
	}

    
	/**
	 * 
	 * 验证导入文档是否是符合模版要求
	 * @return
	 * @throws Exception
	 */
	public boolean validateDoc()throws Exception{
		return dataElement.checkImpColumnsMatch(returnImpDocColumnsName());
	}
    
	
    /**
     * 
     * 数据导入过程控制逻辑主体
     */
    public void run(){
    	System.out.println("Create start run data import_pro.["+validator.getClass()+"] At "+new Date().toLocaleString());
    	try {
    		if(validateDoc()){
	    		
    			//开始读取文档中的数据，转换为行列数据，并当读取完每行数据后的 调用  DataProcess 接口中的 forEachRowValueProcess 方法。
				loadDatas();
				
				//当完成文档中所有数据的读取过程后，调用  DataProcess 接口中的 afterLoadAllRowsDataProcess 方法。
				dataPro.afterLoadAllRowsDataProcess();
				
				//当完成数据导入后，产生对应结果文档
				String[][] _datas = dataPro.createImportResult();
				
				//把生成的结果数据存放到对应的文件中
				createImportResultDocument(_datas);
	          
				_datas = null;
    		}
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	System.out.println("Import Thread  Finished ["+validator.getClass()+"] At "+new Date().toLocaleString());
    }
    
    
    
}
