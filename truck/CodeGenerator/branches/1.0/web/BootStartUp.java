package web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class BootStartUp  implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("�����������...");
		SecurityXMLServer _server = new SecurityXMLServer();
	}

}
