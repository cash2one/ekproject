package cn.qtone.xxt.apps.web;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * ��־���������
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
		getBusinessLogger().info("ҵ��������־");
		getSimpleErrorLogger().info("Simple������־");
		getNormalErrorLogger().info("ƽ��������־");
		getSQLLogger().info("SQL������־");
	}

}
