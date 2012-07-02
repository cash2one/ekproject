package web;


import java.io.IOException;
import java.net.InetSocketAddress;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class BootStartUp implements ServletContextListener {

    private static final int PORT = 888;  
    
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void contextInitialized(ServletContextEvent arg0) {
		
		// TODO Auto-generated method stub
		System.out.println("Æô¶¯·þÎñ¼àÌý...");
		
//		SecurityXMLServer _server = new SecurityXMLServer();
		
		SocketAcceptor acceptor = new NioSocketAcceptor();
		DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
		acceptor.setHandler(new XmlSocketHandler());
		try {
			acceptor.bind(new InetSocketAddress(PORT));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Listening on port " + PORT);
	}

}
