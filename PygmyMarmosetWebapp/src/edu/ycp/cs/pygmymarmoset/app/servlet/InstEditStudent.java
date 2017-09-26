// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.controller.EditStudentController;
import edu.ycp.cs.pygmymarmoset.app.model.User;

@Route(pattern="/i/editStudent/*", view="/_view/instEditStudent.jsp")
@Navigation(parent=InstStudent.class)
@CrumbSpec(text="Edit account", items={PathInfoItem.COURSE_ID, PathInfoItem.STUDENT_ID})
public class InstEditStudent extends AbstractFormServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected Params createParams(HttpServletRequest req) {
//		System.out.printf("B: Student object %s exist in request attributes\n", req.getAttribute("student") != null ? "does" : "does not");
		// Make sure the student User object exists.
		// Clear out the password hash, though.
		// It will be loaded from form data on a POST
		// when the form parameters are unmarshaled.
		Params params = new Params(req).addFromRequest("student", User.class);
		User student = params.get("student");
		student.setPasswordHash("");
		return params;
	}

	@Override
	protected LogicOutcome doLogic(Params params, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		User student = (User) req.getAttribute("student");
		
		EditStudentController editStudent = new EditStudentController();
		editStudent.execute(student);
		req.setAttribute("resultmsg", "Editing student " + student.getUsername() + " successfully");
		
		// clear the password hash
		student.setPasswordHash("");
		
		return LogicOutcome.STAY_ON_PAGE;
	}
}
