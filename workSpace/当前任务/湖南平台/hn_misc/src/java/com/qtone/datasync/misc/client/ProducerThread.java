package com.qtone.datasync.misc.client;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qtone.datasync.bean.AreaBean;
import com.qtone.datasync.misc.client.dao.XxtRequestDao;
import com.qtone.datasync.misc.util.RevertSyncContext;

/**
 * �����ݿ��л�ȡ��Ҫͬ������Ϣ��������Щ��Ϣ���뵽������.
 * ������ݿ��еȴ�ͬ��������ȫ����������ɣ�����̻߳��Զ�ֹͣ������Ӧ�ó��������ģ�Context����������ص�״̬λ��
 * ������߳���Ϊ�쳣�жϣ���Ҳ������״̬λ���Ա�Ѳ���߳�������������
 * 
 * @author ���ڷ� 2009-06-01
 */
public class ProducerThread implements Runnable {
	private final Log log = LogFactory.getLog(ProducerThread.class);

	/**
	 * ������Ϣ
	 */
	private List<AreaBean> areaInfo;

	private XxtRequestDao dao;

	public ProducerThread(final List<AreaBean> areaInfo) {
		this.areaInfo = areaInfo;

		dao = new XxtRequestDao();
	}

	public void run() {
		log.info("[" + Thread.currentThread().getName() + "]�߳�����....");

		try {
			/*
			 * while (true) {}������ѭ����Ϊ����ѭ����ͬ������Ľű������ж�ʱͬ��
			 */
			while (true) {
				// FIXME:��CP���в��ԣ��ѶԵ�����ɨ����ʱ����
				for (int i = 0; i < areaInfo.size(); i++) {
					// TODO:��ɨ��һ������ʱ��Ӧ����ǰ���õ��������д�������ͬ��״̬��adcdeal_suc = 3 and adcdeal_result = '����ͬ��'��
					// ״̬�����ݶ����и��£��Ա�����һ��ͬ��ʱ�ܹ����д���
					
					dao.queryUserInfoByArea(areaInfo.get(i));
					
					try {
						//ÿ������ɨ��һ�κ���ͣ������
						TimeUnit.SECONDS.sleep(3L);
					} catch (InterruptedException e) {
					}
				}
				
				//��Ӷ�CP�û�ҵ�����˶��Ĵ���
				dao.queryCpUserInfo();
				
				try {
					//������е�����һ��ɨ�����ͣ������
					TimeUnit.SECONDS.sleep(RevertSyncContext.SCAN_INTERVAL);
				} catch (InterruptedException e) {
				}
			}
		} finally {
			if (Thread.currentThread().isInterrupted()) {
				log.info("[" + Thread.currentThread().getName() + "]���ڱ��ж϶��˳���");
			} else {
				log.info("[" + Thread.currentThread().getName()
								+ "]��ɹ����������˳���");

				RevertSyncContext.stop();
			}

		}

	}
}
