package web.test;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.test.impb.TTestDataProcess;
import web.test.impb.TTestValidator;
import cn.elam.util.db.DBDOMConfigurator;
import cn.elamzs.common.base.files.FileDownLoadService;
import cn.elamzs.common.eimport.EImportConfigurator;
import cn.elamzs.common.eimport.core.ThreadDataImport;
import cn.elamzs.common.eimport.enums.FileType;
import cn.elamzs.common.eimport.inter.EImporter;
import cn.elamzs.common.eimport.item.TaskModel;

/**
 * ≤‚ ‘Œƒº˛œ¬‘ÿ
 * 
 * @author Ethan.Lam 2011-2-11
 * 
 */
public class FileDownloadServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		try {

			EImportConfigurator.configure(req.getSession().getServletContext()
					.getRealPath("/")
					+ "/configs/EImportCfg.xml");
			DBDOMConfigurator.configure(req.getSession().getServletContext()
					.getRealPath("/")
					+ "/configs/PoolConfig.xml");

			TTestValidator v = new TTestValidator();
			TTestDataProcess p = new TTestDataProcess();
			EImporter importer = new ThreadDataImport(v, p);
			FileDownLoadService srv = new FileDownLoadService();
			TaskModel task = null;
			File file = null;
			if ("template".equals(req.getParameter("action"))) {
				file = importer.downTemplate(FileType.EXCEL_XLSX);
			} else if ("resource".equals(req.getParameter("action"))) {
				task = importer.getResourceFile(req.getParameter("id"));
				file = task!=null?new File(task.getSrcPath()):null;
			} else if ("result".equals(req.getParameter("action"))) {
				task = importer.getImportedResult(req.getParameter("id"));
				file = task!=null?new File(task.getResultPath()):null;
			}
			srv.downloadFile(resp, null,task!=null?task.getFileName():file.getName(), file.getPath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
