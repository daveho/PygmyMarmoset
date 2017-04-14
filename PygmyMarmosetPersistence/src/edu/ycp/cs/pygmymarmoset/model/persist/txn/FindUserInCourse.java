// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.model.persist.txn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Pair;
import edu.ycp.cs.pygmymarmoset.app.model.Role;
import edu.ycp.cs.pygmymarmoset.app.model.Roles;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;
import edu.ycp.cs.pygmymarmoset.model.persist.Query;

public class FindUserInCourse extends DatabaseRunnable<Pair<User, Roles>> {
	private Course course;
	private int userId;
	
	public FindUserInCourse(Course course, int userId) {
		super("find user in course");
		this.course = course;
		this.userId = userId;
	}

	@Override
	public Pair<User, Roles> execute(Connection conn) throws SQLException {
		// Note that this join returns the same user data
		// for each Role.
		PreparedStatement stmt = prepareStatement(
				conn,
				"select u.*, r.* from users as u, roles as r" +
				" where u.id = ?" +
				" and u.id = r.userid" +
				" and r.courseid = ?");
		stmt.setInt(1, userId);
		stmt.setInt(2, course.getId());
		
		ResultSet resultSet = executeQuery(stmt);
		User user = null;
		List<Role> roles = new ArrayList<>();
		while (resultSet.next()) {
			user = new User();
			int index = Query.loadFields(user, resultSet);
			Role role = new Role();
			Query.loadFields(role, resultSet, index);
			roles.add(role);
		}
		if (user == null) {
			return null; // user is not registered in the course
		}
		Roles userRoles = new Roles();
		userRoles.addAll(roles);
		return new Pair<>(user, userRoles);
	}
}
