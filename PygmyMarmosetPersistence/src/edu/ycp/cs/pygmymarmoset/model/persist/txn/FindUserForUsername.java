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

import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;
import edu.ycp.cs.pygmymarmoset.model.persist.Query;

public class FindUserForUsername extends DatabaseRunnable<User> {
	private String username;

	public FindUserForUsername(String username) {
		super("find user for username");
		this.username = username;
	}

	@Override
	public User execute(Connection conn) throws SQLException {
		PreparedStatement stmt = prepareStatement(conn, "select u.* from users as u where u.username = ?");
		stmt.setString(1, username);
		ResultSet resultSet = executeQuery(stmt);
		if (!resultSet.next()) {
			// No such user
			return null;
		}
		User user = new User();
		Query.loadFields(user, resultSet);
		return user;
	}
}
