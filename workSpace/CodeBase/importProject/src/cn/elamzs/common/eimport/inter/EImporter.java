package cn.elamzs.common.eimport.inter;

import java.io.File;

import cn.elamzs.common.eimport.enums.FileType;

/**
 * 
 * @author Ethan.Lam �ļ����빤�߽ӿ�
 * 
 */
public interface EImporter {

	// ���ص���ģ��
	public File downTemplate(FileType type) throws Exception;

	// �ϴ��ļ�
	public String importFile(String dataFile,String alias,String storeSubDir) throws Exception;

	// ����Դ�ļ����ϴ����ļ���
	public File getResourceFile(String importTaskSeq) throws Exception;

	// �����û������ļ���Ľ��
	public File getImportedResult(String importTaskSeq) throws Exception;

}
