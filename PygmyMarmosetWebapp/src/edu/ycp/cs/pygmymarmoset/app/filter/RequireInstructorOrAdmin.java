package edu.ycp.cs.pygmymarmoset.app.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.controller.FindUserInCourseController;
import edu.ycp.cs.pygmymarmoset.app.controller.GetRoleController;
import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Pair;
import edu.ycp.cs.pygmymarmoset.app.model.Roles;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.app.util.ServletUtil;

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
		HttpServletResponse resp = (HttpServletResponse) resp_;
		User user = (User) req.getSession().getAttribute("user");
		
		Course course = (Course) req.getAttribute("course");
		if (course == null) {
			throw new ServletException("No course");
		}
		
		// Get Roles, add them to request.
		// Prevent access if user does not have
		// instructor privileges in the course.
		GetRoleController getRole = new GetRoleController();
		Roles roles = getRole.execute(user, course);
		req.setAttribute("roles", roles);
		if (!roles.isInstructor()) {
			ServletUtil.sendForbidden(req, resp, "Superuser or instructor privileges are required");
			return;
		}
		
		// If a student id was specified, load student User and Roles
		List<Integer> args = ServletUtil.getRequestArgs(req);
		if (args.size() >= 3) {
			Integer studentId = args.get(2);
			FindUserInCourseController findUser = new FindUserInCourseController();
			Pair<User, Roles> studentInfo = findUser.execute(course, studentId);
			if (studentInfo == null) {
				ServletUtil.sendNotFound(req, resp, "No such student");
				return;
			}
			req.setAttribute("student", studentInfo.getFirst());
			req.setAttribute("studentRoles", studentInfo.getSecond());
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
