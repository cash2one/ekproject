package com.qtone.datasync.misc.client;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qtone.datasync.bean.AreaBean;
import com.qtone.datasync.misc.util.MyThreadPool;
import com.qtone.datasync.misc.util.RevertSyncContext;

/**
 * ����������������̡߳������̼߳��̳߳�
 * 
 * @author ���ڷ� 2009-06-01
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
			log.error("������Ϣ�б�Ϊ�գ������߳��޷�����������");
			return;
		}

		// �����������������߳�
		producer = new Thread(new ProducerThread(areaList), "Producer"
				+ getTimestamp());
		producer.start();

		// �����̵߳��������
		while (!Thread.currentThread().isInterrupted()
				&& Thread.currentThread().isAlive()) {
			checkAndRestartProducer();

			if (RevertSyncContext.isStopped()) {
				threadPool.shutdown();

				break;
			}

			try {// ���������������һ��Ѳ��
				log.debug("�����߳�[" + Thread.currentThread().getName()
						+ "]��������״̬��");

				TimeUnit.SECONDS.sleep(3L);

				log.debug("�����߳�[" + Thread.currentThread().getName()
						+ "]��������ʼѲ�죡");
			} catch (InterruptedException e) {
				log.error("�����߳�[" + Thread.currentThread()
						+ "]������ʱ���жϣ����жϽ������ԣ�", e);
			}
		}
	}

	/**
	 * ��������̣߳�����б�Ҫ���������������߳�
	 */
	private void checkAndRestartProducer() {
		// ����������Ǳ��쳣�жϣ�����������һ�������߳�
		// �������̲߳�������������ᵼ�������ظ�ͬ�����������ݵ�״̬Ҫ��������ĵ���
		if (!producer.isAlive() || producer.isInterrupted()) {
			if (RevertSyncContext.isStopped()) {
				threadPool.shutdown();

				return;
			}

			log.info("�������̱߳��쳣�жϣ�׼����������һ�������߳�....");
			producer = new Thread(new ProducerThread(areaList), "Producer"
					+ getTimestamp());
			producer.start();
		}

	}

	private String getTimestamp() {
		return Long.toString(System.currentTimeMillis());
	}
}
