// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.servlet;

@Route(pattern="/u/entry/*", view="/_view/studentViewEntry.jsp")
@Navigation(parent=StudentSubmission.class)
@CrumbSpec(
		text="Entry %e",
		items={PathInfoItem.COURSE_ID, PathInfoItem.STUDENT_ID, PathInfoItem.PROJECT_ID,
				PathInfoItem.SUBMISSION_ID, PathInfoItem.ENTRY_INDEX})
public class StudentViewEntry extends AbstractViewEntryServlet {
	private static final long serialVersionUID = 1L;
	
	public StudentViewEntry() {
		super(4);
	}
}
