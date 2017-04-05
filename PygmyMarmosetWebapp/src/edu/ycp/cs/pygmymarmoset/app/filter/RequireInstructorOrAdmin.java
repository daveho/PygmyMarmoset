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
import edu.ycp.cs.pygmymarmoset.app.servlet.ServletUtil;

/**
 * Filter to require that logged in user is either an
 * instructor in the course (assumes that a {@link Course}
 * has been added to the session by {@link LoadCourse})
 * or is a superuser.
 */
public class RequireInstructorOrAdmin extends AbstractLoginFilter implements Filter {
	@Override
	public void doFilter(ServletRequest req_, ServletResponse resp_, FilterChain chain)
			throws IOException, ServletException {
		// Make sure user is logged in
		if (!checkLogin(req_, resp_)) {
			return;
		}
		HttpServletRequest req = (HttpServletRequest) req_;
		User user = (User) req.getSession().getAttribute("user");
		
		if (!user.isSuperUser()) {
			// User is not a superuser, so check whether user is an instructor in the course
			Course course = (Course) req.getAttribute("course");
			if (course == null) {
				throw new ServletException("No course");
			}
			
			GetRoleController getRole = new GetRoleController();
			Roles roles = getRole.execute(user, course);
			
			if (!roles.isInstructor()) {
				ServletUtil.sendForbidden(req, (HttpServletResponse)resp_, "Superuser or instructor privileges are required");
				return;
			}
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
