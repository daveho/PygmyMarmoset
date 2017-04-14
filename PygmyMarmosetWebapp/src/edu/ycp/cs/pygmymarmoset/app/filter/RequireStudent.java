// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

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

public class RequireStudent extends AbstractLoginFilter implements Filter {
	@Override
	public void doFilter(ServletRequest req_, ServletResponse resp_, FilterChain chain)
			throws IOException, ServletException {
		// Make sure user is logged in
		if (!checkLogin(req_, resp_)) {
			return;
		}
		
		HttpServletRequest req = (HttpServletRequest) req_;
		HttpServletResponse resp = (HttpServletResponse)resp_;
		
		User user = (User) req.getSession().getAttribute("user");
		Course course = (Course) req.getAttribute("course"); // assumes LoadCourse has run
		
		// The second argument of all student URLs is the student
		// id.  The student could be the same as the logged-in user,
		// or course be different as long as the student is a member
		// of the course and the logged-in user is an instructor in
		// the course.
		List<Integer> args = ServletUtil.getRequestArgs(req);
		if (args.size() < 2) {
			ServletUtil.sendBadRequest(req, resp, "A user id argument is required to identify student");
			return;
		}
		Integer studentId = args.get(1);
		FindUserInCourseController findUser = new FindUserInCourseController();
		Pair<User, Roles> studentInfo = findUser.execute(course, studentId);
		if (studentInfo == null) {
			ServletUtil.sendNotFound(req, resp, "Student " + studentId + " is not registered in the course");
		}
		User student = studentInfo.getFirst();
		Roles studentRoles = studentInfo.getSecond();
		
		// Make sure the registered user is either:
		//   - the same as the student, or
		//   - an instructor in the course
		if (student.getId() != user.getId()) {
			// Make sure user is an instructor.
			GetRoleController getRole = new GetRoleController();
			Roles roles = getRole.execute(user, course);
			if (!roles.isInstructor()) {
				ServletUtil.sendForbidden(req, resp, "Instructor or superuser privilege is required");
				return;
			}
		}
		
		System.out.printf("User id=%d, student id=%d\n", user.getId(), student.getId());
		
		req.setAttribute("student", student);
		req.setAttribute("studentRoles", studentRoles);
		
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
