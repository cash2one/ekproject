package cn.elamzs.common.eimport.inter;

import java.io.File;

/**
 * 
 * @author Ethan.Lam
 * �ļ����빤�߽ӿ�
 * 
 */
public interface EImporter {

	//���ص���ģ��
	public File downTemplate();
	
	// �ϴ��ļ�
	public String upLoadFile(String dataFile);

	
	// ����Դ�ļ����ϴ����ļ���
	public File getResourceFile(String fileIdentifyId);

	
	// �����û������ļ���Ľ��
	public File getImportedResult(String fileIdentifyId);


	
}
