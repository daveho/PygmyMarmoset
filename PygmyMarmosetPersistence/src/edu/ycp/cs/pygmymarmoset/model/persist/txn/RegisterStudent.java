// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.model.persist.txn;

import java.sql.Connection;
import java.sql.SQLException;

import edu.ycp.cs.pygmymarmoset.app.model.BCrypt;
import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Role;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;

public class RegisterStudent extends DatabaseRunnable<Boolean> {
	private User student;
	private Course course;
	private Role role;

	public RegisterStudent(User student, Course course, Role role) {
		super("register student in course");
		this.student = student;
		this.course = course;
		this.role = role;
	}

	@Override
	public Boolean execute(Connection conn) throws SQLException {
		// See if user already exists
		FindUserForUsername findUser = new FindUserForUsername(student.getUsername());
		User existing = findUser.execute(conn);
		if (existing != null) {
			// Use existing User
			student = existing;
		} else {
			// Insert new User
			String salt = BCrypt.gensalt(12);
			String plaintext = student.getPasswordHash();
			student.setPasswordHash(BCrypt.hashpw(plaintext, salt));
			InsertModelObject<User> insertUser = new InsertModelObject<User>(student);
			insertUser.execute(conn);
		}
		
		// Insert student Role
		Role studentRole = role;
		role.setUserId(student.getId()); // user id should be known now
		role.setCourseId(course.getId());
		InsertModelObject<Role> insertRole = new InsertModelObject<>(studentRole);
		insertRole.execute(conn);
		
		// Success!
		return true;
	}

}
