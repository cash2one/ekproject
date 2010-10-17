package dictweb;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import cn.elam.osgi.demo.dictquery.query.QueryService;

public class DictQueryServlet extends HttpServlet {

	BundleContext context = null;

	public DictQueryServlet(BundleContext context) {
		super();
		this.context = context;
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		ServletOutputStream out = response.getOutputStream();
		QueryService service = null;
		ServiceReference serviceRef = context
				.getServiceReference(QueryService.class.getName());
		if (null != serviceRef) {
			service = (QueryService) context.getService(serviceRef);
		}
		if (service == null) {
			out.println("No available dict query servcie ");
		} else {
			out.println(service.queryWork(request.getParameter("word")));
		}
		out.close();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
