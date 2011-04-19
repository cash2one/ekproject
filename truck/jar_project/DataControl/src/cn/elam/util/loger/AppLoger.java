package cn.elam.util.loger;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * 
 * 应用日志工具
 * 
 * @author Ethan.Lam 2011-2-16
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

	public static Logger getFailEmailLogger() {
		return Logger.getLogger("failEmail");
	}

	public static String getTrace(Throwable t) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		t.printStackTrace(writer);
		StringBuffer buffer = stringWriter.getBuffer();
		return buffer.toString();
	}

	public static void main(String... m) {
		DOMConfigurator.configure("configs/log4j.xml");
		getBusinessLogger().info("test");
	}

}
