package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.controller.GetRosterController;
import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Pair;
import edu.ycp.cs.pygmymarmoset.app.model.Role;
import edu.ycp.cs.pygmymarmoset.app.model.User;

public class InstCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get the roster
		Course course = (Course) req.getAttribute("course");
		GetRosterController getRoster = new GetRosterController();
		List<Pair<User, Role>> roster = getRoster.execute(course);
		req.setAttribute("roster", roster);
		// Render view
		req.getRequestDispatcher("/_view/instCourse.jsp").forward(req, resp);
	}
}
