package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType("text/html");
		resp.getWriter().println(
				"<html>" +
				"<head>" +
				"<title>Index</title>" +
				"</head>" +
				"<body>" +
				"<p>Welcome to Pygmy Marmoset!</p>" +
				"</head>" +
				"</body>"
				);
	}
}
