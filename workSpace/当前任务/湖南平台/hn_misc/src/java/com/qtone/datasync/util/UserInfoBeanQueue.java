package com.qtone.datasync.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import com.qtone.datasync.xxt.server.bean.XxtRequestBean;

public class UserInfoBeanQueue {
	/**
	 * 已经同步的信息的总数
	 */
	public static volatile AtomicLong syncedBeanCount = new AtomicLong(0);

	/**
	 * 产生的实体信息总数
	 */
	public static volatile AtomicLong producedBeanCount = new AtomicLong(0);

	/**
	 * 等待被同步的用户信息队列
	 */
	private static final BlockingQueue<XxtRequestBean> userInfoQueue = new ArrayBlockingQueue<XxtRequestBean>(
			Context.BEAN_QUEUE_SIZE);

	/**
	 * 获取一个实体信息Bean
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public static XxtRequestBean getUserInfo() throws InterruptedException {
		return userInfoQueue.take();//30, TimeUnit.SECONDS
	}

	/**
	 * 添加用户信息实体到队列中
	 * 
	 * @param userInfo
	 * @throws InterruptedException
	 */
	public static void addUserInfo(XxtRequestBean userInfo)
			throws InterruptedException {
		userInfoQueue.offer(userInfo, 30, TimeUnit.SECONDS);
	}

	public static Long incrementProducedBeanCount() {
		return producedBeanCount.incrementAndGet();
	}

	public static Long incrementSyncedBeanCount() {
		return syncedBeanCount.incrementAndGet();
	}
}
