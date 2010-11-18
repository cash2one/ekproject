package com.qtone.datasync.util;


/**
 * 应用的全局信息
 * 
 * @author 杨腾飞 2009-05-31
 */
// @SuppressWarnings("unchecked")
public class Context {

	/**
	 * 数据读取线程是否已经完成工作，并正常停止
	 */
	public static volatile boolean isProducerFinished = false;

	/**
	 * 数据读取线程是否被异常中断
	 */
	public static volatile boolean isProducerInterrupted = false;

	/**
	 * 消费线程（负责向Misc同步数据的线程）的数目
	 */
	public static volatile int consumerCount = 0;

	/**
	 * 实体队列长度
	 */
	public static final int BEAN_QUEUE_SIZE = 100;

	/**
	 * 任务队列长度
	 */
	public static final int TASK_QUEUE_SIZE = BEAN_QUEUE_SIZE / 2;

	/**
	 * 线程池长度
	 */
	public static final int THREAD_POOL_SIZE = Runtime.getRuntime()
			.availableProcessors() + 1;

	/**
	 * 数据连接池别名
	 */
	public static final String DB_ALIAS = "hnxxt";

	/**
	 * Misc服务地址
	 */
	public static final String MISC_SERVICE_PORT_URL = "http://127.0.0.1/hnxxt/services/misc";
	
	public static final String MISC_SERVICE_WSDL_URL = MISC_SERVICE_PORT_URL+"?wsdl";
	
	/**
	 * 同步程序ID,用于标识当前的同步程序
	 */
	public static final String SYNC_SEQ;
	static{
		SYNC_SEQ = Long.toString(System.nanoTime());
	}
	

}
