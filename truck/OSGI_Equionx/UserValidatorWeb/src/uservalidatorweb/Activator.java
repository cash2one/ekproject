package uservalidatorweb;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uservalidatorweb.servlet.LoginServlet;

public class Activator implements BundleActivator, ServiceTrackerCustomizer {

	public static BundleContext context = null;
	private HttpService httpService;
	private LoginServlet servlet;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		this.context = context;
		servlet = new LoginServlet (context);
		ServiceTracker tracker = new ServiceTracker(context, HttpService.class
				.getName(), this);
		tracker.open();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		unregisterServlet(httpService);
		servlet = null;
		context = null;
	}

	private void registerServlet(HttpService aHttpService) {
		try {
			if (aHttpService != null)
				aHttpService.registerServlet("/login", servlet, null, null);
			System.out.println("Registered /login servlet");
		} catch (Exception e) {
			System.out.println("Unable to register servlet");
		} // try
	} // registerServlet

	private void unregisterServlet(HttpService aHttpService) {
		try {
			if (aHttpService != null)
				aHttpService.unregister("/login");
			System.out.println("Unregistered /login servlet");
		} catch (Exception e) {
			System.out.println("Unable to unregister servlet");
		} // try
	} // unregisterServlet

	/* ----- implementation of ServiceTrackerCustomizer ----- */

	public Object addingService(ServiceReference aRef) {
		httpService = (HttpService) context.getService(aRef);
		registerServlet(httpService);
		return httpService;
	} // addingService

	public void modifiedService(ServiceReference aRef, Object aObj) {
		httpService = (HttpService) context.getService(aRef);
		registerServlet(httpService);
	} // modifiedService

	public void removedService(ServiceReference aRef, Object aObj) {
	} // removedService
}
