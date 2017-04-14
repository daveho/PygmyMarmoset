// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.servlet;

import edu.ycp.cs.pygmymarmoset.app.model.Project;

public enum PathInfoItem {
	/** Path info specifies a {@link Course} id. */
	COURSE_ID,
	/** Path info specifies a {@link User} id of a student. */
	STUDENT_ID,
	/** Path info specifies a {@link Project} id. */
	PROJECT_ID,
	/** Path info specifies a {@link Submission} id. */
	SUBMISSION_ID,
	/** Path info specifies the index of an {@link IndexEntry} in a submission. */
	ENTRY_INDEX,
}
