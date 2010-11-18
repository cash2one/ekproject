package com.qtone.datasync.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import com.qtone.datasync.xxt.server.bean.XxtRequestBean;

public class UserInfoBeanQueue {
	/**
	 * �Ѿ�ͬ������Ϣ������
	 */
	public static volatile AtomicLong syncedBeanCount = new AtomicLong(0);

	/**
	 * ������ʵ����Ϣ����
	 */
	public static volatile AtomicLong producedBeanCount = new AtomicLong(0);

	/**
	 * �ȴ���ͬ�����û���Ϣ����
	 */
	private static final BlockingQueue<XxtRequestBean> userInfoQueue = new ArrayBlockingQueue<XxtRequestBean>(
			Context.BEAN_QUEUE_SIZE);

	/**
	 * ��ȡһ��ʵ����ϢBean
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public static XxtRequestBean getUserInfo() throws InterruptedException {
		return userInfoQueue.take();//30, TimeUnit.SECONDS
	}

	/**
	 * ����û���Ϣʵ�嵽������
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
