// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.controller.GetSingleStudentProjectActivityController;
import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Pair;
import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.model.User;

@Route(pattern="/i/student/*", view="/_view/instStudent.jsp")
@Navigation(parent=InstCourse.class)
@CrumbSpec(text="Student %S", items={PathInfoItem.COURSE_ID, PathInfoItem.STUDENT_ID})
public class InstStudent extends AbstractServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Course course = (Course) req.getAttribute("course");
		User student = (User) req.getAttribute("student");
		
		GetSingleStudentProjectActivityController getSingleStudentActivity =
				new GetSingleStudentProjectActivityController();
		
		List<Pair<Project, Integer[]>> studentActivity = getSingleStudentActivity.execute(course, student);
		req.setAttribute("studentActivity", studentActivity);
		
		delegateToView(req, resp);
	}
}
