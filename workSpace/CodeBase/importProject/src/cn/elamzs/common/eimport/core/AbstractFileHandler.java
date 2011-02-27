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
 * 文件解析操作
 * 
 *
 */
public abstract class AbstractFileHandler implements FileHandler{

	protected DataValidator validator = null; //数据验证器
	
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
	 * 验证导入文档是否是符合模版要求
	 * @return
	 * @throws Exception
	 */
	public boolean validateDoc()throws Exception{
		return dataElement.checkImpColumnsMatch(returnImpDocColumnsName());
	}
    
	/**
	 * 返回导入结果文件的存放路径
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
     * 数据导入过程控制逻辑主体
     */
    public void run(){
    	System.out.println("Create start run data import_pro_"+importTaskId+".["+validator.getClass()+"] At "+new Date().toLocaleString());
    	try {
    		if(validateDoc()){
	    		
    			//开始读取文档中的数据，转换为行列数据，并当读取完每行数据后的 调用  DataProcess 接口中的 forEachRowValueProcess 方法。
				loadDatas();
				
				//当完成文档中所有数据的读取过程后，调用  DataProcess 接口中的 afterLoadAllRowsDataProcess 方法。
				dataPro.afterLoadAllRowsDataProcess();
				
				//当完成数据导入后，产生对应结果文档
				String[][] _datas = dataPro.createImportResult();
				
				//把生成的结果数据存放到对应的文件中
				String fileLocation = createImportResultDocument(_datas);
	          
				if(listenner!=null){
					listenner.afterImportData(importTaskId,fileLocation);
				}
				_datas = null;
				
    		}else{
    			System.out.println("导入模版版本不正确，请检查或更新最新模版！");
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
