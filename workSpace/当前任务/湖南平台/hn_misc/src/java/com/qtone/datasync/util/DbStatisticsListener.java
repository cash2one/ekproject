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
			strBuf.append("[当前活跃连接数]:").append(sp.getActiveConnectionCount()).append("\n");
			strBuf.append("[当前可用连接数]：").append(sp.getAvailableConnectionCount()).append("\n");
			strBuf.append("[连接池中连接总数]:").append(sp.getConnectionCount()).append("\n");
			strBuf.append("[连接池允许的最大连接数]:").append(sp.getMaximumConnectionCount()).append("\n");
			strBuf.append("[离线(Offline)连接数]:").append(sp.getOfflineConnectionCount()).append("\n");
			strBuf.append("[提供服务的次数]:").append(sp.getServedCount()).append("\n");
			strBuf.append("[拒绝连接的次数]:").append(sp.getRefusedCount()).append("\n");
			strBuf.append("[连接池创建时间]:").append(sp.getDateStarted()).append("\n");
			strBuf.append("[当前快照产生时间]:").append(sp.getSnapshotDate()).append("\n");
			strBuf.append("---------------------------- END ----------------------------");
			
			log.info(strBuf.toString());
		} catch (ProxoolException e) {
			log.error(e);
		}catch(Throwable t){
			log.error(t);
		}		
	}

}
