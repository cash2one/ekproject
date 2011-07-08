package web;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import qtone.generator.util.BaseCfg;
import cn.elamzs.common.base.files.FileUploadHandle;
import cn.elamzs.common.base.files.FileUploadService;

 


/**
 * 测试文件上传
 * @author Ethan.Lam   2011-2-11
 *
 */
public class FileUploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	    InputStream in =  req.getInputStream();
	    System.out.println(in.read());
	    
		
		String dirPath=BaseCfg.CFG_PATH;
		System.out.println(dirPath);
	    final List<String> recFiles = new ArrayList<String>();
		try {
		        //建立文件上传服务接口，绑定对应的响应事件	    
				FileUploadService srv = new FileUploadService(new FileUploadHandle(){
		
					public void forEachFileUploadFinishedEvent(File file, String oldFile,
							String newFlile) {
						// TODO Auto-generated method stub
						try {
							System.out.println("服务器接受到新的文件："+newFlile);
							recFiles.add(newFlile);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
				}, dirPath+"/templates/",10,10){
					
					//文件名
				    public  String fileNamingRule(String oldFileName){
				    	    if(oldFileName.indexOf("\\")>0||oldFileName.indexOf("/")>0){
				    	    	oldFileName = oldFileName.replace("\\", "/");
				    	    	oldFileName = oldFileName.substring(oldFileName.lastIndexOf("/")+1);
				    	    }
					    	return oldFileName;
					} 	 
					
				};
			   //开始处理文件上传过程	
			   srv.uploadFile(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
//		out.write("<script>window.location.href='/"+(!BaseCfg.APP_CONTEXT.equals("")?"/"+BaseCfg.APP_CONTEXT:"")+"/index.jsp'</script>");
		StringBuffer urls = new StringBuffer();
		String ip="http://192.168.4.39:80/";
		for(String file:recFiles){
			urls.append(ip).append(""+(!BaseCfg.APP_CONTEXT.equals("")?"/"+BaseCfg.APP_CONTEXT:"")+"/codeTemplators/entity.jsp?cfg=").append(file);
			urls.append(";").append(ip).append((!BaseCfg.APP_CONTEXT.equals("")?"/"+BaseCfg.APP_CONTEXT:"")+"/codeTemplators/mapper.jsp?cfg=").append(file);
			urls.append(";").append(ip).append((!BaseCfg.APP_CONTEXT.equals("")?"/"+BaseCfg.APP_CONTEXT:"")+"/codeTemplators/mapperXml.jsp?cfg=").append(file);
			urls.append(";").append(ip).append((!BaseCfg.APP_CONTEXT.equals("")?"/"+BaseCfg.APP_CONTEXT:"")+"/codeTemplators/business.jsp?cfg=").append(file);
		}
		out.write(urls.toString());
		out.close();
	}

	
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req,resp);
	}

	
	
	
}
