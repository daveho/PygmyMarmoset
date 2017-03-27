package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Params params = new Params()
				.getExpected(req, "username", "password");

		if (params.hasMissing()) {
			req.setAttribute("missing", params.getMissing());
			req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
			return;
		} else {
			resp.sendRedirect(req.getContextPath() + "/index");
		}
	}
}
