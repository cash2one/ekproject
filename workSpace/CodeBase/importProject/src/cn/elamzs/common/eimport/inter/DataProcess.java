package cn.elamzs.common.eimport.inter;

import java.io.File;

import cn.elamzs.common.eimport.core.DataElement;
import cn.elamzs.common.eimport.exception.DataProcessException;

/**
 * 
 * ���������ݵ������������ ��Ӧ�� ���̴��� �Ľӿ�
 * 
 * @author Ethan.Lam
 * 
 */
public interface DataProcess {

	
	/**
	 * 
	 * ��ÿ��ȡ���ĵ��е�һ�����ݺ� ʵ����ɶԸ������ݽ�ִ�еĲ�������ѵ�ǰ�����ݲ��뵽Ŀ�����ݿ�
	 * 
	 * @param data
	 */
	public void forEachRowValueProcess(DataElement data) throws DataProcessException;

	
	/**
	 * 
	 * ��ִ�����������ݼ��غ󣬵��� afterLoadRowsDataEvent ������ ע�ͣ�����������У��ɶ����ݽ���������
	 * 
	 */
	public void afterLoadAllRowsDataProcess() throws DataProcessException;


	
	/**
	 * ��󴴽������ĵ�
	 */
	public String[][] createImportResult() throws DataProcessException;

   
	
	
}
