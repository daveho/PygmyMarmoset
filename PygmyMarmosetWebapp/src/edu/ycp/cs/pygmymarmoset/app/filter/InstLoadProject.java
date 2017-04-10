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

import edu.ycp.cs.pygmymarmoset.app.controller.GetProjectController;
import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.util.ServletUtil;

/**
 * Load {@link Project} for instructor servlets which
 * access a specific project.  Assumes that {@link RequireInstructorOrAdmin}
 * has already verified that the logged-in user has instructor
 * privilege in the course.
 */
public class InstLoadProject implements Filter {
	@Override
	public void doFilter(ServletRequest req_, ServletResponse resp_, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) req_;
		
		// Second path info argument is the project id.
		List<Integer> args = ServletUtil.getRequestArgs(req);
		if (args.size() < 2) {
			ServletUtil.sendBadRequest(req, (HttpServletResponse)resp_, "Missing project id argument");
			return;
		}
		Integer projectId = args.get(1);

		// Find the Project
		GetProjectController getProject = new GetProjectController();
		Project project = getProject.execute(projectId);
		if (project == null) {
			ServletUtil.sendNotFound(req, (HttpServletResponse)resp_, "No project with project id " + projectId);
			return;
		}
		
		// Make sure this project is actually part of the correct course.
		Course course = (Course) req.getAttribute("course");
		if (project.getCourseId() != course.getId()) {
			ServletUtil.sendForbidden(req, (HttpServletResponse)resp_, "Project/course mismatch");
			return;
		}
		req.setAttribute("project", project);
		
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
