// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.model.persist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Role;
import edu.ycp.cs.pygmymarmoset.app.model.Roles;
import edu.ycp.cs.pygmymarmoset.app.model.User;

public class GetUserRolesInCourse extends DatabaseRunnable<Roles> {
	private User user;
	private Course course;
	
	public GetUserRolesInCourse(User user, Course course) {
		super("get user roles in course");
		this.user = user;
		this.course = course;
	}

	@Override
	public Roles execute(Connection conn) throws SQLException {
		PreparedStatement stmt = prepareStatement(conn, "select r.* from roles as r where r.userid = ? and r.courseid = ?");
		stmt.setInt(1, user.getId());
		stmt.setInt(2, course.getId());
		ResultSet resultSet = executeQuery(stmt);
		Roles result = new Roles();
		while (resultSet.next()) {
			Role role = new Role();
			Query.loadFields(role, resultSet);
			result.addRole(role);
		}
		return result;
	}
}
