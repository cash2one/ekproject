package cn.elam.web.app.jetty;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

import cn.elam.web.app.test.servlet.TestServlet;
/**
 * 
 * Æô¶¯JettyÈÝÆ÷
 * @author Ethan.Lam   2011-3-15
 *
 */
public class JettyServer {

	static String APP_LOCATION = "D:/Workspaces/Jetty";
	static String WEB_UI = "webapp";
	static String CONTEXT_PATH ="";
	static int PORT = 8080;

	
	public static void main(String... arg) throws Exception {

		Server server = new Server(PORT);
		
	    ServletContextHandler context0 = new ServletContextHandler(ServletContextHandler.SESSIONS);
	    context0.setContextPath("/");
//	    context0.addServlet(new ServletHolder(new TestServlet()),"/*");
	    context0.addServlet(new ServletHolder(new TestServlet("Buongiorno Mondo")),"/it/*");
	    context0.addServlet(new ServletHolder(new TestServlet("Bonjour le Monde")),"/fr/*");
 	    
		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath("/"+CONTEXT_PATH);
		webapp.setWar(APP_LOCATION + "/"+WEB_UI);
		
		
		ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[] { context0, webapp });
		
		server.setHandler(contexts);

		server.start();
		server.join();

	}

}
