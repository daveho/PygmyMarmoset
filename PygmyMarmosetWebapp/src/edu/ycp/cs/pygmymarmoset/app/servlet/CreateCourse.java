package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.controller.CreateCourseController;
import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.ValidationException;

public class CreateCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/_view/createCourse.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Params params = new Params(req)
				.add("course", Course.class);
		params.unmarshal();
		
		Course course = params.get("course");
		
		try {
			CreateCourseController controller = new CreateCourseController();
			controller.execute(course);
			// Success!
			req.setAttribute("resultmsg", "Course created successfully");
		} catch (ValidationException e) {
			// Validation error
			req.setAttribute("errmsg", "Course creation failed: " + e.getMessage());
		}
		req.getRequestDispatcher("/_view/createCourse.jsp").forward(req, resp);
	}
}
