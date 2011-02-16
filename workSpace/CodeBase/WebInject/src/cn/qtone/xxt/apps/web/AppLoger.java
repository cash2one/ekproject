package cn.qtone.xxt.apps.web;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * 日志输出控制器
 * @author Ethan.Lam  2011-2-16
 *
 */
public class AppLoger {

	private static Logger logger = Logger.getLogger(AppLoger.class);

	public static Logger getSQLLogger() {
		return Logger.getLogger("sql");
	}

	public static Logger getBusinessLogger() {
		return Logger.getLogger("business");
	}

	public static Logger getSimpleErrorLogger() {
		return Logger.getLogger("simpleError");
	}

	public static Logger getNormalErrorLogger() {
		return Logger.getLogger("normalError");
	}

	public static Logger getRuningLogger() {
		return Logger.getLogger("appRunning");
	}

	
	public static void main(String... m) {
		DOMConfigurator.configure("configs/log4j.xml");
		getBusinessLogger().info("业务运行日志");
		getSimpleErrorLogger().info("Simple运行日志");
		getNormalErrorLogger().info("平常运行日志");
		getSQLLogger().info("SQL运行日志");
	}

}
