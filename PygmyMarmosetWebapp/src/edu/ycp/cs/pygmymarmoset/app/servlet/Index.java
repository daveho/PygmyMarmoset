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

import edu.ycp.cs.pygmymarmoset.app.controller.GetCoursesForUserController;
import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.RoleType;
import edu.ycp.cs.pygmymarmoset.app.model.Term;
import edu.ycp.cs.pygmymarmoset.app.model.Triple;
import edu.ycp.cs.pygmymarmoset.app.model.User;

@Route(pattern="/index", view="/_view/index.jsp")
@CrumbSpec(text="Home")
public class Index extends AbstractServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get User's courses
		GetCoursesForUserController getCourses = new GetCoursesForUserController();
		User user = (User) req.getSession().getAttribute("user");
		List<Triple<Course, Term, RoleType>> courses = getCourses.execute(user);
		req.setAttribute("courses", courses);
		// Render view
		delegateToView(req, resp);
	}
}
