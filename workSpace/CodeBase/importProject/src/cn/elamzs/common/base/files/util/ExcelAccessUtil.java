package cn.elamzs.common.base.files.util;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
  
/** 
 * @author jonsion 
 *  
 */  
public class ExcelAccessUtil {  
    public static HSSFWorkbook getWorkBook(String filePath)  
            throws FileNotFoundException, IOException {  
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filePath));  
        return workbook;  
    }  
  
    public static HSSFSheet getSheet(HSSFWorkbook workbook, String sheetName) {  
        if (workbook == null)  
            return null;  
        HSSFSheet sheet = workbook.getSheet(sheetName);  
        return sheet;  
    }  
  
    public static HSSFSheet getSheet(HSSFWorkbook workbook, int sheetIndex) {  
        if (workbook == null)  
            return null;  
        HSSFSheet sheet = workbook.getSheetAt(sheetIndex);  
        return sheet;  
    }  
  
//    public static void outputFileToPage(HSSFWorkbook workbook, String encode,  
//            HttpServletResponse response) throws IOException {  
//        outputFileToPage(workbook, encode, new SimpleDateFormat(  
//                "yyyyMMddHHmmss").format(new Date()).toString(), response);  
//    }  
//  
//    public static void outputFileToPage(HSSFWorkbook workbook, String encode,  
//            String fileName, HttpServletResponse response) throws IOException {  
//        response.setContentType("application/vnd.ms-excel");  
//        response.setCharacterEncoding(encode);  
//        response.setHeader("Content-Disposition", "attachment; filename=\""  
//                + fileName + ".xls" + "\"");  
//        OutputStream outputExcelStream = response.getOutputStream();  
//        workbook.write(outputExcelStream);  
//        outputExcelStream.flush();  
//        outputExcelStream.close();  
//    }  
//  
//    public static void outputFileToPage(HSSFWorkbook workbook,  
//            HttpServletResponse response, String fileName) throws IOException {  
//        outputFileToPage(workbook, "shift-jis", fileName, response);  
//    }  
//  
//    public static void outputFileToPage(HSSFWorkbook workbook,  
//            HttpServletResponse response) throws IOException {  
//        outputFileToPage(workbook, "shift-jis", new SimpleDateFormat(  
//                "yyyyMMddHHmmss").format(new Date()).toString(), response);  
//    }  
  
    public static void fillData(int rowIndex, int colIndex, String value,  
            HSSFSheet sheet) {  
        if (sheet == null)  
            return;  
        if (sheet.getLastRowNum() < rowIndex) {  
            throw new IllegalStateException(  
                    "rowIndex over maxRowIndex!!!rowIndex:" + rowIndex  
                            + "  maxRowIndex:" + sheet.getLastRowNum());  
        }  
        HSSFRow row = sheet.getRow(rowIndex);  
        if (row != null) {  
            if (row.getPhysicalNumberOfCells() < colIndex) {  
                // throw new  
                // IllegalStateException("colIndex over maxColIndex!!!colIndex:"+colIndex+"  maxColIndex:"+row.getPhysicalNumberOfCells());  
            }  
            HSSFCell cell = row.getCell(colIndex);  
            if (cell != null) {  
//                cell.setCellValue(StringUtil.trim(value));  
            }  
        }  
    }  
  
    public static void fillData(int rowIndex, int colIndex, boolean value,  
            HSSFSheet sheet) {  
        if (sheet == null)  
            return;  
        if (sheet.getLastRowNum() < rowIndex) {  
            throw new IllegalStateException(  
                    "rowIndex over maxRowIndex!!!rowIndex:" + rowIndex  
                            + "  maxRowIndex:" + sheet.getLastRowNum());  
        }  
        HSSFRow row = sheet.getRow(rowIndex);  
        if (row != null) {  
            if (row.getPhysicalNumberOfCells() < colIndex) {  
                // throw new  
                // IllegalStateException("colIndex over maxColIndex!!!colIndex:"+colIndex+"  maxColIndex:"+row.getPhysicalNumberOfCells());  
            }  
            HSSFCell cell = row.getCell(colIndex);  
            if (cell != null) {  
                cell.setCellValue(value);  
            }  
        }  
    }  
  
    public static void fillData(int rowIndex, int colIndex, Date value,  
            HSSFSheet sheet) {  
        if (sheet == null)  
            return;  
        if (sheet.getLastRowNum() < rowIndex) {  
            throw new IllegalStateException(  
                    "rowIndex over maxRowIndex!!!rowIndex:" + rowIndex  
                            + "  maxRowIndex:" + sheet.getLastRowNum());  
        }  
        HSSFRow row = sheet.getRow(rowIndex);  
        if (row != null) {  
            if (row.getPhysicalNumberOfCells() < colIndex) {  
                // throw new  
                // IllegalStateException("colIndex over maxColIndex!!!colIndex:"+colIndex+"  maxColIndex:"+row.getPhysicalNumberOfCells());  
            }  
            HSSFCell cell = row.getCell(colIndex);  
            if (cell != null) {  
                cell.setCellValue(value);  
            }  
        }  
    }  
  
    public static void fillData(int rowIndex, int colIndex, double value,  
            HSSFSheet sheet) {  
        if (sheet == null)  
            return;  
        if (sheet.getLastRowNum() < rowIndex) {  
            throw new IllegalStateException(  
                    "rowIndex over maxRowIndex!!!rowIndex:" + rowIndex  
                            + "  maxRowIndex:" + sheet.getLastRowNum());  
        }  
        HSSFRow row = sheet.getRow(rowIndex);  
        if (row != null) {  
            if (row.getPhysicalNumberOfCells() < colIndex) {  
                // throw new  
                // IllegalStateException("colIndex over maxColIndex!!!colIndex:"+colIndex+"  maxColIndex:"+row.getPhysicalNumberOfCells());  
            }  
            HSSFCell cell = row.getCell(colIndex);  
            if (cell != null) {  
                cell.setCellValue(value);  
            }  
        }  
    }  
      
    public static void setFontColor(int rowIndex, int colIndex, Color color,HSSFWorkbook workbook,  
            HSSFSheet sheet) {  
        if (sheet == null)  
            return;  
        if (sheet.getLastRowNum() < rowIndex) {  
            throw new IllegalStateException(  
                    "rowIndex over maxRowIndex!!!rowIndex:" + rowIndex  
                            + "  maxRowIndex:" + sheet.getLastRowNum());  
        }  
        HSSFRow row = sheet.getRow(rowIndex);  
        if (row != null) {  
            if (row.getPhysicalNumberOfCells() < colIndex) {  
                // throw new  
                // IllegalStateException("colIndex over maxColIndex!!!colIndex:"+colIndex+"  maxColIndex:"+row.getPhysicalNumberOfCells());  
            }  
            HSSFCell cell = row.getCell(colIndex);  
            if (cell != null) {  
                //HSSFCellStyle cellStyle=cell.getCellStyle();  
                HSSFFont font = workbook.createFont();  
                font.setColor(getColor(color,workbook).getIndex());  
                  
                HSSFRichTextString ts=cell.getRichStringCellValue();  
                  
                ts.applyFont(font);  
  
                cell.setCellValue(ts);  
            }  
        }  
    }  
  
      
    /** 
     * 克隆字體 
     *  
     * @param font 
     *            要克隆的字體 
     * @return 克隆的字體 
     */  
    public static HSSFFont cloneFont(HSSFFont font,HSSFWorkbook workbook) {  
        HSSFFont newFont = workbook.createFont();  
        newFont.setFontName(font.getFontName());  
        newFont.setFontHeightInPoints(font.getFontHeightInPoints());  
        newFont.setColor(font.getColor());  
        newFont.setBoldweight(font.getBoldweight());  
        newFont.setItalic(font.getItalic());  
        newFont.setStrikeout(font.getStrikeout());  
        newFont.setUnderline(font.getUnderline());  
        return newFont;  
    }  
      
      
    /** 
     * 獲取指定顏色對應的HSSF顏色，如果該HSSF顏色不存在，則創建與指定顏色對應的新HSSF顏色 
     *  
     * @param color 
     *            顏色 
     * @return HSSF顏色 
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
      
    public static void fillDataWithBorder(int rowIndex, int colIndex,  
            String value, HSSFSheet sheet) {  
        if (sheet == null)  
            return;  
        if (sheet.getLastRowNum() < rowIndex) {  
            throw new IllegalStateException(  
                    "rowIndex over maxRowIndex!!!rowIndex:" + rowIndex  
                            + "  maxRowIndex:" + sheet.getLastRowNum());  
        }  
        HSSFRow row = sheet.getRow(rowIndex);  
        if (row != null) {  
            if (row.getPhysicalNumberOfCells() < colIndex) {  
                // throw new  
                // IllegalStateException("colIndex over maxColIndex!!!colIndex:"+colIndex+"  maxColIndex:"+row.getPhysicalNumberOfCells());  
            }  
            HSSFCell cell = row.getCell(colIndex);  
            if (cell != null) {  
//                cell.setCellValue(StringUtil.trim(value));  
                HSSFCellStyle cellStyle = cell.getCellStyle();  
  
                cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);  
  
                cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
  
                cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
  
                cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            }  
        }  
    }  
      
      
    public static void createNewRow(int beginrowindex,int rowcount,HSSFSheet sheet){  
        if(sheet==null)return;  
        HSSFCellStyle cellstyle=null;  
        if(beginrowindex!=0){  
            if(sheet.getRow(beginrowindex)!=null)  
            cellstyle=sheet.getRow(beginrowindex).getCell(0).getCellStyle();  
        }  
            for(int i=beginrowindex;i<rowcount+beginrowindex;i++){  
                HSSFRow row=sheet.createRow(i);  
                for(int j=0;j<sheet.getRow(beginrowindex-1).getLastCellNum();j++){  
                    if(cellstyle!=null)  
                        row.createCell(j).setCellStyle(cellstyle);  
                    else  
                        row.createCell(j);  
                }  
            }  
            sheet.showInPane((short)0, (short)0);  
  
    }  
}  