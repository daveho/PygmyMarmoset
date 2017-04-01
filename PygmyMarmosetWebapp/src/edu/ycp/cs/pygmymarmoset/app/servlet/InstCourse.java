package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.controller.FindCourseController;
import edu.ycp.cs.pygmymarmoset.app.controller.ListAllTermsController;
import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Pair;
import edu.ycp.cs.pygmymarmoset.app.model.Term;

public class InstCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		System.out.println("pathInfo=" + pathInfo);
		
		Object[] args = ServletUtil.parsePathInfo("i", pathInfo);
		
		// Load the Course (and its Term)
		FindCourseController controller = new FindCourseController();
		Pair<Course, Term> courseAndTerm = controller.execute((Integer) args[0]);
		req.setAttribute("course", courseAndTerm.getFirst());
		req.setAttribute("term", courseAndTerm.getSecond());
		
		req.getRequestDispatcher("/_view/instCourse.jsp").forward(req, resp);
	}
}
