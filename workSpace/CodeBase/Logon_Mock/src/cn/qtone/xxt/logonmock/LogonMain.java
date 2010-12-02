package cn.qtone.xxt.logonmock;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import static java.util.concurrent.TimeUnit.SECONDS;

import cn.qtone.xxt.base.dao.comom.BaseDao;
import cn.qtone.xxt.base.dao.comom.DBConnector;

public class LogonMain {

	static String CALL_PROCEDURE = "";
	static String COUNT_SQL = "";

	long startTime,endTime;
	
	ExecutorService pool = null;

	Map<String, Boolean> threadsPoolStatus;

	public LogonMain() {
		COUNT_SQL = " SELECT COUNT(*) FROM " + Config.TEMP_TABLE;
		CALL_PROCEDURE = "{ call pro_xxt_synless_login(?) }";
		pool = Executors.newFixedThreadPool(Config.POST_THREAD_NUM);
		threadsPoolStatus = new HashMap<String, Boolean>();
		this.startTime = System.currentTimeMillis();
	}

	public void excuteThread(String name, Thread thread) {
		this.pool.execute(thread);
		threadsPoolStatus.put(name, Boolean.FALSE);
		AppLoger.getRuningLogger().info("已成功启用线程 [ " + name + " ] 进行模拟登录！");
	}

	public void appendFinishedState(String name) {
		if (threadsPoolStatus.containsKey(name))
			threadsPoolStatus.put(name, Boolean.TRUE);
		
		if(this.isAllSubThreadFinished())
			this.endTime = System.currentTimeMillis();
	}

	public void shutDownThreadPool() {
		this.pool.shutdown();
		AppLoger.getRuningLogger().info("程序已经运行完成，并已关闭所有的POST线程。"
				+ new Date().toLocaleString()+"，[开始时间："+new Date(startTime)+",总耗时："+((endTime-startTime)/1000)+" 秒。]");
		
	}

	
	public boolean isAllSubThreadFinished(){
		for(Boolean current:this.threadsPoolStatus.values())
		 if(current.equals(Boolean.FALSE))
			 return false;
		return true;
	}
	
	
	/**
	 * 找出需要登录的用户，把数据插入到对应的中间表中
	 */
	public boolean perpare() {
		long startTime = System.currentTimeMillis();
		StringBuffer createTempRecordSql = new StringBuffer();
		Connection conn = null;
		CallableStatement cs = null;
		try {
			conn = DBConnector.getConnection(Config.POOL_NAME);
			AppLoger.getRuningLogger().info("1、准备生成中间表的记录！");
			cs = conn.prepareCall(CALL_PROCEDURE);
			cs.registerOutParameter(1, Types.NUMERIC);
			cs.execute();
			int resultCode = cs.getInt(1);
			if (Config.ISDEUG)
				AppLoger.getRuningLogger().info("存储过程返回结果为：" + resultCode);
			if (resultCode == 100) {
				AppLoger.getRuningLogger().info("2、已成功生成中间表的记录,耗时:"+(System.currentTimeMillis()-startTime)/1000+"秒。");
				return true;
			} else {
				AppLoger.getRuningLogger().info("2、生成中间表的记录失败！");
				return false;
			}
		} catch (Exception e) {
//			e.printStackTrace();
			AppLoger.getSimpleErrorLogger().info(e.getMessage());
			return false;
		} finally {
			try {
				if(cs!=null)
				  cs.close();
				if(conn!=null)
				  conn.close();
			} catch (SQLException e) {
			}
		}
	}

