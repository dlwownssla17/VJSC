package edu.upenn.cis455.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class LoginServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String query = request.getQueryString();
		String[] parts = query.split("=");
		String username = parts[1];
		Cookie c = new Cookie("Debra-User", username);
		c.setMaxAge(10000000);
		//c.setMaxAge(1);
		response.addCookie(c);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<HTML><HEAD><TITLE>Debra FitBit Registration</TITLE></HEAD><BODY>");
		out.println("<P>Added cookie (TestCookie,54321) to response.</P>");
		out.println("<P>Click to <A HREF=\"cookie2\">Cookie Servlet 2</A>.</P>");
		out.println("<P>User: " + username + "</P>");
		
		out.println("<h1>Query String: " + request.getQueryString() + " " + request.getParameter("test") + " " + request.getParameter("test2") + "</h1>");
		
		out.println("</BODY></HTML>");	
		String debraURL = "http%3A%2F%2Fec2-34-205-71-82.compute-1.amazonaws.com%3A8080%2Fdebra%2Ffitbitcode";
		response.sendRedirect("https://www.fitbit.com/oauth2/authorize?response_type=code&client_id=22875R&redirect_uri=" + debraURL + "&scope=activity%20heartrate%20location%20nutrition%20profile%20settings%20sleep%20social%20weight&expires_in=604800");
	}
}