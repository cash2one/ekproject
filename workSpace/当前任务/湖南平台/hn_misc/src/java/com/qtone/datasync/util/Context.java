package com.qtone.datasync.util;


/**
 * Ӧ�õ�ȫ����Ϣ
 * 
 * @author ���ڷ� 2009-05-31
 */
// @SuppressWarnings("unchecked")
public class Context {

	/**
	 * ���ݶ�ȡ�߳��Ƿ��Ѿ���ɹ�����������ֹͣ
	 */
	public static volatile boolean isProducerFinished = false;

	/**
	 * ���ݶ�ȡ�߳��Ƿ��쳣�ж�
	 */
	public static volatile boolean isProducerInterrupted = false;

	/**
	 * �����̣߳�������Miscͬ�����ݵ��̣߳�����Ŀ
	 */
	public static volatile int consumerCount = 0;

	/**
	 * ʵ����г���
	 */
	public static final int BEAN_QUEUE_SIZE = 100;

	/**
	 * ������г���
	 */
	public static final int TASK_QUEUE_SIZE = BEAN_QUEUE_SIZE / 2;

	/**
	 * �̳߳س���
	 */
	public static final int THREAD_POOL_SIZE = Runtime.getRuntime()
			.availableProcessors() + 1;

	/**
	 * �������ӳر���
	 */
	public static final String DB_ALIAS = "hnxxt";

	/**
	 * Misc�����ַ
	 */
	public static final String MISC_SERVICE_PORT_URL = "http://127.0.0.1/hnxxt/services/misc";
	
	public static final String MISC_SERVICE_WSDL_URL = MISC_SERVICE_PORT_URL+"?wsdl";
	
	/**
	 * ͬ������ID,���ڱ�ʶ��ǰ��ͬ������
	 */
	public static final String SYNC_SEQ;
	static{
		SYNC_SEQ = Long.toString(System.nanoTime());
	}
	

}
