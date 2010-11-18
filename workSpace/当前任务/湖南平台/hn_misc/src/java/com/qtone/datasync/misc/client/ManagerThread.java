package com.qtone.datasync.misc.client;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qtone.datasync.bean.AreaBean;
import com.qtone.datasync.misc.util.MyThreadPool;
import com.qtone.datasync.misc.util.RevertSyncContext;

/**
 * 负责管理、调度生产线程、消费线程及线程池
 * 
 * @author 杨腾飞 2009-06-01
 */
public class ManagerThread implements Runnable {
	private final Log log = LogFactory.getLog(ManagerThread.class);

	public static MyThreadPool threadPool;

	private Thread producer = null;

	private List<AreaBean> areaList = null;

	public ManagerThread(List<AreaBean> areaList) {
		threadPool = new MyThreadPool();

		this.areaList = areaList;
	}

	public void run() {
		if (areaList == null) {
			log.error("地区信息列表为空，管理线程无法正常启动！");
			return;
		}

		// 启动生产和消费者线程
		producer = new Thread(new ProducerThread(areaList), "Producer"
				+ getTimestamp());
		producer.start();

		// 检查各线程的运行情况
		while (!Thread.currentThread().isInterrupted()
				&& Thread.currentThread().isAlive()) {
			checkAndRestartProducer();

			if (RevertSyncContext.isStopped()) {
				threadPool.shutdown();

				break;
			}

			try {// 休眠三秒后，再做下一轮巡检
				log.debug("管理线程[" + Thread.currentThread().getName()
						+ "]进入休眠状态！");

				TimeUnit.SECONDS.sleep(3L);

				log.debug("管理线程[" + Thread.currentThread().getName()
						+ "]醒来，开始巡检！");
			} catch (InterruptedException e) {
				log.error("管理线程[" + Thread.currentThread()
						+ "]在休眠时被中断，该中断将被忽略！", e);
			}
		}
	}

	/**
	 * 检查生产线程，如果有必要则重新启动生产线程
	 */
	private void checkAndRestartProducer() {
		// 如果生产者是被异常中断，则重新启动一个生产线程
		// 生产者线程不能随便重启，会导致数据重复同步。对于数据的状态要参阅相关文档！
		if (!producer.isAlive() || producer.isInterrupted()) {
			if (RevertSyncContext.isStopped()) {
				threadPool.shutdown();

				return;
			}

			log.info("生产者线程被异常中断，准备重新生产一个生产线程....");
			producer = new Thread(new ProducerThread(areaList), "Producer"
					+ getTimestamp());
			producer.start();
		}

	}

	private String getTimestamp() {
		return Long.toString(System.currentTimeMillis());
	}
}
