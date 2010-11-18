package com.qtone.datasync.misc.mock;

/**
 * @author 杨腾飞 2009-9-7 模拟MISC报文发送器接口
 */
public interface IMiscSenderMocker {

	/**
	 * 直接发送报文，报文是已经组织好的。
	 * 
	 * @param msgFile
	 *            报文文件，一行一个报文
	 * @param retFeil
	 *            报文返回将记录于这个文件
	 * @return
	 */
	public String sendMsg(String msgFile, String retFeil);

	/**
	 * 将传的参数#phone salemodalid#（不包含#号）进行分解，并且对解析相应的 模板生成一个报文，发送给XXT同步程序
	 * 
	 * @param msgFile 数据文件，#phone salemodalid#（不包含#号）
	 * @param tplFile 模板文件
	 * @param retFile 返回文件
	 * @return
	 */
	public String parseAndSend(String msgFile, String tplFile, String retFile);
}
