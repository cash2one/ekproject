package com.qtone.datasync.xxt.dp;

/**
 * @author ���ڷ�	2009-9-2 
 *
 */
public interface IMiscToXxtFunctionTestHelper {
	/**
	 * ׼��DB������������½����ݼ�¼
	 */
	public void prepareDb();

	/**
	 * ��ģ�������ͬ������
	 */
	public void doSync();

	/**
	 * ��鷢������ı�����Ŀ����յ��ļ�¼��Ŀ
	 * �����ͬ�ͷ���true
	 * 
	 * @return 
	 */
	public boolean checkRequestCount();

	/**
	 * ���Բ�������ƽ̨�ϵļ�¼�Ĵ�����
	 * 
	 * @return
	 */
	public boolean checkNonexistsUserDealResult();

	/**
	 * ���Դ�����ƽ̨���û��Ĵ�����
	 * �����˶Զ����շ�ҵ������ҵ��ķֱ���
	 * @return
	 */
	public boolean checkExistsUserDealRequest();
	
}
