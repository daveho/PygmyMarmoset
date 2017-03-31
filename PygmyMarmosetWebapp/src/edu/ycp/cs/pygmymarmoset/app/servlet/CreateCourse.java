package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.controller.CreateCourseController;
import edu.ycp.cs.pygmymarmoset.app.model.Course;

public class CreateCourse extends AbstractFormServlet {
	private static final long serialVersionUID = 1L;
	
	public CreateCourse() {
		super("/_view/createCourse.jsp");
	}
	
	@Override
	protected Params createParams(HttpServletRequest req) {
		return new Params(req)
				.add("course", Course.class);
	}
	
	@Override
	protected LogicOutcome doLogic(Params params, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Course course = params.get("course");
		CreateCourseController controller = new CreateCourseController();
		controller.execute(course);
		// Success!
		req.setAttribute("resultmsg", "Course created successfully");
		
		// Put an empty Course object in the request so that the form
		// fields will be blanked out
		req.setAttribute("course", new Course());
		
		return LogicOutcome.STAY_ON_PAGE;
	}
}
