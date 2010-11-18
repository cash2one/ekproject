package com.qtone.datasync.xxt.server;

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
 * @author ÑîÌÚ·É	2009-8-13 
 *
 */
@SuppressWarnings("serial")
public class DefaultMiscRequestHandlerServlet extends HttpServlet {
	private DefaultMiscRequestHandler handler = new DefaultMiscRequestHandler();
	
	private Log log = LogFactory.getLog(DefaultMiscRequestHandlerServlet.class);
	
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

			String result = handler.SyncOrderRelationReq(msg.toString());

			out = resp.getWriter();
			out.write(result);
		}catch(Exception e){
			log.error(e);
		} finally {
			if (in != null)
				in.close();

			if (out != null)
				out.close();
		}
	}
	
}
