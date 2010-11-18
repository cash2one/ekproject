package com.qtone.datasync.misc;

public class Constraints {
	/**
	 * Misc正向同步的消息类型
	 */
	public static final String MSG_TYPE_REQUEST = "SyncOrderRelationReq";

	/**
	 * Misc正向同步响应消息类型
	 */
	public static final String MSG_TYPE_RESPONSE = "SyncOrderRelationResp";

	/**
	 * 反向订购同步请求
	 */
	public static final String MSG_TYPE_SUBSCRIBE = "SubscribeServiceReq";

	/**
	 * 反向取消同步请求
	 */
	public static final String MSG_TYPE_UNSUBSCRIBE = "UnSubscribeServiceReq";

	/**
	 * Misc同步方法
	 */
	public static final String MISC_ENDPOINT_REFERENCE = "";

	/**
	 * 反向订购同步方法命名空间
	 */
	public static final String ACTION_SUBSCRIBE_NS = "";

	/**
	 * 反向订购同步方法名称
	 */
	public static final String ACTION_SUBSCRIBE_NAME = "";

	/**
	 * 反向取消同步方法命名空间
	 */
	public static final String ACTION_UNSUBSCRIBE_NS = "";

	/**
	 * 反向取消同步方法名称
	 */
	public static final String ACTION_UNSUBSCRIBE_NAME = "";

	/**
	 * 校讯通平台SPID
	 */
	public static final String SP_ID = "918522";
	
	/**
	 * CP平台SP ID
	 */
	public static final String CP_SP_ID = "818324";
	
	/**
	 * 协议的版本
	 */
	public static final String VERSION = "1.5.0";
	
	/**
	 * 校讯通平台 设备类型	SP
	 */
	public static final String DEVICE_TYPE = "400";

	/**
	 * 校讯通平台 设备ID
	 */
	public static final String DEVICE_ID = SP_ID;

	/**
	 * Misc设备类型
	 */
	public static final String MISC_DEVICE_TYPE = "0";

	/**
	 * Misc设备ID
	 */
	public static final String MISC_DEVICE_ID = "0023";
	
}
