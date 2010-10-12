package cn.elam.osgi.demo.localdictquery;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import cn.elam.osgi.demo.dictquery.query.QueryService;
import cn.elam.osgi.demo.localdictquery.impl.LocalQueryService;

public class Activator implements BundleActivator {

	ServiceRegistration sr = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		sr = context.registerService(QueryService.class.getName(),
				new LocalQueryService(), null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		sr.unregister();
	}

}
