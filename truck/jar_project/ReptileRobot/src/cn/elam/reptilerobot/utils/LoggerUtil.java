package cn.elam.reptilerobot.utils;

/**
 * 
 * @author Ethan.Lam  2011-11-16
 *
 */
public class LoggerUtil {

	public static void debug(String handler, Object... args) {
//		p(handler,args);
	}

	public static void info(String handler, Object... args) {
        p(handler,args);
	}

	
	public static void p(String handler, Object... args){
		 String msg = handler+" : ";
	     for(Object par:args)
	    	 msg+=par+" , ";
	     System.out.println(msg);
	}
	
	public static void error(String handler, String message, Throwable t) {
		 p(handler,message);
	}

}
