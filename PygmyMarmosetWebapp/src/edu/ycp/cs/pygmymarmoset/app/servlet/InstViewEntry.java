// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.servlet;

@Route(pattern="/i/entry/*", view="/_view/instViewEntry.jsp")
@Navigation(parent=InstSubmission.class)
@CrumbSpec(
		text="Entry %e",
		items={PathInfoItem.COURSE_ID, PathInfoItem.PROJECT_ID, PathInfoItem.STUDENT_ID,
				PathInfoItem.SUBMISSION_ID, PathInfoItem.ENTRY_INDEX})
public class InstViewEntry extends AbstractViewEntryServlet {
	private static final long serialVersionUID = 1L;

	public InstViewEntry() {
		super(4);
	}
}
