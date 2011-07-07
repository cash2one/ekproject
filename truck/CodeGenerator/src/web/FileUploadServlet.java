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
 * �����ļ��ϴ�
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
	
		try {
		        //�����ļ��ϴ�����ӿڣ��󶨶�Ӧ����Ӧ�¼�	    
				FileUploadService srv = new FileUploadService(new FileUploadHandle(){
		
					public void forEachFileUploadFinishedEvent(File file, String oldFile,
							String newFlile) {
						// TODO Auto-generated method stub
						try {
							System.out.println("���������ܵ��µ��ļ���"+oldFile);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
				}, req.getRealPath("/")+"templates/",10,10){
					
					//�ļ���
				    public  String fileNamingRule(String oldFileName){
					    	return oldFileName;
					} 	 
					
				};
			   //��ʼ�����ļ��ϴ�����	
			   srv.uploadFile(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.write("<script>window.location.href='/code/index.jsp'</script>");
		out.close();
	}

	
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req,resp);
	}

	
	
	
}
