package com.qtone.datasync.xxt.dp;

/**
 * @author 杨腾飞	2009-9-2 
 *
 */
public interface IMiscToXxtFunctionTestHelper {
	/**
	 * 准备DB，包括清理和新建数据记录
	 */
	public void prepareDb();

	/**
	 * 向模拟程序发起同步请求
	 */
	public void doSync();

	/**
	 * 检查发起请求的报文数目与接收到的记录数目
	 * 如果相同就返回true
	 * 
	 * @return 
	 */
	public boolean checkRequestCount();

	/**
	 * 检查对不存在于平台上的记录的处理结果
	 * 
	 * @return
	 */
	public boolean checkNonexistsUserDealResult();

	/**
	 * 检查对存在于平台的用户的处理结果
	 * 包括了对定购收费业务和免费业务的分别处理
	 * @return
	 */
	public boolean checkExistsUserDealRequest();
	
}
