// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.controller.CreateProjectController;
import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Project;

@Route(pattern="/i/createProject/*", view="/_view/instCreateProject.jsp")
@Navigation(parent=InstCourse.class)
@CrumbSpec(text="Create project", items={PathInfoItem.COURSE_ID})
public class InstCreateProject extends AbstractFormServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected Params createParams(HttpServletRequest req) {
		return new Params(req)
				.add("project", Project.class);
	}

	@Override
	protected LogicOutcome doLogic(Params params, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Course course = (Course) req.getAttribute("course");
		Project project = params.get("project");
		
		CreateProjectController createProj = new CreateProjectController();
		createProj.execute(course, project);
		
		// Success!
		req.setAttribute("project", new Project()); // blank out form fields
		req.setAttribute("resultmsg", "Created project " + project.getName() + " successfully");
		
		return LogicOutcome.STAY_ON_PAGE;
	}
}
