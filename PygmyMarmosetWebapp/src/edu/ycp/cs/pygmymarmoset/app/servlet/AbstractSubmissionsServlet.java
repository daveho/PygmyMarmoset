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

import edu.ycp.cs.pygmymarmoset.app.controller.GetSubmissionsController;
import edu.ycp.cs.pygmymarmoset.app.model.Pair;
import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.model.Submission;
import edu.ycp.cs.pygmymarmoset.app.model.SubmissionStatus;
import edu.ycp.cs.pygmymarmoset.app.model.User;

public abstract class AbstractSubmissionsServlet extends AbstractServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Project project = (Project) req.getAttribute("project");
		User student = (User) req.getAttribute("student");
		
		// Get submissions for student
		GetSubmissionsController getSubmissions = new GetSubmissionsController();
		List<Pair<Submission, SubmissionStatus>> submissions = getSubmissions.execute(project, student);
		req.setAttribute("submissions", submissions);

		delegateToView(req, resp);
	}
}
