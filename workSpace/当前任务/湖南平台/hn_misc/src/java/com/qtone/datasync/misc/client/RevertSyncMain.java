package com.qtone.datasync.misc.client;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qtone.datasync.MainClass;
import com.qtone.datasync.bean.AreaBean;
import com.qtone.datasync.dao.UtilDao;

public class RevertSyncMain {
	private final static Log log = LogFactory.getLog(MainClass.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UtilDao dao = new UtilDao();
		
		log.info("清理数据……");
		if(!dao.cleanCustomerInfo()){
			log.fatal("无法正常清理数据，程序退出！");
			System.exit(1);
		}
		
		List<AreaBean> list = dao.getAreaList();

		if (list == null || list.size() == 0) {
			throw new IllegalArgumentException("不能正常获取各地区信息，无法启动同步程序！");
		}

		// 启动管理线程，准备同步
		Thread t = new Thread(new ManagerThread(list));
		t.start();
	}

}
