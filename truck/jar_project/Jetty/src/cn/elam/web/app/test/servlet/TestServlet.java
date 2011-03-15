package cn.elam.web.app.test.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * ≤‚ ‘Servlet
 * 
 * @author Ethan.Lam 2011-3-15
 * 
 */
public class TestServlet extends HttpServlet {

	private String greeting = "Hello World";

	public TestServlet() {

	}

	public TestServlet(String greeting) {
		this.greeting = greeting;
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		// response.sendRedirect("/index.jsp");

		System.out.println(request.getServletContext().getRealPath("/"));
		ServletContext sc = getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/index.jsp");
		rd.forward(request, response);

		// response.getWriter().println("<h1>" + greeting + "</h1>");
		// response.getWriter().println("session=" +
		// request.getSession(true).getId());
	}

}
