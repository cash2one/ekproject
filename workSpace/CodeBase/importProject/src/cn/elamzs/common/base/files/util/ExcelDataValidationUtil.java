package cn.elamzs.common.base.files.util;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.ss.util.CellRangeAddressList;

/**
 * 
 * @author Ethan.Lam 2011-2-9
 * Excel ������Ч�� ��֤
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
	 * ��������б�ķ�������
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
		// ����������Ч�Լ������ĸ���Ԫ���ϡ�
		// �ĸ������ֱ��ǣ���ʼ�С���ֹ�С���ʼ�С���ֹ��
		CellRangeAddressList regions = new CellRangeAddressList(beginRowindex,
				endRowindex, beginColindex, endColindex);

		// ������Ч�Զ���
		HSSFDataValidation data_validation = new HSSFDataValidation(regions,
				constraint);
		data_validation.createErrorBox(errorMsg, correctMsg);
		return data_validation;
	}

	
	/**
	 * 
	 * ��������������Ʒ�������
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
		// ����������Ч�Լ������ĸ���Ԫ���ϡ�
		// �ĸ������ֱ��ǣ���ʼ�С���ֹ�С���ʼ�С���ֹ��
		CellRangeAddressList regions = new CellRangeAddressList(beginRowindex,
				endRowindex, beginColindex, endColindex);

		// ������Ч�Զ���
		HSSFDataValidation data_validation = new HSSFDataValidation(regions,
				constraint);
		// data_validation.createErrorBox("error",
		// "You must input a numeric between "+beginDate+" and "+endDate+".");

		data_validation.createErrorBox(errorMsg, beginDate + " �� " + endDate
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
		// ����������Ч�Լ������ĸ���Ԫ���ϡ�
		// �ĸ������ֱ��ǣ���ʼ�С���ֹ�С���ʼ�С���ֹ��
		CellRangeAddressList regions = new CellRangeAddressList(beginRowindex,
				endRowindex, beginColindex, endColindex);

		// ������Ч�Զ���
		HSSFDataValidation data_validation = new HSSFDataValidation(regions,
				constraint);
		data_validation.createErrorBox(errorMsg, beginIntData + " �� "
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
		
		// ����������Ч�Լ������ĸ���Ԫ���ϡ�
		// �ĸ������ֱ��ǣ���ʼ�С���ֹ�С���ʼ�С���ֹ��
		CellRangeAddressList regions = new CellRangeAddressList(beginRowindex,
				endRowindex, beginColindex, endColindex);

		// ������Ч�Զ���
		HSSFDataValidation data_validation = new HSSFDataValidation(regions,
				constraint);
		data_validation.createErrorBox(errorMsg, beginDecimalData + " �� "
				+ endDecimalData + correctMsg);

		return data_validation;
	}

}