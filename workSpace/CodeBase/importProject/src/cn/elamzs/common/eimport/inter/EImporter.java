package cn.elamzs.common.eimport.inter;

import java.io.File;

import cn.elamzs.common.eimport.enums.FileType;

/**
 * �����ļ��������ӿ�
 * @author Ethan.Lam 
 * 
 */
public interface EImporter {

	/**
	 * 
	 * ���ص���ģ��
	 * @param type   �ļ�����
	 * @return       ����ģ��
	 * @throws Exception
	 */
	public File downTemplate(FileType type) throws Exception;

	
	/**
	 * �ϴ��ļ�
	 * @param dataFileFullPath   �����ļ���ȫ·��
	 * @param alias              �ļ���
	 * @param storeSubDir        ��Ŀ¼
	 * @param taskType           �������ͱ�ʶ
	 * @return                   
	 * @throws Exception
	 */
	public String importFile(String dataFileFullPath,String alias,String storeSubDir,String taskType) throws Exception;

	
	/**
	 * 
	 * @param importTaskSeq    ��������к�
	 * @return                 ����Դ�ϴ����ļ� 
	 * @throws Exception
	 */
	public File getResourceFile(String importTaskSeq) throws Exception;

	
	/**
	 * 
	 * @param importTaskSeq  ��������к�
	 * @return               �����û������ļ���Ľ��
	 * @throws Exception
	 */
	public File getImportedResult(String importTaskSeq) throws Exception;

	
}
