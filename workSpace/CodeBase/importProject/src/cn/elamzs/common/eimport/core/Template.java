package cn.elamzs.common.eimport.core;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

import cn.elamzs.common.base.files.util.POIExcelUtil;
import cn.elamzs.common.eimport.annotations.ColumnValidate;
import cn.elamzs.common.eimport.annotations.Restriction;
import cn.elamzs.common.eimport.config.ConfigSetting;
import cn.elamzs.common.eimport.enums.FileType;
import cn.elamzs.common.eimport.inter.DataValidator;

/**
 * @author Ethan.Lam
 * 文件导入模版生成器
 * 
 */
public class Template {

	private DataValidator validator;
	
	private String templateName;
	
	//用来记录   列序号-列中文命名   键值对 数据集合   
	private Map<Integer,String> cnColumnsName = null;
	
	//用来记录   列序号-列宽   键值对 数据集合   
	private Map<Integer,Integer> columnsWidth = null;
	
	//用来记录表头的颜色
	private Map<Integer,IndexedColors> columnsColor = null;
	
	//用来记录   列序号-批注    键值对 数据集合   
	private Map<Integer,String> columnsComment = null;
	
	public Template(DataValidator validator) throws Exception{
        this.validator = validator;
        inintTemplateInfos();
	}
	
	/**
	 * 
	 * 生成导入模版  
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
		   throw new Exception("类"+validator.getClass()+"没有正确配置 Restriction 注解！");
		
		templateName =restriction.fileName();
		
		
	   	Method[] methods = validator.getClass().getMethods();
	    
	   	if(methods==null)
	    	return ;
	   
	   	cnColumnsName = new HashMap<Integer,String>();
	    columnsWidth = new HashMap<Integer,Integer>();
	    columnsColor = new HashMap<Integer,IndexedColors>();
	    columnsComment  = new HashMap<Integer,String>();
	    
	    for(Method method:methods){
	    	 ColumnValidate rule = method.getAnnotation(ColumnValidate.class);		    	
	    	 if(rule!=null){
	    		 cnColumnsName.put(rule.columnSeq(),rule.showName());
	    		 columnsWidth.put(rule.columnSeq(), rule.width());
	    		 columnsColor.put(rule.columnSeq(), rule.color());
	    		 columnsComment.put(rule.columnSeq(), rule.comment());
	    	 }
	    }
	}
	
	 
    /**
     * 
     * 返回导入文件中文列名，用 “,” 相隔
     * @return
     */
    String getImpColumnsName(){
    	String columnsNameStr = "";
    	for(int seq = 0;seq<this.cnColumnsName.size();seq++)
    		columnsNameStr+=","+this.cnColumnsName.get(seq);
    	return columnsNameStr.substring(1);
    }
    
    /**
     * 返回导入文件列宽的设置
     * @return
     */
    int[] getImpColumnsWidthSet(){
    	int[] columnsWidths = new int[columnsWidth.size()];
    	for(int seq = 0;seq<this.columnsWidth.size();seq++)
    		columnsWidths[seq]=columnsWidth.get(seq);
    	return columnsWidths;
    }
    
    
    /**
     * 返回导入文件列宽的设置
     * @return
     */
    IndexedColors[] getImpColumnsColorSet(){
    	 IndexedColors[] columnsColors = new IndexedColors[columnsColor.size()];
    	for(int seq = 0;seq<this.columnsColor.size();seq++)
    		columnsColors[seq]=columnsColor.get(seq);
    	return columnsColors;
    }
    
    /**
     * 返回导入文件列的批注
     * @return
     */
    String[] getImpColumnsCommentSet(){
    	String[] columnsCommentStr = new String[columnsComment.size()];
    	for(int seq = 0;seq<this.columnsComment.size();seq++)
    		columnsCommentStr[seq]=columnsComment.get(seq);
    	return columnsCommentStr;
    }
    
    
    /**
     * 
     * 生成Excle模版文件
     * @param type
     * @return
     */
	synchronized File createExcleTemplate(FileType type){
		try{
			String columnsNameStr = getImpColumnsName();      //列名称
			int[] width = getImpColumnsWidthSet();            //列宽
			IndexedColors[] colors = getImpColumnsColorSet(); //列表头颜色
			String [] columnsName = columnsNameStr.split(","); 
			String[] comments = getImpColumnsCommentSet();  //列表头批注
			String fileName =ConfigSetting.DIR_IMPORT_TEMPLATE+templateName+type.suffix();
			POIExcelUtil.writeDataToExcel(fileName,columnsName,null,width,colors,IndexedColors.BRIGHT_GREEN,FileType.EXCEL_XLS==type?true:false,comments);
		    return new File(fileName);
		}catch(Exception e){
			
		}
		return null;
	}
	
                 
}
