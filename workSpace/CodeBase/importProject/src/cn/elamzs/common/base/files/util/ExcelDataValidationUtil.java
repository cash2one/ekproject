package cn.elamzs.common.base.files.util;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.ss.util.CellRangeAddressList;

/**
 * 
 * @author Ethan.Lam 2011-2-9
 * Excel 数据有效性 验证
 * 
 */
public class ExcelDataValidationUtil {

	public static DVConstraint getListDVConstraint(String[] strArray) {
		DVConstraint constraint = DVConstraint
				.createExplicitListConstraint(strArray);
		return constraint;
	}

	public static DVConstraint getDateDVConstraintBetween(String beginDate,
			String endDate, String dateFormat) {
		DVConstraint constraint = DVConstraint.createDateConstraint(
				DVConstraint.OperatorType.BETWEEN, beginDate, endDate,
				dateFormat);
		return constraint;
	}

	public static DVConstraint getIntDVConstraintBetween(String beginData,
			String endData) {
		DVConstraint constraint = DVConstraint.createNumericConstraint(
				DVConstraint.ValidationType.INTEGER,
				DVConstraint.OperatorType.BETWEEN, beginData, endData);
		return constraint;
	}

	public static DVConstraint getDecimalDVConstraintBetween(String beginData,
			String endData) {
		DVConstraint constraint = DVConstraint.createNumericConstraint(
				DVConstraint.ValidationType.DECIMAL,
				DVConstraint.OperatorType.BETWEEN, beginData, endData);
		return constraint;
	}

	/**
	 * 
	 * 添加下拉列表的方法如下
	 * @param beginRowindex
	 * @param beginColindex
	 * @param endRowindex
	 * @param endColindex
	 * @param strArray
	 * @param errorMsg
	 * @param correctMsg
	 * @return
	 */
	public static HSSFDataValidation addDataValidation(int beginRowindex,
			short beginColindex, int endRowindex, short endColindex,
			String[] strArray, String errorMsg, String correctMsg) {

		DVConstraint constraint = getListDVConstraint(strArray);
		// 设置数据有效性加载在哪个单元格上。
		// 四个参数分别是：起始行、终止行、起始列、终止列
		CellRangeAddressList regions = new CellRangeAddressList(beginRowindex,
				endRowindex, beginColindex, endColindex);

		// 数据有效性对象
		HSSFDataValidation data_validation = new HSSFDataValidation(regions,
				constraint);
		data_validation.createErrorBox(errorMsg, correctMsg);
		return data_validation;
	}

	
	/**
	 * 
	 * 添加日期输入限制方法如下
	 * @param beginRowindex
	 * @param beginColindex
	 * @param endRowindex
	 * @param endColindex
	 * @param beginDate
	 * @param endDate
	 * @param dateFormat
	 * @param errorMsg
	 * @param correctMsg
	 * @return
	 */
	public static  HSSFDataValidation addDataValidation(int beginRowindex,
			short beginColindex, int endRowindex, short endColindex,
			String beginDate, String endDate, String dateFormat,
			String errorMsg, String correctMsg) {

		DVConstraint constraint = getDateDVConstraintBetween(beginDate,
				endDate, dateFormat);
		// 设置数据有效性加载在哪个单元格上。
		// 四个参数分别是：起始行、终止行、起始列、终止列
		CellRangeAddressList regions = new CellRangeAddressList(beginRowindex,
				endRowindex, beginColindex, endColindex);

		// 数据有效性对象
		HSSFDataValidation data_validation = new HSSFDataValidation(regions,
				constraint);
		// data_validation.createErrorBox("error",
		// "You must input a numeric between "+beginDate+" and "+endDate+".");

		data_validation.createErrorBox(errorMsg, beginDate + " ～ " + endDate
				+ correctMsg);

		return data_validation;
	}

	
	/**
	 * 
	 * @param beginRowindex
	 * @param beginColindex
	 * @param endRowindex
	 * @param endColindex
	 * @param beginIntData
	 * @param endIntData
	 * @param errorMsg
	 * @param correctMsg
	 */
	public static HSSFDataValidation addDataValidationInt(int beginRowindex, short beginColindex,
			int endRowindex, short endColindex, String beginIntData,
			String endIntData, String errorMsg, String correctMsg) {

		DVConstraint constraint = getIntDVConstraintBetween(beginIntData,
				endIntData);
		// 设置数据有效性加载在哪个单元格上。
		// 四个参数分别是：起始行、终止行、起始列、终止列
		CellRangeAddressList regions = new CellRangeAddressList(beginRowindex,
				endRowindex, beginColindex, endColindex);

		// 数据有效性对象
		HSSFDataValidation data_validation = new HSSFDataValidation(regions,
				constraint);
		data_validation.createErrorBox(errorMsg, beginIntData + " ～ "
				+ endIntData + correctMsg);

		return data_validation;
	}

	/**
	 * 
	 * @param beginRowindex
	 * @param beginColindex
	 * @param endRowindex
	 * @param endColindex
	 * @param beginDecimalData
	 * @param endDecimalData
	 * @param errorMsg
	 * @param correctMsg
	 */
	public static HSSFDataValidation addDataValidationDecimal(int beginRowindex,
			short beginColindex, int endRowindex, short endColindex,
			String beginDecimalData, String endDecimalData, String errorMsg,
			String correctMsg) {

		DVConstraint constraint = getDecimalDVConstraintBetween(
				beginDecimalData, endDecimalData);
		
		// 设置数据有效性加载在哪个单元格上。
		// 四个参数分别是：起始行、终止行、起始列、终止列
		CellRangeAddressList regions = new CellRangeAddressList(beginRowindex,
				endRowindex, beginColindex, endColindex);

		// 数据有效性对象
		HSSFDataValidation data_validation = new HSSFDataValidation(regions,
				constraint);
		data_validation.createErrorBox(errorMsg, beginDecimalData + " ～ "
				+ endDecimalData + correctMsg);

		return data_validation;
	}

}