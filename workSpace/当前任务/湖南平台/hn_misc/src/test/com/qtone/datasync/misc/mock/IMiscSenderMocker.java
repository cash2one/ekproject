package com.qtone.datasync.misc.mock;

/**
 * @author ���ڷ� 2009-9-7 ģ��MISC���ķ������ӿ�
 */
public interface IMiscSenderMocker {

	/**
	 * ֱ�ӷ��ͱ��ģ��������Ѿ���֯�õġ�
	 * 
	 * @param msgFile
	 *            �����ļ���һ��һ������
	 * @param retFeil
	 *            ���ķ��ؽ���¼������ļ�
	 * @return
	 */
	public String sendMsg(String msgFile, String retFeil);

	/**
	 * �����Ĳ���#phone salemodalid#��������#�ţ����зֽ⣬���ҶԽ�����Ӧ�� ģ������һ�����ģ����͸�XXTͬ������
	 * 
	 * @param msgFile �����ļ���#phone salemodalid#��������#�ţ�
	 * @param tplFile ģ���ļ�
	 * @param retFile �����ļ�
	 * @return
	 */
	public String parseAndSend(String msgFile, String tplFile, String retFile);
}
