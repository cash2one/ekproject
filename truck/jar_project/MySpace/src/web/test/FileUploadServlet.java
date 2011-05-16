package web.test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.test.impb.TTestDataProcess;
import web.test.impb.TTestValidator;
import cn.elam.util.db.DBDOMConfigurator;
import cn.elamzs.common.base.files.FileUploadHandle;
import cn.elamzs.common.base.files.FileUploadService;
import cn.elamzs.common.eimport.EImportConfigurator;
import cn.elamzs.common.eimport.core.ThreadDataImport;
import cn.elamzs.common.eimport.inter.EImporter;


/**
 * �����ļ��ϴ�
 * @author Ethan.Lam   2011-2-11
 *
 */
public class FileUploadServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
		try {
			    
			    EImportConfigurator.configure(req.getSession().getServletContext().getRealPath("/")+"/configs/EImportCfg.xml");
			    DBDOMConfigurator.configure(req.getSession().getServletContext().getRealPath("/")+"/configs/PoolConfig.xml");
			    
		        //�����ļ��ϴ�����ӿڣ��󶨶�Ӧ����Ӧ�¼�	    
				FileUploadService srv = new FileUploadService(new FileUploadHandle(){
		
					public void forEachFileUploadFinishedEvent(File file, String oldFile,
							String newFlile) {
						// TODO Auto-generated method stub
						try {
							System.out.println(oldFile);
							TTestValidator v = new TTestValidator();
							TTestDataProcess p = new TTestDataProcess();
							EImporter importer = new ThreadDataImport(v, p);
							//�����ϴ�
							importer.importFile(file.getPath(),oldFile,"testDir","testImp");
						    //������������ģ��
//							importer.downTemplate(FileType.EXCEL_XLSX);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
				}, "d:/eiweb/upfile",10,10);
				
				
			   //��ʼ�����ļ��ϴ�����	
			   srv.uploadFile(req);
			   
			   
			   
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.write("<script>window.location.href='listTask'</script>");
		out.close();
		
//		ServletContext sc = getServletContext();
//		RequestDispatcher rd = sc.getRequestDispatcher("/listTask");
//		rd.forward(req, resp);
		
	}

	
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req,resp);
	}

	
	
	
}
