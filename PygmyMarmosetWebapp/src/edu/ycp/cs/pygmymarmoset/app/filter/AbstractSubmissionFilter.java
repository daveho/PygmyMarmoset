// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.controller.GetSubmissionController;
import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.model.Submission;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.app.util.ServletUtil;

public abstract class AbstractSubmissionFilter {
	public void doFilter(ServletRequest req_, ServletResponse resp_, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) req_;
		HttpServletResponse resp = (HttpServletResponse) resp_;
		
		Project project = (Project) req.getAttribute("project");
		User student = (User) req.getAttribute("student");
		if (student == null) {
			// The request didn't specify a student id
			ServletUtil.sendBadRequest(req, resp, "Student id is required");
			return;
		}

		// Get submission id
		Integer submissionId = getSubmissionId(req, resp);
		if (submissionId == null) {
			return;
		}
		
		// Find the submission
		GetSubmissionController getSubmission = new GetSubmissionController();
		Submission submission = getSubmission.execute(submissionId);
		if (submission == null) {
			ServletUtil.sendNotFound(req, resp, "No submission with id " + submissionId);
			return;
		}
		
		// Subclass access check (e.g., does the submission belong to the correct student)
		// Make sure this submission belongs to the student
		if (submission.getUserId() != student.getId()) {
			ServletUtil.sendForbidden(req, resp, "Student/submission mismatch");
			return;
		}
		// Make sure this submission belongs to the project
		if (submission.getProjectId() != project.getId()) {
			ServletUtil.sendBadRequest(req, resp, "Student/project mismatch");
			return;
		}
		
		// Add to request
		req.setAttribute("submission", submission);
		
		chain.doFilter(req_, resp_);
	}

	protected abstract Integer getSubmissionId(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException;

	public void init(FilterConfig filterConfig) throws ServletException {
		// nothing to do
	}

	public void destroy() {
		// nothing to do
	}
}