	public int countRecords() {
		BaseDao dao = null;
		ResultSet rs = null;
		try {
			dao = new BaseDao(Config.POOL_NAME);
			rs = dao.query(COUNT_SQL);
			int records = 0;
			while (rs != null && rs.next()) {
				records = rs.getInt(1);
			}
			return records;
		} catch (Exception e) {
			AppLoger.getSimpleErrorLogger().info(e.getMessage());
			return 0;
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
			}
			dao.close();
		}
	}

	public int getMaxOrMinRecord(boolean isMax) {
		BaseDao dao = null;
		ResultSet rs = null;
		try {
			dao = new BaseDao(Config.POOL_NAME);
			if (isMax)
				rs = dao.query("select max(id) from " + Config.TEMP_TABLE);
			else
				rs = dao.query("select min(id) from " + Config.TEMP_TABLE);
			int records = 0;
			while (rs != null && rs.next()) {
				records = rs.getInt(1);
			}
			return records;
		} catch (Exception e) {
			AppLoger.getSimpleErrorLogger().info(e.getMessage());
			return 0;
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
			}
			dao.close();
		}
	}

	/**
	 * 
	 * 程序主入口
	 * 
	 * @param srg
	 */
	public static void main(String... srg) {

		AppLoger.getRuningLogger().info("-----------------------------------------------------------------------------------------");
		AppLoger.getRuningLogger().info("[INFO-LOGON APP-MAIN:]启动同步课堂用户模拟登录程序");
		AppLoger.getRuningLogger().info("[INFO-LOGON APP-MAIN:]程序配置：" + "[线程数:" + Config.POST_THREAD_NUM
				+ ",批处理数:" + Config.RECORDS_BATCH_LOADNUM + "]");
		AppLoger.getRuningLogger().info("-----------------------------------------------------------------------------------------");

		LogonMain main = new LogonMain();
		if (main.perpare()) {
			int allACountNums = main.countRecords();
			// 根据数据量的大小 ， 初始话线程的数量，分配对应的子线程进行批处理
			int maxRecordId = main.getMaxOrMinRecord(true);
			int minRecordId = main.getMaxOrMinRecord(false);

			// 分配线程
			if (allACountNums > 0) {
				int threadHanlderAcountNums = allACountNums
						/ Config.POST_THREAD_NUM;
				int sleepTime = 100;
				PostThread postThread = null;
				int startId = 0;
				int endId = 0;
				int leftRecords = allACountNums%Config.POST_THREAD_NUM;
				
				for (int threadSeq = 1; threadSeq <= Config.POST_THREAD_NUM; threadSeq++) {
					startId = minRecordId + (threadSeq - 1)
							* threadHanlderAcountNums;
					endId = minRecordId + threadSeq * threadHanlderAcountNums;
					
					//最后的线程或需要处理更多
					if(threadSeq==Config.POST_THREAD_NUM&Config.POST_THREAD_NUM>1)
					  endId+=leftRecords;	
						
					postThread = new PostThread(main, threadSeq, startId,
							endId, sleepTime);
					
					main.excuteThread(threadSeq + "", postThread);
				}
              
				appendThreadChecker(main,5);
			}

		}
	}

	
	/**
	 * 加上守护线程，监控完成情况
	 * @param app
	 * @param checkTimeSeconds
	 */
	public static void appendThreadChecker(LogonMain app, int checkTimeSeconds) {
		final ScheduledExecutorService scheduler = Executors
				.newScheduledThreadPool(2);

		ThreadProceStateCheck checker = new ThreadProceStateCheck(app);

		// 1秒钟后运行，并每隔2秒运行一次
		final ScheduledFuture<?> checkHandle = scheduler.scheduleAtFixedRate(
				checker, 1, checkTimeSeconds, SECONDS);

	}

}

class ThreadProceStateCheck extends Thread {

	LogonMain logonApp;

	public ThreadProceStateCheck(LogonMain logonApp) {
		this.logonApp = logonApp;
	}

    public void run(){
        if(logonApp.isAllSubThreadFinished()){
        	AppLoger.getRuningLogger().info("[INFO-LOGON APP-MAIN:] 模拟登录程序所有子线程运行完成。");
        	logonApp.shutDownThreadPool();
        }else{
        	AppLoger.getRuningLogger().info("[INFO-LOGON APP-MAIN:] 模拟登录程序还有子线程未运行完成，请继续等待！");
        }
    }	
    	
	
}