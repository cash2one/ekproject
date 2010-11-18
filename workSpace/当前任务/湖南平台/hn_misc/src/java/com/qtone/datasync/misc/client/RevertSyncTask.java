package com.qtone.datasync.misc.client;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qtone.datasync.misc.client.bean.MiscRespBean;
import com.qtone.datasync.misc.client.dao.XxtRequestDao;
import com.qtone.datasync.xxt.server.bean.XxtRequestBean;

/**
 * 反向数据同步接口
 * 
 * @author 杨腾飞 2009-05-19
 */
public class RevertSyncTask implements Runnable {
	private final Log log = LogFactory.getLog(RevertSyncTask.class);

	private final Log msgLog = LogFactory.getLog("msg.misc_client");

	private XxtRequestBean userInfo;

	private XxtRequestDao dao;

	private final URL miscServerUrl;

	public RevertSyncTask(URL miscServerUrl, XxtRequestBean userInfo) {
		this.miscServerUrl = miscServerUrl;
		this.userInfo = userInfo;

		dao = new XxtRequestDao();
	}

	/**
	 * 向Misc同步信息
	 * 
	 * @param xmlMsg
	 * @return
	 */
	private String syncOrderInfo(String msg) {
		HttpURLConnection urlconn = null;
		String response = null;

		try {
			urlconn = (HttpURLConnection) miscServerUrl.openConnection();

			urlconn.setRequestProperty("content-type", "text/plain");
			urlconn.setRequestMethod("POST");
			urlconn.setDoInput(true);
			urlconn.setDoOutput(true);

			BufferedOutputStream out = null;
			try {
				out = new BufferedOutputStream(urlconn.getOutputStream());
				out.write(msg.getBytes("UTF8"));
			} catch (IOException e) {
				log.error("反向同步->向MISC服务器写入数据时发生异常", e);
			} finally {
				if (out != null) {
					out.flush();
					out.close();
				}
			}

			BufferedReader in = null;
			try {
				in = new BufferedReader(new InputStreamReader(urlconn
						.getInputStream()));
				StringBuilder sb = new StringBuilder();
				char[] charBuf = new char[1024];
				int len = -1;

				while ((len = in.read(charBuf)) > -1) {
					sb.append(charBuf, 0, len);
				}

				response = sb.toString();
			} catch (IOException e) {
				log.error("反向同步->读取MISC服务器返回结果时发生异常！", e);
			} finally {
				if (in != null) {
					in.close();
				}
			}

		} catch (IOException e) {
			log.error("反向同步->同步数据时发生异常", e);
		} finally {
			if (urlconn != null)
				urlconn.disconnect();
		}

		return response;
	}

	public Object doSync() throws Exception {
		String reqMsg = XxtXmlHandler.toXxtRequestXml(userInfo);
		msgLog.fatal(reqMsg);// 记录同步日志

		try {
			String retMsg = null;

			// 如果能够成功地转化报文，那么就修改数据状态，并向Misc发起同步请求
			// 将数据信息与入到misc_order_relation
			if (reqMsg != null) {// && dao.addUserInfoToMiscOrderTab(userInfo)
				retMsg = syncOrderInfo(reqMsg);
			} else {
				log.error("无法完成反向同步数据的准备，不能发起请求！");
				return null;
			}

			if (log.isDebugEnabled())
				log.debug("校讯通反向同步时，服务器的响应报文：\n" + retMsg.toString());

			MiscRespBean bean = null;
			if (retMsg != null) {
				msgLog.fatal(retMsg); // 记录MISC返回日志
				bean = XxtXmlHandler.parseMiscRespMsg(retMsg);
			}

			handleResponse(bean);
		} catch (Throwable t) {
			log.error(t);
		}

		return null;
	}

	/**
	 * 处理返回结果 1.如果返回结果表示操作异常，则撤消已有的操作：恢复用户表的数据状态（同步失败）、misc_order_relation
	 * 2.如果返回结果正常，则不需要任务操作
	 * 
	 * @param bean
	 */
	private void handleResponse(MiscRespBean bean) {
		if (bean == null) {
			log.error("[反向同步]：Misc返回的报文为空，无法正常处理！");
			return;
		}

		if (!"0".equals(bean.getHRet()))//同步失败
			dao.changeStatusToFailure(bean);
		else//同步成功
			dao.cancelOrderLocal(userInfo);
	}

	public void run() {
		try {
			if (needToSync())
				doSync();
			else
				cancelOrderLocal();
		} catch (Exception e) {
			log.error(e);
		}
	}

	/**
	 * 直接在本地取消业务(也就是删除用户关系)
	 */
	private void cancelOrderLocal() {
		dao.cancelOrderLocal(userInfo);
	}

	/**
	 * 检查该用户记录是否需要同步到MISC服务器：
	 * 1.对于(phone,stud1,tran),(phone,stud2,tran)的情况，当(phone,stud1,tran)退订,(phone,stud2,tran)没有退订时，
	 * 	只需要删除一条用户关系记录，不需要同步到MISC服务器。
	 * @return
	 */
	private boolean needToSync() {
		//对CP反向同步不做判断，直接同步
		if(userInfo.getIsCp() == 1)
			return true;
		
		int i = dao.countByPhoneAndTran(userInfo);
		
		return i == 0 ? true : false;
	}

}
