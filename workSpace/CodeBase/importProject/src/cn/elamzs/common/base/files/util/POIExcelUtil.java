package cn.elamzs.common.base.files.util;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
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
			wb = new HSSFWorkbook();
		} else {// 2007
			wb = new XSSFWorkbook();
		}
	    FileOutputStream fileOut = new FileOutputStream(fileName);
	    wb.write(fileOut);
	    fileOut.close();
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
	 * 创建Excel 并写入数据
	 * @param fileName
	 * @param columnsName
	 * @param datas
	 * @param width
	 * @param flag
	 */
	public static void writeDataToExcel(String fileName,String[] columnsName,String[][] datas,int[] width,boolean flag){
		Workbook wb = null;
		try{
			if (flag) {// 2003
				wb = new HSSFWorkbook();
			} else {// 2007
				wb = new XSSFWorkbook();
			}
			
			int columnsNum = columnsName.length;
			int totalRowNum = datas!=null?datas.length+1:1;
			
			Sheet sheet = wb.createSheet();
			Row newRow = null;
			
			//设置列宽
			for(int index=0;index<width.length;index++)
				sheet.setColumnWidth(index, width[index]*100);
			
			//写列表头
		    newRow = sheet.createRow(0);
			for(int cellIndex=0;cellIndex<columnsNum;cellIndex++){
				createCell(wb,newRow,cellIndex,columnsName[cellIndex],null,CellStyle.ALIGN_CENTER,Cell.CELL_TYPE_STRING,true);
			}	
			
			
			//写数据
			if(datas!=null)
			for(int rowSeq=1;rowSeq<totalRowNum;rowSeq++){
				newRow = sheet.createRow(rowSeq);
				for(int cellIndex=0;cellIndex<columnsNum;cellIndex++){
					createCell(wb,newRow,cellIndex,datas[rowSeq-1][cellIndex],null,CellStyle.ALIGN_CENTER,Cell.CELL_TYPE_STRING,false);
				}	
			}
			
			FileOutputStream fileOut = new FileOutputStream(fileName);
		    wb.write(fileOut);
			fileOut.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 
	 * 创建Excel 并写入数据
	 * @param fileName
	 * @param columnsColor
	 * @param columnsName
	 * @param datas
	 * @param width
	 * @param flag
	 */
	public static void writeDataToExcel(String fileName,String[] columnsName,String[][] datas,int[] width,IndexedColors[] columnsColor,boolean flag){
		Workbook wb = null;
		try{
			if (flag) {// 2003
				wb = new HSSFWorkbook();
			} else {// 2007
				wb = new XSSFWorkbook();
			}
			
			int columnsNum = columnsName.length;
			int totalRowNum = datas!=null?datas.length+1:1;
			
			Sheet sheet = wb.createSheet();
			
			Row newRow = null;
			
			//设置列宽
			for(int index=0;index<width.length;index++)
				sheet.setColumnWidth(index, width[index]*100);
			
			//写列表头
		    newRow = sheet.createRow(0);
			for(int cellIndex=0;cellIndex<columnsNum;cellIndex++){
				createCell(wb,newRow,cellIndex,columnsName[cellIndex],columnsColor[cellIndex],CellStyle.ALIGN_CENTER,Cell.CELL_TYPE_STRING,true);
			}	
			
			//写数据
			if(datas!=null)
			for(int rowSeq=1;rowSeq<totalRowNum;rowSeq++){
				newRow = sheet.createRow(rowSeq);
				for(int cellIndex=0;cellIndex<columnsNum;cellIndex++){
				    createCell(wb,newRow,cellIndex,datas[rowSeq-1][cellIndex],null,CellStyle.ALIGN_CENTER,Cell.CELL_TYPE_STRING,false);
				}	
			}
			
			FileOutputStream fileOut = new FileOutputStream(fileName);
		    wb.write(fileOut);
			fileOut.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 创建一个cell
	 * @param wb
	 * @param newRow
	 * @param cellIndex
	 * @param cellValue
	 * @return
	 */
	public static void createCell(Workbook wb,Row newRow,int cellIndex,String cellValue,IndexedColors color,short cellAlignment,int cellType,boolean isUnderLine){
		
		CreationHelper creationHelper = wb.getCreationHelper();
		RichTextString richTextStr = creationHelper.createRichTextString(cellValue);
		
		Cell newCell= newRow.createCell(cellIndex);
		CellStyle style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
	    newCell.setCellStyle(style);
	    newCell.setCellType(Cell.CELL_TYPE_STRING);
	    
	    Font font = wb.createFont();
		font.setItalic(true);
		if(isUnderLine)
		  font.setUnderline((byte) 1);
		
		if(color!=null)
			font.setColor(color.getIndex());
		else
			font.setColor(IndexedColors.BLACK.getIndex());
		
		richTextStr.applyFont(font);
		newCell.setCellValue(richTextStr);
		
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
		//读取
		File f = new File("d:/test.xlsx");
		FileInputStream is = new FileInputStream(f);
		System.out.println(f.getName());
		testReadExcel(openWorkBook(is, false));
        //新建
		
		writeDataToExcel("d:/testw.xlsx",new String[]{"A","B"},new String[][]{{"a_1","b_1"},{"a_2","b_2"},{"a_3","b_3"}},new int[]{50,50},false);
	}
}
