package web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import qtone.generator.util.BaseCfg;

/**
 * 测试文件上传
 * 
 * @author Ethan.Lam 2011-2-11
 * 
 */
public class Upload extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(ServletInputStream) request.getInputStream(),"utf-8"));
		String line = null;
		String fileName = System.currentTimeMillis()+".xml";
		
		String dirPath=BaseCfg.CFG_PATH+"/templates/";
		File file = new File(dirPath+fileName);
		file.createNewFile();
		
		OutputStreamWriter p =new OutputStreamWriter(new FileOutputStream(file),"utf-8");
		StringBuilder cfgContent = new StringBuilder();
		while ((line = br.readLine()) != null) {
			cfgContent.append(line);
			System.out.println(line);
			 p.write(line);
		}
		 p.flush();
		 p.close();
		
		final List<String> recFiles = new ArrayList<String>();
		recFiles.add(fileName); 
		
		
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		// out.write("<script>window.location.href='/"+(!BaseCfg.APP_CONTEXT.equals("")?"/"+BaseCfg.APP_CONTEXT:"")+"/index.jsp'</script>");
		StringBuffer urls = new StringBuffer();
		String ip = BaseCfg.WWW;
		for (String fileCfg : recFiles) {
			urls.append(ip).append(
					""
							+ (!BaseCfg.APP_CONTEXT.equals("") ? "/"
									+ BaseCfg.APP_CONTEXT : "")
							+ "/codeTemplators/entity.jsp?cfg=").append(fileCfg);
			urls.append(";").append(ip).append(
					(!BaseCfg.APP_CONTEXT.equals("") ? "/"
							+ BaseCfg.APP_CONTEXT : "")
							+ "/codeTemplators/mapper.jsp?cfg=").append(fileCfg);
			urls.append(";").append(ip).append(
					(!BaseCfg.APP_CONTEXT.equals("") ? "/"
							+ BaseCfg.APP_CONTEXT : "")
							+ "/codeTemplators/mapperXml.jsp?cfg=")
					.append(fileCfg);
			urls.append(";").append(ip).append(
					(!BaseCfg.APP_CONTEXT.equals("") ? "/"
							+ BaseCfg.APP_CONTEXT : "")
							+ "/codeTemplators/business.jsp?cfg=").append(fileCfg);
		}
		out.write(urls.toString());
		out.close();
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
