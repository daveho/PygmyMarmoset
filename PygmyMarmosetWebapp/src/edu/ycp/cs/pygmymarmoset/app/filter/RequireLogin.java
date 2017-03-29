package edu.ycp.cs.pygmymarmoset.app.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.model.LoginCredentials;
import edu.ycp.cs.pygmymarmoset.app.model.User;

/**
 * Filter to protect access to servlets requiring that the
 * user is logged in.
 */
public class RequireLogin implements Filter {
	@Override
	public void doFilter(ServletRequest req_, ServletResponse resp_, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) req_;
		User user = (User) req.getSession().getAttribute("user");
		if (user == null) {
			// User is not logged in: make a note of goal URL
			// so we can redirect there after successful login.
			// By putting a LoginCredentials object in the
			// session, the Login servlet will be able to see it.
			LoginCredentials creds = new LoginCredentials();
			creds.setGoal(req.getRequestURI());
			req.getSession().setAttribute("creds", creds);
			HttpServletResponse resp = (HttpServletResponse) resp_;
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}
		chain.doFilter(req_, resp_);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// nothing to do
	}

	@Override
	public void destroy() {
		// nothing to do
	}
}
