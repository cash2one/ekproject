package com.qtone.datasync.util;

public class GUtil {
	public static p(params){
		println params
	}
	
	public static p(tip,params){
		println "${tip}\t${params}"
	}
	
	/**
	 * 获取正向同步订购业务报文的模板
	 */
	public static String getSyncOrderTemplate(){
		def str = new File(GUtil.class.getClassLoader().getResource('resources/order_msg.xml').getPath()).getText()
		return str;
	}
	
	/**
	 * 获取反向同步取消业务的模板
	 */
	public static String getSyncCancelTemplate(){
		def str = new File(GUtil.class.getClassLoader().getResource('resources/cancel_msg.xml').getPath()).getText()
		return str;
	}
}