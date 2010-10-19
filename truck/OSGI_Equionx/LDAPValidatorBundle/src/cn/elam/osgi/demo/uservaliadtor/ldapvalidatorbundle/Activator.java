package cn.elam.osgi.demo.uservaliadtor.ldapvalidatorbundle;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import cn.elam.osgi.demo.service.user.Validator;
import cn.elam.osgi.demo.uservaliadtor.ldapvalidatorbundle.service.impl.UserValidator;

public class Activator implements BundleActivator {

	ServiceRegistration sr = null;
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		sr = context.registerService(Validator.class.getName(),
				new UserValidator(), null);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		sr.unregister();
	}

}
