// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.servlet;

@Route(pattern="/u/submission/*", view="/_view/studentSubmission.jsp")
@Navigation(parent=StudentProject.class)
@CrumbSpec(
		text="Submission %s",
		items={PathInfoItem.COURSE_ID, PathInfoItem.STUDENT_ID, PathInfoItem.PROJECT_ID, PathInfoItem.SUBMISSION_ID})
public class StudentSubmission extends AbstractSubmissionServlet {
	private static final long serialVersionUID = 1L;

	// logic is implemented in superclass
}
