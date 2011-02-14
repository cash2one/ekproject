package cn.elamzs.common.eimport.core;

import java.io.File;

/**
 * 
 * @author Ethan.Lam 2011-2-7
 * 
 */
public interface FileHandler extends Runnable {

	/**
	 * ��֤�����ĵ��Ƿ��Ƿ���ģ��Ҫ��
	 * @return �����ļ�������(��������)
	 * @throws Exception
	 */
	public String[] returnImpDocColumnsName()throws Exception;
	
	
	/**
	 * ���ı��е����ݶ��뵽�ڴ��У�ʵʩ������֤����
	 * 
	 * @throws Exception
	 */
	public void loadDatas() throws Exception;

	
	/**
	 * �Ѷ�Ӧ�ĵ������ݽ�������ɲ����浽��Ӧ���ļ��ĵ���
	 * 
	 * @param resultDatas
	 * @throws Exception
	 */
	public String createImportResultDocument(String[][] resultDatas)
			throws Exception;

	
}
