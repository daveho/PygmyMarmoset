package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.controller.RegisterStudentController;
import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.User;

public class InstRegisterStudent extends AbstractFormServlet {
	private static final long serialVersionUID = 1L;
	
	public InstRegisterStudent() {
		super("/_view/instRegisterStudent.jsp");
	}

	@Override
	protected Params createParams(HttpServletRequest req) {
		Params params = new Params(req)
				.add("student", User.class);
		return params;
	}

	@Override
	protected LogicOutcome doLogic(Params params, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Course course = (Course) req.getAttribute("course");
		
		User student = params.get("student");
		student.setSuperUser(false); // paranoia
		
		// See if this student already exists
		RegisterStudentController controller = new RegisterStudentController();
		controller.execute(student, course);
		// Success!
		req.setAttribute("resultmsg", "Student " + student.getUsername() + " registered successfully");
		
		// Reset form fields
		req.setAttribute("student", new User());
		
		return LogicOutcome.STAY_ON_PAGE;
	}

}
