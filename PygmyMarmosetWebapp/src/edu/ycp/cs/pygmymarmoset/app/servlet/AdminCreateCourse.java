// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.controller.CreateCourseController;
import edu.ycp.cs.pygmymarmoset.app.controller.ListAllTermsController;
import edu.ycp.cs.pygmymarmoset.app.model.Course;

@Route(pattern="/a/createCourse", view="/_view/adminCreateCourse.jsp")
@Navigation(parent=AdminIndex.class)
@CrumbSpec(text="Create course")
public class AdminCreateCourse extends AbstractFormServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected Params createParams(HttpServletRequest req) {
		// Note that because there is a Term drop-down,
		// the List of Terms must be in the request attributes.
		ListAllTermsController termsController = new ListAllTermsController();
		req.setAttribute("terms", termsController.getAllTerms());
		
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
