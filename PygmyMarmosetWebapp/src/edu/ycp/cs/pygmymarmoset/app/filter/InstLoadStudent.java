// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

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

import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.model.Roles;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.app.servlet.InstStudent;
import edu.ycp.cs.pygmymarmoset.app.util.ServletUtil;

/**
 * Normally, {@link RequireInstructorOrAdmin} loads the student {@link User}
 * and student {@link Roles}.  However, the {@link InstStudent} violates
 * the usual argument ordering convention that instructor URIs have
 * the form courseId/projectId/studentId, because there is no {@link Project} id.
 * This filter loads the student user and roles from the second argument
 * rather than the third.
 */
public class InstLoadStudent implements Filter {
	@Override
	public void doFilter(ServletRequest req_, ServletResponse resp_, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) req_;
		HttpServletResponse resp = (HttpServletResponse) resp_;
		
		Course course = (Course) req.getAttribute("course");
		
		if (!ServletUtil.loadStudent(req, resp, course, 1)) {
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
