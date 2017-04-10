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

import edu.ycp.cs.pygmymarmoset.app.controller.GetSubmissionController;
import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.model.Submission;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.app.util.ServletUtil;

/**
 * Load a {@link Submission} identified by a student URL.
 */
public class StudentLoadSubmission implements Filter {
	@Override
	public void doFilter(ServletRequest req_, ServletResponse resp_, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) req_;
		HttpServletResponse resp = (HttpServletResponse) resp_;
		
		User student = (User) req.getAttribute("student");
		Project project = (Project) req.getAttribute("project");
		
		// Fourth path info argument is the submission id
		List<Integer> args = ServletUtil.getRequestArgs(req);
		
		if (args.size() < 4) {
			ServletUtil.sendBadRequest(req, resp, "Submission id is required");
			return;
		}
		Integer submissionId = args.get(3);
		
		// Find the submission
		GetSubmissionController getSubmission = new GetSubmissionController();
		Submission submission = getSubmission.execute(submissionId);
		if (submission == null) {
			ServletUtil.sendNotFound(req, resp, "No submission with id " + submissionId);
			return;
		}
		
		// Make sure this actually belongs to the student and is for the
		// correct project.
		if (submission.getUserId() != student.getId()) {
			ServletUtil.sendForbidden(req, resp, "Student/submission mismatch");
			return;
		}
		if (submission.getProjectId() != project.getId()) {
			ServletUtil.sendBadRequest(req, resp, "Student/project mismatch");
			return;
		}
		
		// Add to request
		req.setAttribute("submission", submission);
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
