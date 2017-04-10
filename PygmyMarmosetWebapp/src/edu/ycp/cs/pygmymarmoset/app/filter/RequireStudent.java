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

import edu.ycp.cs.pygmymarmoset.app.controller.GetRoleController;
import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Roles;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.app.util.ServletUtil;

public class RequireStudent extends AbstractLoginFilter implements Filter {
	@Override
	public void doFilter(ServletRequest req_, ServletResponse resp_, FilterChain chain)
			throws IOException, ServletException {
		// Make sure user is logged in
		if (!checkLogin(req_, resp_)) {
			return;
		}
		
		HttpServletRequest req = (HttpServletRequest) req_;
		
		User user = (User) req.getSession().getAttribute("user");
		Course course = (Course) req.getAttribute("course");

		// Make sure user is registered in the course.
		GetRoleController getRole = new GetRoleController();
		Roles roles = getRole.execute(user, course);
		if (roles.isEmpty()) {
			ServletUtil.sendForbidden(req, (HttpServletResponse) resp_, "Only students and instructors may access this course");
			return;
		}
		req.setAttribute("roles", roles);
		
		chain.doFilter(req_, resp_);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// nothing to do
	}

	@Override
	public void destroy() {
		// nothing to do
	}
}
