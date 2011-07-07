package web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.elamzs.common.base.files.FileUploadHandle;
import cn.elamzs.common.base.files.FileUploadService;

 


/**
 * 测试文件上传
 * @author Ethan.Lam   2011-2-11
 *
 */
public class FileUploadServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
		try {
			    
			    
		        //建立文件上传服务接口，绑定对应的响应事件	    
				FileUploadService srv = new FileUploadService(new FileUploadHandle(){
		
					public void forEachFileUploadFinishedEvent(File file, String oldFile,
							String newFlile) {
						// TODO Auto-generated method stub
						try {
							System.out.println(oldFile);
						 
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
				}, "d:/eiweb/upfile",10,10);
				
				
			   //开始处理文件上传过程	
			   srv.uploadFile(req);
			   
			   
			   
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.write("<script>window.location.href='listTask'</script>");
		out.close();
	}

	
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req,resp);
	}

	
	
	
}
