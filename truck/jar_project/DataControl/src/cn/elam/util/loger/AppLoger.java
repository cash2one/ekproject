package cn.elam.util.loger;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * 日志记录器工具
 * @author Ethan.Lam 2011-2-16
 */
public class AppLoger {

	static{
		DOMConfigurator.configure("configs/log4j.xml");
	}

	private static Logger logger = Logger.getLogger(AppLoger.class);

	
	/**
	 * 重新绑定配置文件
	 * @param xmlConfigPath
	 */
	public static void configureLogger(String xmlConfigPath){
		if(xmlConfigPath!=null&&!"".equals(xmlConfigPath))
			DOMConfigurator.configure(xmlConfigPath);
	}
	
	
	
	/**
	 * SQL执行记录器
	 * @param message
	 */
	public static void SQLLoggerInfo(String message) {
		Logger.getLogger("sql").info(message);
	}

	/**
	 * SQL错误记录
	 * @param message
	 * @param e
	 */
	public static void SQLLogerError(String message, Throwable e) {
		Logger.getLogger("sql").error(message);
		Logger.getLogger("sql").error(getTrace(e));
	}

	
	/**
	 * 业务操作日志
	 * @param message
	 */
	public static void BusinessLoggerInfo(String message) {
		Logger.getLogger("business").info(message);
	}

	/**
	 * 业务操作失败日志
	 * @param message
	 * @param e
	 */
	public static void BusinessLogerError(String message, Throwable e) {
		Logger.getLogger("business").error(message);
		Logger.getLogger("business").error(getTrace(e));
	}

	
	/**
	 * 程序运行日志（运行轨迹）
	 * @param message
	 */
	public static void RuningLoggerInfo(String message) {
		Logger.getLogger("appRunning").info(message);
	}

	/**
	 * 程序运行故障日志（运行轨迹）
	 * @param message
	 * @param e
	 */
	public static void RuningLoggerError(String message, Throwable e) {
		Logger.getLogger("appRunning").error(message);
		Logger.getLogger("appRunning").error(getTrace(e));
	}

	/**
	 * 跟踪错误信息
	 * @param t
	 * @return
	 */
	 static String getTrace(Throwable t) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		t.printStackTrace(writer);
		StringBuffer buffer = stringWriter.getBuffer();
		return buffer.toString();
	}

	public static void main(String... m) {
		DOMConfigurator.configure("configs/log4j.xml");
		RuningLoggerInfo("测试是否可行");
	}

}
