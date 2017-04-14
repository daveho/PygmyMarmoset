// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.model.persist.txn;

import java.sql.Connection;
import java.sql.SQLException;

import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;

public class CreateProject extends DatabaseRunnable<Boolean> {
	private Course course;
	private Project project;
	
	public CreateProject(Course course, Project project) {
		super("create project");
		this.course = course;
		this.project = project;
	}

	@Override
	public Boolean execute(Connection conn) throws SQLException {
		project.setCourseId(course.getId());
		InsertModelObject<Project> insertProj = new InsertModelObject<Project>(project);
		return insertProj.execute(conn);
	}
}
