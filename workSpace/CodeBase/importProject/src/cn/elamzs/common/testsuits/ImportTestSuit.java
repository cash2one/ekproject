package cn.elamzs.common.testsuits;

import cn.elamzs.common.eimport.core.ThreadDataImport;
import cn.elamzs.common.eimport.enums.FileType;
import cn.elamzs.common.eimport.inter.EImporter;
import cn.elamzs.common.eimport.sample.TestDataProcess;
import cn.elamzs.common.eimport.sample.TestValidator;

public class ImportTestSuit {

	public static void testCreateTemplateFile() throws Exception {
		TestValidator v = new TestValidator();
		TestDataProcess p = new TestDataProcess();
		EImporter importer = new ThreadDataImport(v, p);
		importer.downTemplate(FileType.EXCEL_XLS);
		importer.downTemplate(FileType.EXCEL_XLSX);
	}
	
	public static void testImportFile() throws Exception {
		TestValidator v = new TestValidator();
		TestDataProcess p = new TestDataProcess();
		EImporter importer = new ThreadDataImport(v, p);
		String dataFile = "d:/importfile/src/导入测试模版.xlsx";
		importer.importFile(dataFile,"别名","sub");
	}

	public static void main(String... args) throws Exception {
		testCreateTemplateFile();
		testImportFile();
	}

}
