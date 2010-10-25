package cn.qtone.xxt.csop.util;

import java.util.Date;

public class CsopLog {

	public static void debug(String...logs){
		messageHandle(MessageType.DEBUG,logs); 
	}
	
	public static void info(String...logs){
		messageHandle(MessageType.INFO,logs); 
	}
	
	public static void error(String...logs){
		messageHandle(MessageType.ERROR,logs); 
	}
	
	static void messageHandle(MessageType type,String...messages){
		System.out.print("[CSOP-"+(type.equals(MessageType.DEBUG)?"DEBUG ":(type.equals(MessageType.INFO)?"INFO ":"ERROR ")));
		System.out.print(new Date(System.currentTimeMillis()).toString()+" ]:");
		if(messages!=null&&messages.length>1){
			for(String log:messages){
        		System.out.print(log+",");
			}
			System.out.println();
		}else{
			System.out.println(messages[0]);
        }     
	}
	
}
enum MessageType{
	DEBUG,
	INFO,
	ERROR
}