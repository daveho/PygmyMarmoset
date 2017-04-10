package edu.ycp.cs.pygmymarmoset.app.filter;

import java.io.IOException;
import java.util.List;

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

public abstract class AbstractProjectFilter {
	private int projectIdIndex;
	
	public AbstractProjectFilter(int projectIdIndex) {
		this.projectIdIndex = projectIdIndex;
	}

	public void doFilter(ServletRequest req_, ServletResponse resp_, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) req_;
		int minArgs = projectIdIndex + 1;
		
		// Get the project id argument.
		// Position varies depending on whether this is for an instructor
		// or student URL.
		List<Integer> args = ServletUtil.getRequestArgs(req);
		if (args.size() < minArgs) {
			ServletUtil.sendBadRequest(req, (HttpServletResponse)resp_, "Missing project id argument");
			return;
		}
		Integer projectId = args.get(projectIdIndex);

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

	public void init(FilterConfig arg0) throws ServletException {
		// nothing to do
	}

	public void destroy() {
		// nothing to do
	}
}
