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

import edu.ycp.cs.pygmymarmoset.app.controller.GetStudentProjectActivityController;
import edu.ycp.cs.pygmymarmoset.app.controller.UpdateProjectController;
import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.model.ProjectActivityField;
import edu.ycp.cs.pygmymarmoset.app.model.Role;
import edu.ycp.cs.pygmymarmoset.app.model.Triple;
import edu.ycp.cs.pygmymarmoset.app.model.User;

@Route(pattern="/i/project/*", view="/_view/instProject.jsp")
@Navigation(parent=InstCourse.class)
@CrumbSpec(text="Project %p", items={PathInfoItem.COURSE_ID, PathInfoItem.PROJECT_ID})
public class InstProject extends AbstractServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		getStudentActivity(req);
		delegateToView(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String toggle = req.getParameter("toggle");
		if (toggle != null) {
			// toggle project visibility
			Project project = (Project) req.getAttribute("project");
			project.setVisible(!project.isVisible());
			UpdateProjectController updateProject = new UpdateProjectController();
			updateProject.execute(project);
		}
		getStudentActivity(req);
		delegateToView(req, resp);
	}
	
	private void getStudentActivity(HttpServletRequest req) {
		Project project = (Project) req.getAttribute("project");

		// See if a sort order was specified
		ProjectActivityField[] sortOrder = null;
		String sort = req.getParameter("sort");
		if (sort != null) {
			try {
				ProjectActivityField primary = ProjectActivityField.find(sort.trim());
				sortOrder = ProjectActivityField.sortBy(primary);
			} catch (IllegalArgumentException e) {
				// unknown sort order
			}
		}
		
		if (sortOrder == null) {
			sortOrder = ProjectActivityField.getDefaultSortOrder();
			sort = sortOrder[0].toString().toLowerCase();
		}

		req.setAttribute("sort", sort);
		
		GetStudentProjectActivityController getStudentProjectActivity =
				new GetStudentProjectActivityController();
		
		List<Triple<User, Integer[], Role>> studentActivity = getStudentProjectActivity.execute(project, sortOrder);
		req.setAttribute("studentActivity", studentActivity);
	}
}
