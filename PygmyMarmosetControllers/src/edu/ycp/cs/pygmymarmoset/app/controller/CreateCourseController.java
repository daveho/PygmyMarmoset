// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.controller;

import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.ModelObjectUtil;
import edu.ycp.cs.pygmymarmoset.app.model.ValidationException;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseProvider;
import edu.ycp.cs.pygmymarmoset.model.persist.IDatabase;

public class CreateCourseController {
	public boolean execute(Course course) {
		IDatabase db = DatabaseProvider.getInstance();

		// FIXME: would be nice to have some kind of "magic" to validate model object
		if (!ModelObjectUtil.isSet(course.getName())) {
			// FIXME: should communicate which field failed to validate
			throw new ValidationException("Missing course name");
		}

		return db.createCourse(course);
	}
}
