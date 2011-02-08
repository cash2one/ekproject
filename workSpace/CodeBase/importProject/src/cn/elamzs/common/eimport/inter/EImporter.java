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
	public File downTemplate(Class<? extends DataValidator> validator,
			FileType type) throws Exception;

	// �ϴ��ļ�
	public String importFile(String dataFile) throws Exception;

	// ����Դ�ļ����ϴ����ļ���
	public File getResourceFile(String fileIdentifyId) throws Exception;

	// �����û������ļ���Ľ��
	public File getImportedResult(String fileIdentifyId) throws Exception;

}
