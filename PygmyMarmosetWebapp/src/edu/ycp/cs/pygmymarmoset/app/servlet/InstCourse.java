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

import edu.ycp.cs.pygmymarmoset.app.controller.GetProjectsController;
import edu.ycp.cs.pygmymarmoset.app.controller.GetRosterController;
import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Pair;
import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.model.Role;
import edu.ycp.cs.pygmymarmoset.app.model.RosterField;
import edu.ycp.cs.pygmymarmoset.app.model.User;

@Route(pattern="/i/course/*", view="/_view/instCourse.jsp")
@Navigation(parent=Index.class)
@CrumbSpec(text="%c, %t", items={PathInfoItem.COURSE_ID})
public class InstCourse extends AbstractServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// See if a sort order was specified
		String sort = req.getParameter("sort");
		RosterField[] sortOrder = null;
		if (sort != null) {
			try {
				RosterField primary = RosterField.find(sort.trim());
				sortOrder = RosterField.sortBy(primary);
				req.setAttribute("sort", sort);
			} catch (IllegalArgumentException e) {
				// invalid sort order was specified
			}
		}

		// Use default sort order if none was specified
		if (sortOrder == null) {
			sortOrder = RosterField.getDefaultSortOrder();
		}
		
		// Get the roster
		Course course = (Course) req.getAttribute("course");
		GetRosterController getRoster = new GetRosterController();
		List<Pair<User, Role>> roster = getRoster.execute(course, sortOrder);
		req.setAttribute("roster", roster);
		// Get the projects
		GetProjectsController getProjects = new GetProjectsController();
		List<Project> projects = getProjects.execute(course);
		req.setAttribute("projects", projects);
		// Render view
		delegateToView(req, resp);
	}
}
