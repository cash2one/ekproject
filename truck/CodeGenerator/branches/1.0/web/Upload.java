package web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import qtone.generator.util.BaseCfg;

/**
 * 测试文件上传
 * 
 * @author Ethan.Lam 2011-2-11
 * 
 */
public class Upload extends HttpServlet {

	private Logger logger = Logger.getLogger(Upload.class);
	
	private static String MODEL = "new_model";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
        
		System.out.println("有新的文件被上传到服务器中："+new Date().toLocaleString());
		logger.info("有新的文件被上传到服务器中.....");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(
				(ServletInputStream) request.getInputStream(),"utf-8"));
		String line = null;
		
		String fileName =request.getHeader("FileName")+".xml";
		String dirPath=BaseCfg.CFG_PATH+"/templates/";
		File file = new File(dirPath+fileName);
		if(file.exists()){
		   String dateStr = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		   fileName =  fileName.substring(0,fileName.lastIndexOf(".xml"))+dateStr+new Random().nextInt(999)+".xml";
		   file =  new File(dirPath+fileName);
		   file.createNewFile();
		}else
		   file.createNewFile();
		
		OutputStreamWriter p =new OutputStreamWriter(new FileOutputStream(file),"utf-8");
		StringBuilder cfgContent = new StringBuilder();
		while ((line = br.readLine()) != null) {
			 cfgContent.append(line);
//			 System.out.println(line);
			 p.write(line);
		}
		 p.flush();
		 p.close();
		
		final List<String> recFiles = new ArrayList<String>();
		recFiles.add(fileName); 
		
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		StringBuffer urls = new StringBuffer();
		System.out.println(request.getRemoteAddr());
		String ip = request.getRemoteAddr().equals("127.0.0.1")||request.getRemoteAddr().indexOf("192.168.")>=0?BaseCfg.WWW_LOCALHOST:BaseCfg.WWW;
		
		for (String fileCfg : recFiles) {
			urls.append(ip).append(
					""+(!BaseCfg.APP_CONTEXT.equals("") ? "/"
									+ BaseCfg.APP_CONTEXT : "")
							+ "/"+MODEL+"/entity.jsp?cfg=").append(fileCfg);
			urls.append(";").append(ip).append(
					(!BaseCfg.APP_CONTEXT.equals("") ? "/"
							+ BaseCfg.APP_CONTEXT : "")
							+ "/"+MODEL+"/baseDao.jsp?cfg=").append(fileCfg);
			urls.append(";").append(ip).append(
					(!BaseCfg.APP_CONTEXT.equals("") ? "/"
							+ BaseCfg.APP_CONTEXT : "")
							+ "/"+MODEL+"/daoImp.jsp?cfg=").append(fileCfg);
			urls.append(";").append(ip).append(
					(!BaseCfg.APP_CONTEXT.equals("") ? "/"
							+ BaseCfg.APP_CONTEXT : "")
							+ "/"+MODEL+"/mapperXml.jsp?cfg=")
					.append(fileCfg);
			urls.append(";").append(ip).append(
					(!BaseCfg.APP_CONTEXT.equals("") ? "/"
							+ BaseCfg.APP_CONTEXT : "")
							+ "/"+MODEL+"/business.jsp?cfg=").append(fileCfg);
			urls.append(";").append(ip).append(
					(!BaseCfg.APP_CONTEXT.equals("") ? "/"
							+ BaseCfg.APP_CONTEXT : "")
							+ "/"+MODEL+"/viewObject.jsp?cfg=").append(fileCfg);
		}
		out.write(urls.toString());
		out.close();
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
