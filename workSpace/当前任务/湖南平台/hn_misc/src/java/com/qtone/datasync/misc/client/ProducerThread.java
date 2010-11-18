package com.qtone.datasync.misc.client;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qtone.datasync.bean.AreaBean;
import com.qtone.datasync.misc.client.dao.XxtRequestDao;
import com.qtone.datasync.misc.util.RevertSyncContext;

/**
 * 从数据库中获取需要同步的信息，并把这些信息放入到队列中.
 * 如果数据库中等待同步的数据全部被处理完成，则该线程会自动停止，关在应用程序上下文（Context）中设置相关的状态位。
 * 如果该线程因为异常中断，它也会设置状态位。以便巡检线程重新启动它。
 * 
 * @author 杨腾飞 2009-06-01
 */
public class ProducerThread implements Runnable {
	private final Log log = LogFactory.getLog(ProducerThread.class);

	/**
	 * 地区信息
	 */
	private List<AreaBean> areaInfo;

	private XxtRequestDao dao;

	public ProducerThread(final List<AreaBean> areaInfo) {
		this.areaInfo = areaInfo;

		dao = new XxtRequestDao();
	}

	public void run() {
		log.info("[" + Thread.currentThread().getName() + "]线程启动....");

		try {
			/*
			 * while (true) {}将无限循环改为单次循环，同步程序的脚本将进行定时同步
			 */
			while (true) {
				// FIXME:对CP进行测试，把对地区的扫描暂时屏蔽
				for (int i = 0; i < areaInfo.size(); i++) {
					// TODO:在扫描一个地区时，应该提前将该地区中所有处理正在同步状态（adcdeal_suc = 3 and adcdeal_result = '正在同步'）
					// 状态的数据都进行更新，以便于下一次同步时能够进行处理。
					
					dao.queryUserInfoByArea(areaInfo.get(i));
					
					try {
						//每个地区扫描一次后都暂停三秒钟
						TimeUnit.SECONDS.sleep(3L);
					} catch (InterruptedException e) {
					}
				}
				
				//添加对CP用户业务反向退订的处理
				dao.queryCpUserInfo();
				
				try {
					//完成所有地区的一次扫描后，暂停三分钟
					TimeUnit.SECONDS.sleep(RevertSyncContext.SCAN_INTERVAL);
				} catch (InterruptedException e) {
				}
			}
		} finally {
			if (Thread.currentThread().isInterrupted()) {
				log.info("[" + Thread.currentThread().getName() + "]由于被中断而退出！");
			} else {
				log.info("[" + Thread.currentThread().getName()
								+ "]完成工作，正常退出！");

				RevertSyncContext.stop();
			}

		}

	}
}
