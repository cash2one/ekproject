package cn.elamzs.common.base.files.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * @author Ethan.Lam 2011-2-5
 * 
 */
public class POIExcelUtil {

	/**
	 * 打开工作薄
	 * 
	 * @param fileName
	 * @param flag
	 *            false:2007 | true:2003
	 * @return
	 * @throws Exception
	 */
	public static Workbook openWorkBook(String fileName, boolean flag)
			throws Exception {
		Workbook wb = null;
		if (flag) {// 2003
			File f = new File(fileName);
			FileInputStream is = new FileInputStream(f);
			POIFSFileSystem fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
			is.close();
		} else {// 2007
			wb = new XSSFWorkbook(fileName);
		}
		return wb;
	}
	
	/**
	 * 打开工作薄
	 * 
	 * @param fileName
	 * @param flag
	 *            false:2007 | true:2003
	 * @return
	 * @throws Exception
	 */
	public static Workbook openWorkBook(File file, boolean flag)
			throws Exception {
		Workbook wb = null;
		if (flag) {// 2003
			FileInputStream is = new FileInputStream(file);
			POIFSFileSystem fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
			is.close();
		} else {// 2007
			FileInputStream is = new FileInputStream(file);
			wb = new XSSFWorkbook(is);
		}
		return wb;
	}

	/**
	 * 
	 * 打开工作薄
	 * 
	 * @param is
	 * @param false:2007 | true:2003
	 * @return
	 * @throws Exception
	 */
	public static Workbook openWorkBook(InputStream is, boolean flag)
			throws Exception {
		Workbook wb = null;
		if (flag) {// 2003
			wb = new HSSFWorkbook(is);
		} else {// 2007
			wb = new XSSFWorkbook(is);
		}
		return wb;
	}

	/**
	 * 
	 * 创建工作薄
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static Workbook createWorkBook(String fileName, boolean flag)
			throws Exception {
		Workbook wb = null;
		if (flag) {// 2003
			File f = new File(fileName);
			if (!f.exists())
				f.createNewFile();
			FileInputStream is = new FileInputStream(f);
			POIFSFileSystem fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
			is.close();
		} else {// 2007
			wb = new XSSFWorkbook(fileName);
		}
		return wb;
	}

	/**
	 * 
	 * 打开表
	 * 
	 * @param wb
	 * @param sheet
	 * @return
	 */
	public static Sheet openSheet(Workbook wb, int sheet) {
		int totalNum = wb.getNumberOfSheets();
		if (sheet < totalNum)
			return wb.getSheetAt(sheet);
		return null;
	}

	/**
	 * 
	 * 读取其中一行的数据的值
	 * 
	 * @param sheet
	 * @param rowSeq
	 */
	public static String[] getRowValues(Sheet sheet, int rowSeq) {
		String[] rowValues = null;
		try {
			// 定义 row
			Row row = sheet.getRow(rowSeq);
			if (row != null) {
				int cells = row.getPhysicalNumberOfCells();
				rowValues = new String[cells];
				for (short c = 0; c < cells; c++) {
					Cell cell = row.getCell(c);
					if (cell != null) {
						String value = null;

						switch (cell.getCellType()) {

						case Cell.CELL_TYPE_FORMULA:
							value = cell.getCellFormula();
							break;

						case Cell.CELL_TYPE_NUMERIC:
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								value = cell.getDateCellValue() + "";
							} else {
								value = cell.getNumericCellValue() + "";
							}

							break;

						case Cell.CELL_TYPE_STRING:
							value = cell.getStringCellValue();
							break;

						case Cell.CELL_TYPE_BOOLEAN:
							value = cell.getBooleanCellValue() + "";
							break;
						default:
						}
						rowValues[c] = value;
					}
				}
			}
			return rowValues;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {

		}
	}

	/**
	 * @param rowValues
	 */
	static void printRowValues(String[] rowValues){
		if(rowValues!=null){
			for(String value:rowValues)
				System.out.print(value+" | ");
			System.out.println();
		}
	}
	
	/**
	 * 具体读取Excel
	 * @param wb
	 * @throws Exception
	 */
	public static void testReadExcel(Workbook wb) throws Exception {
		try {
			for (int k = 0; k < wb.getNumberOfSheets(); k++) {
				// sheet
				Sheet sheet = openSheet(wb, k);
				int rows = sheet.getPhysicalNumberOfRows();
				String[] rowValues = null;
				for (int r = 0; r < rows; r++)
					printRowValues(getRowValues(sheet, r));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		File f = new File("d:/test.xlsx");
		FileInputStream is = new FileInputStream(f);
		System.out.println(f.getName());
		testReadExcel(openWorkBook(is, false));

	}
}
