package com.qtone.datasync.util;

public class GUtil {
	public static p(params){
		println params
	}
	
	public static p(tip,params){
		println "${tip}\t${params}"
	}
	
	/**
	 * ��ȡ����ͬ������ҵ���ĵ�ģ��
	 */
	public static String getSyncOrderTemplate(){
		def str = new File(GUtil.class.getClassLoader().getResource('resources/order_msg.xml').getPath()).getText()
		return str;
	}
	
	/**
	 * ��ȡ����ͬ��ȡ��ҵ���ģ��
	 */
	public static String getSyncCancelTemplate(){
		def str = new File(GUtil.class.getClassLoader().getResource('resources/cancel_msg.xml').getPath()).getText()
		return str;
	}
}