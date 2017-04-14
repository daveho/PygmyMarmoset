// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.controller.ListAllCoursesController;
import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Pair;
import edu.ycp.cs.pygmymarmoset.app.model.PygmyMarmosetException;
import edu.ycp.cs.pygmymarmoset.app.model.Term;

@Route(pattern="/a/courses", view="/_view/adminCourses.jsp")
@Navigation(parent=AdminIndex.class)
@CrumbSpec(text="Courses")
public class AdminCourses extends AbstractServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			ListAllCoursesController controller = new ListAllCoursesController();
			List<Pair<Course,Term>> courses = controller.execute();
			req.setAttribute("coursesAndTerms", courses);
		} catch (PygmyMarmosetException e) {
			req.setAttribute("errmsg", e.getErrorMessage());
		}
		delegateToView(req, resp);
	}
}
