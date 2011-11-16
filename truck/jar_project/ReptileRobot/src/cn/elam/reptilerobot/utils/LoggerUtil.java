package cn.elam.reptilerobot.utils;

/**
 * 
 * @author Ethan.Lam  2011-11-16
 *
 */
public class LoggerUtil {

	public static void debug(String handler, Object... args) {
		p(handler,args);
	}

	public static void info(String handler, Object... args) {
        p(handler,args);
	}

	static void  p(String handler, Object... args){
		  System.out.print(handler+" : ");
	       for(Object par:args)
	          System.out.print(par+" , ");
	       System.out.println();
	}
	
	public static void error(String handler, String message, Throwable t) {

	}

}
