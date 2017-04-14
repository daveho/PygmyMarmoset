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

import edu.ycp.cs.pygmymarmoset.app.controller.FindCourseController;
import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Pair;
import edu.ycp.cs.pygmymarmoset.app.model.Term;
import edu.ycp.cs.pygmymarmoset.app.util.ServletUtil;

/**
 * Filter to load the {@link Course}/{@link Term} pair for
 * <code>/i/course</code> servlets.  Note that no checks are
 * done to ensure that the user is authorized to access
 * the course.
 */
public class LoadCourse implements Filter {
	@Override
	public void doFilter(ServletRequest req_, ServletResponse resp_, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) req_;
		List<Integer> args = ServletUtil.getRequestArgs(req);
		if (args.size() < 1) {
			ServletUtil.sendBadRequest(req, (HttpServletResponse)resp_, "Course id argument is required");
			return;
		}
		Integer courseId = args.get(0);
		
		FindCourseController findCourse = new FindCourseController();
		Pair<Course, Term> courseAndTerm = findCourse.execute(courseId);
		req.setAttribute("course", courseAndTerm.getFirst());
		req.setAttribute("term", courseAndTerm.getSecond());
		
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
