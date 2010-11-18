package com.qtone.datasync.misc.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 模拟MISC服务器，将收到的报文写入到文本文件中，并返回处理成功的报文。
 * 
 * @author 杨腾飞
 */
public class MiscServerMock extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Log log = LogFactory.getLog(getClass());
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			in = new BufferedReader(new InputStreamReader(req.getInputStream()));

			StringBuilder msg = new StringBuilder();
			String line = null;
			while ((line = in.readLine()) != null)
				msg.append(line);

			MiscHandlerMock handler = new MiscHandlerMock();
			String result = handler.handle(msg.toString());

			out = resp.getWriter();
			out.write(result);
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (in != null)
				in.close();

			if (out != null){
				out.flush();
				out.close();
			}
		}
	}

}
