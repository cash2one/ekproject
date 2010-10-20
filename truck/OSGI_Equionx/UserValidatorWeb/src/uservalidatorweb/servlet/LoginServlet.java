package uservalidatorweb.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import cn.elam.osgi.demo.service.user.Validator;


public class LoginServlet extends HttpServlet{
	
	BundleContext context = null;

	public LoginServlet(BundleContext context) {
		super();
		this.context = context;
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		ServletOutputStream out = response.getOutputStream();
		Validator service = null;
		ServiceReference serviceRef = context.getServiceReference(Validator.class.getName());
		if (null != serviceRef) {
			service = (Validator) context.getService(serviceRef);
		}
		if (service == null) {
			out.println("No available dict query servcie ");
		} else {
			out.println(service.validator(null, null));
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
