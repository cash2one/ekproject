package web.test;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.elam.util.db.DBDOMConfigurator;
import cn.elamzs.common.eimport.EImportConfigurator;
import cn.elamzs.common.eimport.core.FileStorePersisService;
import cn.elamzs.common.eimport.item.TaskModel;

/**
 * 测试文件上传
 * 
 * @author Ethan.Lam 2011-2-11
 * 
 */
public class ImpTaskServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		EImportConfigurator.configure(req.getSession().getServletContext().getRealPath("/")+"/configs/EImportCfg.xml");
		DBDOMConfigurator.configure(req.getSession().getServletContext().getRealPath("/")+"/configs/PoolConfig.xml");
		    
		try {
			FileStorePersisService  ser = new FileStorePersisService();
			List<TaskModel> tasks = ser.query(null, 1, 100);
			req.setAttribute("tasks", tasks);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ServletContext sc = getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/imp_tasklst.jsp");
		rd.forward(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
