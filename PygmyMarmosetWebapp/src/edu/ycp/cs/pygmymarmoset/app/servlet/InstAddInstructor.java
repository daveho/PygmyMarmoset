// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.controller.AddInstructorController;
import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Role;
import edu.ycp.cs.pygmymarmoset.app.model.User;

@Route(pattern="/i/addInst/*", view="/_view/instAddInst.jsp")
@Navigation(parent=InstCourse.class)
@CrumbSpec(text="Add instructor", items={PathInfoItem.COURSE_ID})
public class InstAddInstructor extends AbstractFormServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected Params createParams(HttpServletRequest req) {
		return new Params(req)
				.add("inst", User.class)
				.add("role", Role.class);
	}

	@Override
	protected LogicOutcome doLogic(Params params, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Course course = (Course) req.getAttribute("course");
		User inst = params.get("inst");
		Role role = params.get("role");
		AddInstructorController addInst = new AddInstructorController();
		addInst.execute(course, inst.getUsername(), role.getSection());
		// Success!
		req.setAttribute("resultmsg", "Successfully added instructor " + inst.getUsername());
		req.setAttribute("inst", new User()); // clear out form fields
		req.setAttribute("role", new Role()); // clear out form fields
		return LogicOutcome.STAY_ON_PAGE;
	}
}
