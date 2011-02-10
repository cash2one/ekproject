package cn.elamzs.common.eimport.core;

import java.io.File;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import cn.elamzs.common.base.files.util.POIExcelUtil;
import cn.elamzs.common.base.files.util.StringUtil;
import cn.elamzs.common.eimport.inter.DataProcess;
import cn.elamzs.common.eimport.inter.DataValidator;

/**
 * 
 * @author Ethan.Lam   2011-2-5
 * Excel ���ݵ��� ��  ��������
 * 
 */
public class ExcelImportHandler extends AbstractFileHandler {
 		
	private int isXlsx = 0; // 0:2003 1:2007
	
	
	public ExcelImportHandler(DataValidator validator,DataProcess dataPro,File file,int isXlsx) throws Exception {
		super(validator,dataPro,file);	
		this.isXlsx = isXlsx;
	}

	
	@Override
	public String[] returnImpDocColumnsName() throws Exception {
		// TODO Auto-generated method stub
		
		Workbook wb = POIExcelUtil.openWorkBook(importFile, isXlsx==0?true:false);
		Sheet sheet  = POIExcelUtil.openSheet(wb,0);
		
		if(sheet==null)
			throw new Exception("��Excelû�а���������");
		String[] columnsName = POIExcelUtil.getRowValues(wb,sheet, 0);
	   
		sheet = null;
		wb = null;
	    
		return columnsName;
	}
	
	
	
	/**
	 * 
	 * ��ʼ���ĵ��е����ݶ�ȡ���ڴ���
	 * @throws Exception 
	 */
	@Override
	public void loadDatas() throws Exception {
		// TODO Auto-generated method stub
		
		Workbook wb = POIExcelUtil.openWorkBook(importFile, isXlsx==0?true:false);
		Sheet sheet  = POIExcelUtil.openSheet(wb,0);
		
		if(sheet==null)
			throw new Exception("��Excelû�а���������");
		
		
		int totalRows = sheet.getPhysicalNumberOfRows();
		String[] rowValues =  null;
		String validateMsg = "";

		
		//���ж�ȡ�ĵ��е�����
		for(int rowSeq = 1;rowSeq<totalRows;rowSeq++){
		    rowValues = POIExcelUtil.getRowValues(wb,sheet, rowSeq);
		    validateMsg = dataElement.match(rowValues);
//		    if(!StringUtil.isNull(validateMsg))
		         dataPro.forEachRowValueProcess(dataElement);
		    rowValues = null;
		}
	}


	@Override
	public void createImportResultDocument(String[][] resultDatas)
			throws Exception {
		// TODO Auto-generated method stub
		
		String columnsNameStr = dataElement.getImpColumnsName()+",������";
		int[] width = dataElement.getImpColumnsWidthSet();
		String [] columnsName = columnsNameStr.split(",");
		
		String fileName =ConfigControl.DIR_IMPORT_RESULT+"imp_"+importFile.getName();
		POIExcelUtil.writeDataToExcel(fileName,columnsName,resultDatas,width,null,IndexedColors.LIGHT_YELLOW,isXlsx==0?true:false,null);
		
		
	}


	
}
