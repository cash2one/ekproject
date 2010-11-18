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
 * @author 杨腾飞 2009-9-9 构造自定义的线程池，用于控制提交任务的数量
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
			.toLong(RevertSyncContext.THREAD_KEEP_ALIVE_TIME);/* 以秒为单位 */
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
			log.error("提交的任务被线程池拒绝",e);
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
			log.error("线程池关闭失败！将采取强制性措施！", e);
			throw new RuntimeException("强制性关闭线程池,并退出应用！");
		}

	}

	/**
	 * @return
	 */
	public BlockingQueue<Runnable> getWorkQueue() {
		return workQueue;
	}
}