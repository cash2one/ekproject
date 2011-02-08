package cn.elamzs.common.base.files.util;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
	public static String[] getRowValues(Sheet sheet, int rowSeq) {
		String[] rowValues = null;
		try {
			// ���� row
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
	 * ����Excel ��д������
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
			Cell newCell = null;
			
			//�����п�
			for(int index=0;index<width.length;index++)
				sheet.setColumnWidth(index, width[index]*100);
			
			
			//д�б�ͷ
		    newRow = sheet.createRow(0);
			for(int cellIndex=0;cellIndex<columnsNum;cellIndex++){
				newCell= newRow.createCell(cellIndex);
				newCell.setCellType(Cell.CELL_TYPE_STRING);
				newCell.setCellValue(columnsName[cellIndex]);
				setFontColor(0,0,Color.red,wb,wb.getSheetAt(0));
			}	
			
			
			//д����
			if(datas!=null)
			for(int rowSeq=1;rowSeq<totalRowNum;rowSeq++){
				newRow = sheet.createRow(rowSeq);
				for(int cellIndex=0;cellIndex<columnsNum;cellIndex++){
					 newCell= newRow.createCell(cellIndex);
					 newCell.setCellType(Cell.CELL_TYPE_STRING);
					 newCell.setCellValue(datas[rowSeq-1][cellIndex]);
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
					printRowValues(getRowValues(sheet, r));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	   /**
	    * ����������ɫ
	    * @param rowIndex
	    * @param colIndex
	    * @param color
	    * @param workbook
	    * @param sheet
	    */
	  public static void setFontColor(int rowIndex, int colIndex,Color color,Workbook workbook,  
	            Sheet sheet) {  
	        if (sheet == null)  
	            return;  
	        if (sheet.getLastRowNum() < rowIndex) {  
	            throw new IllegalStateException(  
	                    "rowIndex over maxRowIndex!!!rowIndex:" + rowIndex  
	                            + "  maxRowIndex:" + sheet.getLastRowNum());  
	        }  
	        Row row = sheet.getRow(rowIndex);  
	        if (row != null) {  
	            if (row.getPhysicalNumberOfCells() < colIndex) {  
	                // throw new  
	                // IllegalStateException("colIndex over maxColIndex!!!colIndex:"+colIndex+"  maxColIndex:"+row.getPhysicalNumberOfCells());  
	            }  
	            Cell cell = row.getCell(colIndex);  
	            if (cell != null) {  
	                Font font = workbook.createFont();  
	                if(font instanceof HSSFFont )
	                  font.setColor(getColor(color,(HSSFWorkbook) workbook).getIndex());  
	                else if(font instanceof XSSFFont){
	                  font.setColor((short) color.getRGB());
	                }
	                RichTextString ts=cell.getRichStringCellValue();  
	                ts.applyFont(font);  
	                cell.setCellValue(ts);  
	            }  
	        }  
	    }  
	  
	  
	  /** 
	     * �@ȡָ���ɫ������HSSF�ɫ�����ԓHSSF�ɫ�����ڣ��t�����cָ���ɫ��������HSSF�ɫ 
	     *  
	     * @param color 
	     *            �ɫ 
	     * @return HSSF�ɫ 
	     */  
	    public static HSSFColor getColor(Color color,HSSFWorkbook workbook) {  
	        HSSFPalette palette = workbook.getCustomPalette();  
	        HSSFColor hssfColor = palette.findColor((byte) color.getRed(),  
	                (byte) color.getGreen(), (byte) color.getBlue());  
	        if (hssfColor == null) {  
	            hssfColor = palette.addColor((byte) color.getRed(), (byte) color  
	                    .getGreen(), (byte) color.getBlue());  
	        }  
	        return hssfColor;  
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
		
		writeDataToExcel("d:/testw.xlsx",new String[]{"A","B"},new String[][]{{"a_1","b_1"},{"a_2","b_2"},{"a_3","b_3"}},new int[]{50,50},false);
	}
}
