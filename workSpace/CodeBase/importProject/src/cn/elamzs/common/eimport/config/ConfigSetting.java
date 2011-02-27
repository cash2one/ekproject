package cn.elamzs.common.eimport.config;

import java.io.File;
import java.io.IOException;
/**
 * ”¶”√≈‰÷√
 * @author Ethan.Lam  2011-2-14
 *
 */
public class ConfigSetting {
	
    public  static String DIR_IMPORT_RESULT = "d:/importfile/result/";
	
	public  static String DIR_IMPORT_SRC = "d:/importfile/src/";
	
	public  static String DIR_IMPORT_TEMPLATE = "d:/importfile/template/";

	
	static{
		try {
			
			File _dir = new File(DIR_IMPORT_RESULT); 
			if(!_dir.exists())
			  _dir.mkdirs();
		 
			_dir = new File(DIR_IMPORT_SRC); 
			if(!_dir.exists())
		      _dir.mkdirs();
			 
			_dir = new File(DIR_IMPORT_TEMPLATE); 
			if(!_dir.exists())
			  _dir.mkdirs();
		
		} catch (Exception e) {
				e.printStackTrace();
		}
	}
	
}
