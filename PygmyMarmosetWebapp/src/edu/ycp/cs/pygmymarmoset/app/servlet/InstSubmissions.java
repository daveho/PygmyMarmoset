package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.controller.GetSubmissionsController;
import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.model.Submission;
import edu.ycp.cs.pygmymarmoset.app.model.User;

@Route(pattern="/i/submissions/*", view="/_view/instSubmissions.jsp")
@Navigation(parent=InstProject.class)
@CrumbSpec(text="Submissions for %S", items={PathInfoItem.COURSE_ID, PathInfoItem.PROJECT_ID, PathInfoItem.STUDENT_ID})
public class InstSubmissions extends AbstractServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Project project = (Project) req.getAttribute("project");
		User student = (User) req.getAttribute("student");
		
		// Get submissions for project
		// Get submissions for student
		GetSubmissionsController getSubmissions = new GetSubmissionsController();
		List<Submission> submissions = getSubmissions.execute(project, student);
		req.setAttribute("submissions", submissions);
		
		delegateToView(req, resp);
	}
}