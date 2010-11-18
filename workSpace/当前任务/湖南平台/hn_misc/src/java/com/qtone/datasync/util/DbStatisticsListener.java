package com.qtone.datasync.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;
import org.logicalcobwebs.proxool.admin.SnapshotIF;
import org.logicalcobwebs.proxool.admin.StatisticsIF;
import org.logicalcobwebs.proxool.admin.StatisticsListenerIF;

public class DbStatisticsListener implements StatisticsListenerIF {
	private Log log = LogFactory.getLog(DbStatisticsListener.class);

	public void statistics(String alais, StatisticsIF stat) {
		try {
			SnapshotIF sp = ProxoolFacade.getSnapshot(Context.DB_ALIAS, true);
			
			StringBuilder strBuf = new StringBuilder();
			strBuf.append("\n");
			strBuf.append("----------------------------START----------------------------");
			strBuf.append("\n");
			strBuf.append("[��ǰ��Ծ������]:").append(sp.getActiveConnectionCount()).append("\n");
			strBuf.append("[��ǰ����������]��").append(sp.getAvailableConnectionCount()).append("\n");
			strBuf.append("[���ӳ�����������]:").append(sp.getConnectionCount()).append("\n");
			strBuf.append("[���ӳ���������������]:").append(sp.getMaximumConnectionCount()).append("\n");
			strBuf.append("[����(Offline)������]:").append(sp.getOfflineConnectionCount()).append("\n");
			strBuf.append("[�ṩ����Ĵ���]:").append(sp.getServedCount()).append("\n");
			strBuf.append("[�ܾ����ӵĴ���]:").append(sp.getRefusedCount()).append("\n");
			strBuf.append("[���ӳش���ʱ��]:").append(sp.getDateStarted()).append("\n");
			strBuf.append("[��ǰ���ղ���ʱ��]:").append(sp.getSnapshotDate()).append("\n");
			strBuf.append("---------------------------- END ----------------------------");
			
			log.info(strBuf.toString());
		} catch (ProxoolException e) {
			log.error(e);
		}catch(Throwable t){
			log.error(t);
		}		
	}

}
