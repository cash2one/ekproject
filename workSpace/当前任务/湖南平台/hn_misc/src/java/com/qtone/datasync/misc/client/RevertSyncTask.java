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
 * ��������ͬ���ӿ�
 * 
 * @author ���ڷ� 2009-05-19
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
	 * ��Miscͬ����Ϣ
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
				log.error("����ͬ��->��MISC������д������ʱ�����쳣", e);
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
				log.error("����ͬ��->��ȡMISC���������ؽ��ʱ�����쳣��", e);
			} finally {
				if (in != null) {
					in.close();
				}
			}

		} catch (IOException e) {
			log.error("����ͬ��->ͬ������ʱ�����쳣", e);
		} finally {
			if (urlconn != null)
				urlconn.disconnect();
		}

		return response;
	}

	public Object doSync() throws Exception {
		String reqMsg = XxtXmlHandler.toXxtRequestXml(userInfo);
		msgLog.fatal(reqMsg);// ��¼ͬ����־

		try {
			String retMsg = null;

			// ����ܹ��ɹ���ת�����ģ���ô���޸�����״̬������Misc����ͬ������
			// ��������Ϣ���뵽misc_order_relation
			if (reqMsg != null) {// && dao.addUserInfoToMiscOrderTab(userInfo)
				retMsg = syncOrderInfo(reqMsg);
			} else {
				log.error("�޷���ɷ���ͬ�����ݵ�׼�������ܷ�������");
				return null;
			}

			if (log.isDebugEnabled())
				log.debug("УѶͨ����ͬ��ʱ������������Ӧ���ģ�\n" + retMsg.toString());

			MiscRespBean bean = null;
			if (retMsg != null) {
				msgLog.fatal(retMsg); // ��¼MISC������־
				bean = XxtXmlHandler.parseMiscRespMsg(retMsg);
			}

			handleResponse(bean);
		} catch (Throwable t) {
			log.error(t);
		}

		return null;
	}

	/**
	 * �����ؽ�� 1.������ؽ����ʾ�����쳣���������еĲ������ָ��û��������״̬��ͬ��ʧ�ܣ���misc_order_relation
	 * 2.������ؽ������������Ҫ�������
	 * 
	 * @param bean
	 */
	private void handleResponse(MiscRespBean bean) {
		if (bean == null) {
			log.error("[����ͬ��]��Misc���صı���Ϊ�գ��޷���������");
			return;
		}

		if (!"0".equals(bean.getHRet()))//ͬ��ʧ��
			dao.changeStatusToFailure(bean);
		else//ͬ���ɹ�
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
	 * ֱ���ڱ���ȡ��ҵ��(Ҳ����ɾ���û���ϵ)
	 */
	private void cancelOrderLocal() {
		dao.cancelOrderLocal(userInfo);
	}

	/**
	 * �����û���¼�Ƿ���Ҫͬ����MISC��������
	 * 1.����(phone,stud1,tran),(phone,stud2,tran)���������(phone,stud1,tran)�˶�,(phone,stud2,tran)û���˶�ʱ��
	 * 	ֻ��Ҫɾ��һ���û���ϵ��¼������Ҫͬ����MISC��������
	 * @return
	 */
	private boolean needToSync() {
		//��CP����ͬ�������жϣ�ֱ��ͬ��
		if(userInfo.getIsCp() == 1)
			return true;
		
		int i = dao.countByPhoneAndTran(userInfo);
		
		return i == 0 ? true : false;
	}

}
