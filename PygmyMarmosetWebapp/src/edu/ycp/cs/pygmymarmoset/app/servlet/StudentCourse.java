package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.controller.GetStudentProjectsController;
import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Pair;
import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.model.User;

@Route(pattern="/u/course/*", view="/_view/studentCourse.jsp")
@Navigation(parent=Index.class)
@CrumbSpec(text="%c, %t", items={PathInfoItem.COURSE_ID})
public class StudentCourse extends AbstractServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User) req.getSession().getAttribute("user");
		Course course = (Course) req.getAttribute("course");
		
		// Load projects
		GetStudentProjectsController getStudentProjects = new GetStudentProjectsController();
		List<Pair<Project, Integer>> studentProjects = getStudentProjects.getStudentProjects(course, user);
		req.setAttribute("studentProjects", studentProjects);
		
		delegateToView(req, resp);
	}
}
