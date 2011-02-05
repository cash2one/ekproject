package cn.elamzs.common.eimport.core;

import java.io.File;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import cn.elamzs.common.base.files.util.POIExcelUtil;
import cn.elamzs.common.base.files.util.StringUtil;
import cn.elamzs.common.eimport.inter.DataElement;
import cn.elamzs.common.eimport.inter.DataProcess;
import cn.elamzs.common.eimport.inter.DataValidator;

/**
 * 
 * @author Ethan.Lam   2011-2-5
 * 
 * 
 */
public class ExcelImportHandler extends AbstractFileHandler {
 		
	private int isXlsx = 0; // 0:2003 1:2007
	private File importFile = null;
	
	public ExcelImportHandler(DataValidator validator,DataProcess dataPro,File file,int isXlsx) {
		super(validator,dataPro,file);	
		this.isXlsx = isXlsx;
		importFile = file;
	}

	/**
	 * 
	 * ��ʼ��ȡ�ı�����
	 * @throws Exception 
	 */
	@Override
	public void loadDatas() throws Exception {
		// TODO Auto-generated method stub
		Workbook wb = POIExcelUtil.openWorkBook(importFile, isXlsx==0?true:false);
		Sheet sheet  = POIExcelUtil.openSheet(wb,0);
		if(sheet==null)
			throw new Exception("��Excelû�а���������");
		
		DataElement dataBo = new DataElement(this.validator);
		int totalRows = sheet.getPhysicalNumberOfRows();
		String[] rowValues =  null;
		String validateMsg = "";
		
		//���ж�ȡ�ĵ��е�����
		for(int rowSeq = 1;rowSeq<totalRows;rowSeq++){
		    rowValues = POIExcelUtil.getRowValues(sheet, rowSeq);
		    validateMsg = dataBo.match(rowValues);
		    if(!StringUtil.isNull(validateMsg))
		         dataPro.currentRowValueProcess(dataBo);
		    rowValues = null;
		}
		
	}
	  

	
	
}
