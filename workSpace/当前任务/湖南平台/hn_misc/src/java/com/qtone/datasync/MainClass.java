package com.qtone.datasync;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qtone.datasync.bean.AreaBean;
import com.qtone.datasync.dao.UtilDao;
import com.qtone.datasync.misc.client.ManagerThread;
import com.qtone.datasync.util.Context;

/**
 * ��ڷ�������ʼ�����������̣߳�
 * 
 * @author ���ڷ� 2009-6-8
 */
public class MainClass {
	private final static Log log = LogFactory.getLog(MainClass.class);
	
	public static void main(String[] args) {
		log.info("��ǰ�����ID��"+Context.SYNC_SEQ);
		
		UtilDao dao = new UtilDao();
		
		log.info("�������ݡ���");
		if(!dao.cleanCustomerInfo()){
			log.fatal("�޷������������ݣ������˳���");
			System.exit(1);
		}
		
		List<AreaBean> list = dao.getAreaList();

		if (list == null || list.size() == 0) {
			throw new RuntimeException("����������ȡ��������Ϣ���޷�����ͬ������");
		}

		// ���������̣߳�׼��ͬ��
		Thread t = new Thread(new ManagerThread(list));
		t.start();
	}
}
