package cn.elamzs.common.eimport.core;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

import cn.elamzs.common.base.files.util.POIExcelUtil;
import cn.elamzs.common.eimport.Anotation.Restriction;
import cn.elamzs.common.eimport.Anotation.ValidatorRule;
import cn.elamzs.common.eimport.enums.FileType;
import cn.elamzs.common.eimport.inter.DataValidator;

/**
 * @author Ethan.Lam
 * �ļ�����ģ��������
 * 
 */
public class Template {

	private DataValidator validator;
	
	private String templateName;
	
	//������¼   �����-����������   ��ֵ�� ���ݼ���   
	private Map<Integer,String> cnColumnsName = null;
	
	
	//������¼   �����-�п�   ��ֵ�� ���ݼ���   
	private Map<Integer,Integer> columnsWidth = null;
	
	
	public Template(DataValidator validator) throws Exception{
        this.validator = validator;
        inintTemplateInfos();
	}
	
	
	/**
	 * 
	 * ���ɵ���ģ��  
	 * @return
	 */
	public File createImpTemplateDoc(FileType type){
	   return FileType.EXCEL_XLS==type||FileType.EXCEL_XLSX==type?createExcleTemplate(type):null;	
	}
	
	
	/**
	 * 
	 */
	void inintTemplateInfos() throws Exception{
		
		Restriction restriction = validator.getClass().getAnnotation(Restriction.class);	
		
		if(restriction==null)
		   throw new Exception("��"+validator.getClass()+"û����ȷ���� Restriction ע�⣡");
		
		templateName =restriction.fileName();
		
		
	   	Method[] methods = validator.getClass().getMethods();
	    
	   	if(methods==null)
	    	return ;
	    
	    cnColumnsName = new HashMap<Integer,String>();
	    columnsWidth = new HashMap<Integer,Integer>();
	    
	    for(Method method:methods){
	    	 ValidatorRule rule = method.getAnnotation(ValidatorRule.class);		    	
	    	 if(rule!=null){
	    		 cnColumnsName.put(rule.columnSeq(),rule.showName());
	    		 columnsWidth.put(rule.columnSeq(), rule.width());
	    	 }
	    }
	}
	
	 
    /**
     * 
     * ���ص����ļ������������� ��,�� ���
     * @return
     */
    String getImpColumnsName(){
    	String columnsNameStr = "";
    	for(int seq = 0;seq<this.cnColumnsName.size();seq++)
    		columnsNameStr+=","+this.cnColumnsName.get(seq);
    	return columnsNameStr.substring(1);
    }
    
    /**
     * ���ص����ļ��п������
     * @return
     */
    int[] getImpColumnsWidthSet(){
    	int[] columnsWidths = new int[columnsWidth.size()];
    	for(int seq = 0;seq<this.columnsWidth.size();seq++)
    		columnsWidths[seq]=columnsWidth.get(seq);
    	return columnsWidths;
    }
    
    
    /**
     * 
     * ����Excleģ���ļ�
     * @param type
     * @return
     */
	synchronized File createExcleTemplate(FileType type){
		try{
			String columnsNameStr = getImpColumnsName();
			int[] width = getImpColumnsWidthSet();
			String [] columnsName = columnsNameStr.split(",");
			String fileName =ConfigControl.DIR_IMPORT_TEMPLATE+templateName+type.suffix();
			POIExcelUtil.writeDataToExcel(fileName,columnsName,null,width,FileType.EXCEL_XLS==type?true:false);
			
//			Workbook wb = POIExcelUtil.openWorkBook(fileName,FileType.EXCEL_XLS==type?true:false);
//			POIExcelUtil.setFontColor(0,0,Color.red,wb,wb.getSheetAt(0));
//			FileOutputStream fileOut = new FileOutputStream(fileName);
//		    wb.write(fileOut);
//			fileOut.close();

		    return new File(fileName);
		}catch(Exception e){
			
		}
		return null;
	}
	
                 
}
