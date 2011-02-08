package cn.elamzs.common.testsuits;

import cn.elamzs.common.eimport.core.ThreadDataImport;
import cn.elamzs.common.eimport.inter.EImporter;
import cn.elamzs.common.eimport.sample.TestDataProcess;
import cn.elamzs.common.eimport.sample.TestValidator;

public class ImportTestSuit {

	
	public static void testCreateTemplateFile() {
      
		
	}

	
	public static void main(String...args){
		TestValidator v = new TestValidator();
		TestDataProcess p = new TestDataProcess();
		EImporter importer = new ThreadDataImport(v,p,"test.xlsx");
		
	}
	
	
}
