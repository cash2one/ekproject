package uservalidatorweb;

import org.eclipse.equinox.http.servlet.HttpServiceServlet;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import uservalidatorweb.servlet.LoginServlet;

public class Activator implements BundleActivator,ServiceListener,ServiceTrackerCustomizer  {

	
	public static BundleContext context = null;
	private  HttpServiceServlet   httpService;
	private  LoginServlet  servlet;
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		ServiceTracker tracker = new ServiceTracker (context, LoginServlet.class.getName (), this);
		tracker.open ();
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
	
	
	}

	private void registerServlet (HttpServlet aHttpService) {
		try {
		if (aHttpService != null)
		aHttpService.registerServlet ("/hello", servlet, null, null);
		System.out.println ("Registered /hello servlet");
		} catch (Exception e) {
		System.out.println ("Unable to register servlet");
		} // try
		}
	
	private void unregisterServlet (HttpService aHttpService) {
		try {
		if (aHttpService != null)
		aHttpService.unregister ("/hello");
		System.out.println ("Unregistered /hello servlet");
		} catch (Exception e) {
		System.out.println ("Unable to unregister servlet");
		} // try
		}
	
	@Override
	public void serviceChanged(ServiceEvent arg0) {
	   
	
	}

	@Override
	public Object addingService(ServiceReference arg0) {
		httpService = (HttpService) context.getService (aRef);
		registerServlet (httpService);
		return httpService;
	}

	@Override
	public void modifiedService(ServiceReference arg0, Object arg1) {
		httpService = (HttpService) context.getService (aRef);
		registerServlet (httpService);
	}

	@Override
	public void removedService(ServiceReference arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}
