package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.controller.ListAllCoursesController;
import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Pair;
import edu.ycp.cs.pygmymarmoset.app.model.PygmyMarmosetException;
import edu.ycp.cs.pygmymarmoset.app.model.Term;

public class AdminCourses extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			ListAllCoursesController controller = new ListAllCoursesController();
			List<Pair<Course,Term>> courses = controller.execute();
			req.setAttribute("coursesAndTerms", courses);
		} catch (PygmyMarmosetException e) {
			req.setAttribute("errmsg", e.getErrorMessage());
		}
		req.getRequestDispatcher("/_view/adminCourses.jsp").forward(req, resp);
	}
}