package cn.elamzs.common.base.files.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTDataValidation;

/**
 * 
 * @author Ethan.Lam 2011-2-5
 * 
 */
public class POIExcelUtil {

	/**
	 * �򿪹�����
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
	 * �򿪹�����
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
	 * �򿪹�����
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
	 * ����������
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
	 * �򿪱�
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
	 * ��ȡ����һ�е����ݵ�ֵ
	 * 
	 * @param sheet
	 * @param rowSeq
	 */
	public static String[] getRowValues(Workbook wb,Sheet sheet, int rowSeq) {
		String[] rowValues = null;
		try {
			// ���� row
			Row row = sheet.getRow(rowSeq);
			if (row != null) {
				int cells = row.getPhysicalNumberOfCells();
				rowValues = new String[cells];
				DecimalFormat df = new DecimalFormat("#");
				FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
				for (short c = 0; c < cells; c++) {
					Cell cell = row.getCell(c);
					if (cell != null) {
						String value = null;
						switch (cell.getCellType()) {
						
								case Cell.CELL_TYPE_FORMULA:
									{
										  switch (evaluator.evaluateInCell(cell).getCellType()) {
									        case Cell.CELL_TYPE_BOOLEAN:
									            value= cell.getBooleanCellValue()+"";
									            break;
									        case Cell.CELL_TYPE_NUMERIC:
									        	value=cell.getNumericCellValue()+"";
									            break;
									        case Cell.CELL_TYPE_STRING:
									        	value=cell.getStringCellValue()+"";
									            break;
									        case Cell.CELL_TYPE_BLANK:
									            break;
									        case Cell.CELL_TYPE_ERROR:
									        	value=cell.getErrorCellValue()+"";
									            break;
									        // CELL_TYPE_FORMULA will never occur
									        case Cell.CELL_TYPE_FORMULA:
									            break;
									    }
									}
									break;
		
								case Cell.CELL_TYPE_NUMERIC:
									if (HSSFDateUtil.isCellDateFormatted(cell)) {
										value = cell.getDateCellValue() + "";
									} else {
										 //����������
									     if(cell.getNumericCellValue() == Double.parseDouble(df.format(cell.getNumericCellValue())))
									    	 value = df.format(cell.getNumericCellValue());
							             else 
							            	 //������С��
							            	 value = String.valueOf(new BigDecimal(cell.getNumericCellValue()).setScale((short)2, BigDecimal.ROUND_HALF_UP).doubleValue());
//										value = df.format(cell.getNumericCellValue()) + "";
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
	 * ����Excel ��д������
	 * @param fileName
	 * @param columnsName
	 * @param datas
	 * @param width
	 * @param flag
	 */
	public static void writeDataToExcel(String fileName,String[] columnsName,String[][] datas,int[] width,boolean flag,String[]columnsComment){
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
			
			//�����п�
			for(int index=0;index<width.length;index++)
				sheet.setColumnWidth(index, width[index]*100);
			
			//д�б�ͷ
		    newRow = sheet.createRow(0);
		    String comment = "";
			for(int cellIndex=0;cellIndex<columnsNum;cellIndex++){
				comment = columnsComment!=null&&columnsComment.length>cellIndex?columnsComment[cellIndex]:"";
				createCell(wb,sheet,newRow,cellIndex,columnsName[cellIndex],null,CellStyle.ALIGN_CENTER,Cell.CELL_TYPE_STRING,true,comment);
			}	
			
			
			//д����
			if(datas!=null){
				columnsNum = datas[0].length;
				for(int rowSeq=1;rowSeq<totalRowNum;rowSeq++){
					newRow = sheet.createRow(rowSeq);
					for(int cellIndex=0;cellIndex<columnsNum;cellIndex++){
						createCell(wb,sheet,newRow,cellIndex,datas[rowSeq-1][cellIndex],null,CellStyle.ALIGN_CENTER,Cell.CELL_TYPE_STRING,false,null);
					}	
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
	 * ����Excel ��д������
	 * @param fileName
	 * @param columnsColor
	 * @param columnsName
	 * @param datas
	 * @param width
	 * @param flag
	 */
	public static void writeDataToExcel(String fileName,String[] columnsName,String[][] datas,int[] width,IndexedColors[] columnsColor,boolean flag,String[]columnsComment){
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
			
			//�����п�
			for(int index=0;index<width.length;index++){
				sheet.setColumnWidth(index, width[index]*100);
				
			}
			
			//���������֤����
			addDataValidator(wb,sheet);
			
			//д�б�ͷ
		    newRow = sheet.createRow(0);
		    String comment = "";
			for(int cellIndex=0;cellIndex<columnsNum;cellIndex++){
				comment = columnsComment!=null&&columnsComment.length>cellIndex?columnsComment[cellIndex]:"";
				createCell(wb,sheet,newRow,cellIndex,columnsName[cellIndex],columnsColor[cellIndex],CellStyle.ALIGN_CENTER,Cell.CELL_TYPE_STRING,true,columnsComment[cellIndex]);
			}	
			
			//д����
			if(datas!=null){
				columnsNum = datas[0].length;
				for(int rowSeq=1;rowSeq<totalRowNum;rowSeq++){
					newRow = sheet.createRow(rowSeq);
					for(int cellIndex=0;cellIndex<columnsNum;cellIndex++){
					    createCell(wb,sheet,newRow,cellIndex,datas[rowSeq-1][cellIndex],null,CellStyle.ALIGN_CENTER,Cell.CELL_TYPE_STRING,false,null);
					}	
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
	 * ����ĵ�������֤
	 * @param wb
	 * @param sheet
	 */
	public static void addDataValidator(Workbook wb,Sheet sheet){

		//����� ������֤
		DataValidation dataValidation = null;
		if(wb instanceof HSSFWorkbook){
			CellRangeAddressList addressList = new CellRangeAddressList(
				      1, -1, 3,3);
			DVConstraint dvConstraint = DVConstraint.createExplicitListConstraint(
				      new String[]{"10", "20", "30"});
		    dataValidation = new HSSFDataValidation(addressList, dvConstraint);
			dataValidation.setSuppressDropDownArrow(true);
			sheet.addValidationData(dataValidation);
		}else if(wb instanceof XSSFWorkbook){
	  
	    	
	    }
		
	}
	
	/**
	 * ����һ��cell
	 * @param wb
	 * @param sheet
	 * @param newRow
	 * @param cellIndex
	 * @param cellValue
	 * @param color
	 * @param cellAlignment
	 * @param cellType
	 * @param isUnderLine
	 * @param commentTip
	 */
	public static void createCell(Workbook wb,Sheet sheet,Row newRow,int cellIndex,String cellValue,IndexedColors color,short cellAlignment,int cellType,boolean isUnderLine,String commentTip){
		
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
		
		
		//�����ע
		if(commentTip==null||"".endsWith(commentTip))
			return;
		
		Drawing drawing = sheet.createDrawingPatriarch();
	    ClientAnchor anchor = creationHelper.createClientAnchor();
	    anchor.setCol1(1);
	    anchor.setRow1(1);
	    anchor.setCol2(3);
	    anchor.setRow2(8);
	    Comment comment = drawing.createCellComment(anchor);
	    richTextStr = creationHelper.createRichTextString(commentTip);
	    comment.setString(richTextStr);
//	    comment.setAuthor("Apache POI");
	    newCell.setCellComment(comment);
		
	}
	
	

	/**
	 * �����ȡExcel
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
					printRowValues(getRowValues(wb,sheet, r));
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
		//��ȡ
		File f = new File("d:/test.xlsx");
		FileInputStream is = new FileInputStream(f);
		System.out.println(f.getName());
		testReadExcel(openWorkBook(is, false));
        //�½�
		
		writeDataToExcel("d:/testw.xlsx",new String[]{"A","B"},new String[][]{{"a_1","b_1"},{"a_2","b_2"},{"a_3","b_3"}},new int[]{50,50},false,null);
	}
}
