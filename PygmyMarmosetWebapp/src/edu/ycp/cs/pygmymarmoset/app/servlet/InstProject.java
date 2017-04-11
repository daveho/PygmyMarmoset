package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.controller.GetStudentProjectActivityController;
import edu.ycp.cs.pygmymarmoset.app.model.Pair;
import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.model.User;

@Route(pattern="/i/project/*", view="/_view/instProject.jsp")
@Navigation(parent=InstCourse.class)
@CrumbSpec(text="Project %p", items={PathInfoItem.COURSE_ID, PathInfoItem.PROJECT_ID})
public class InstProject extends AbstractServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Project project = (Project) req.getAttribute("project");
		
		GetStudentProjectActivityController getStudentProjectActivity =
				new GetStudentProjectActivityController();
		
		List<Pair<User, Integer>> studentActivity = getStudentProjectActivity.execute(	project);
		req.setAttribute("studentActivity", studentActivity);
		
		delegateToView(req, resp);
	}
}
