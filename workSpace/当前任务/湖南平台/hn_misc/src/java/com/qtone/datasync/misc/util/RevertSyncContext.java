package com.qtone.datasync.misc.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qtone.datasync.util.FileUtil;

/**
 * @author ���ڷ� 2009-9-9 ����ͬ��������
 */
public class RevertSyncContext {
	private static final Log log = LogFactory.getLog(RevertSyncContext.class);

	public static final String MISC_SERVER_ADDR;
	public static final String THREAD_POOL_CORE_SIZE;
	public static final String THREAD_POOL_MAX_SIZE;
	public static final String THREAD_KEEP_ALIVE_TIME;
	
	public static final long SCAN_INTERVAL;

	static {
		BufferedInputStream in = null;
		Properties prop = new Properties();
		try {
			in = new BufferedInputStream(new FileInputStream(FileUtil
					.getFilePath("datasync.properties")));
			prop.load(in);

			MISC_SERVER_ADDR = StringUtils
					.trim(prop.getProperty("misc-server"));
			THREAD_POOL_CORE_SIZE = StringUtils.trim(prop.getProperty(
					"threadpool-core-size", String.valueOf(Runtime.getRuntime()
							.availableProcessors())));
			THREAD_POOL_MAX_SIZE = StringUtils.trim(prop.getProperty(
					"threadpool-max-size", String.valueOf(Runtime.getRuntime()
							.availableProcessors() + 1)));
			THREAD_KEEP_ALIVE_TIME = StringUtils.trim(prop.getProperty(
					"threadpool-keep-alive-time", "30"));
			SCAN_INTERVAL = NumberUtils.toLong(StringUtils.trim(prop.getProperty("scan-interval","30")));
		} catch (FileNotFoundException e) {
			log.error("�Ҳ�������ͬ�������ļ�", e);
			throw new RuntimeException("�Ҳ��������ļ����޷�����Ӧ��");
		} catch (IOException e) {
			log.error("�޷��������������ļ�", e);
			throw new RuntimeException("�޷��������������ļ�");
		} finally {
			IOUtils.closeQuietly(in);
		}

		// ��֤���в������������
		Validate.notEmpty(MISC_SERVER_ADDR);
		Validate.notEmpty(THREAD_POOL_CORE_SIZE);
		Validate.notEmpty(THREAD_POOL_MAX_SIZE);
		Validate.notEmpty(THREAD_KEEP_ALIVE_TIME);
	}
	
	/**
	 * �Ƿ���Ҫ�ر�Ӧ�ã���ɨ���̣߳�Producer���������ɨ�蹤��֮�󣬽������ø�״̬λ��
	 * �����̶߳��ڼ���״̬λ�����ж��Ƿ���ҪֹͣӦ�á�
	 */
	private static volatile boolean needToStop = false;
	
	public static void stop(){
		needToStop = true;
	}
	
	public static boolean isStopped(){
		return needToStop;
	}
}
