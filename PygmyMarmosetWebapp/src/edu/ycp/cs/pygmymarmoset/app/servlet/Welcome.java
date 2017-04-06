package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.model.User;

/**
 * The welcome servlet is used as the welcome page,
 * and just redirects to the login page (if the user is not
 * logged in) or the index page (if the user is logged in.)
 */
@Route(pattern="/welcome")
public class Welcome extends AbstractServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User) req.getSession().getAttribute("user");
		if (user == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
		} else {
			resp.sendRedirect(req.getContextPath() + "/u/index");
		}
	}
}
