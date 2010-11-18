package com.qtone.datasync.misc.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author ���ڷ� 2009-9-9 �����Զ�����̳߳أ����ڿ����ύ���������
 */
public class MyThreadPool {
	private final Log log = LogFactory.getLog(this.getClass());

	private final Semaphore locks;

	private final ThreadPoolExecutor proxy;

	private final BlockingQueue<Runnable> workQueue;

	/********************************/
	private final int CORE_POOL_SIZE = NumberUtils
			.toInt(RevertSyncContext.THREAD_POOL_CORE_SIZE);
	private final int MAX_POOL_SIZE = NumberUtils
			.toInt(RevertSyncContext.THREAD_POOL_MAX_SIZE);
	private final long THREAD_ALIVE_TIME = NumberUtils
			.toLong(RevertSyncContext.THREAD_KEEP_ALIVE_TIME);/* ����Ϊ��λ */
	/********************************/
	
	public MyThreadPool() {
		workQueue = new LinkedBlockingQueue<Runnable>();

		locks = new Semaphore(MAX_POOL_SIZE);

		proxy = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE + 1,
				THREAD_ALIVE_TIME, TimeUnit.SECONDS, workQueue,
				new ThreadPoolExecutor.CallerRunsPolicy());
	}

	public void submit(final Runnable task) throws InterruptedException {
		locks.acquire();
		try{
			proxy.submit(new Runnable() {

				public void run() {
					try {
						task.run();
					} finally {
						locks.release();
					}
				}

			});
		}catch(RejectedExecutionException e){
			log.error("�ύ�������̳߳ؾܾ�",e);
			locks.release();
		}
	}

	public void execute(final Runnable task) throws InterruptedException {
		submit(task);
	}

	public synchronized void shutdown() {
		proxy.shutdown();

		try {
			boolean stopped = proxy.awaitTermination(3L * 60, TimeUnit.SECONDS);
			if (!stopped)
				proxy.awaitTermination(3L * 60, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			log.error("�̳߳عر�ʧ�ܣ�����ȡǿ���Դ�ʩ��", e);
			throw new RuntimeException("ǿ���Թر��̳߳�,���˳�Ӧ�ã�");
		}

	}

	/**
	 * @return
	 */
	public BlockingQueue<Runnable> getWorkQueue() {
		return workQueue;
	}
}