package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.controller.GetProjectsController;
import edu.ycp.cs.pygmymarmoset.app.controller.GetRosterController;
import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Pair;
import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.model.Role;
import edu.ycp.cs.pygmymarmoset.app.model.User;

@Route(pattern="/i/course/*", view="/_view/instCourse.jsp")
@Navigation(parent=Index.class)
@CrumbSpec(text="%c, %t", items={PathInfoItem.COURSE_ID})
public class InstCourse extends AbstractServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get the roster
		Course course = (Course) req.getAttribute("course");
		GetRosterController getRoster = new GetRosterController();
		List<Pair<User, Role>> roster = getRoster.execute(course);
		req.setAttribute("roster", roster);
		// Get the projects
		GetProjectsController getProjects = new GetProjectsController();
		List<Project> projects = getProjects.execute(course);
		req.setAttribute("projects", projects);
		// Render view
		delegateToView(req, resp);
	}
}
