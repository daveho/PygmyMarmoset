// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.model.persist.txn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;

public class UpdateUserPasswordHash extends DatabaseRunnable<Boolean> {
	private User user;
	private String passwordHash;
	
	public UpdateUserPasswordHash(User user, String passwordHash) {
		super("update user password");
		this.user = user;
		this.passwordHash = passwordHash;
	}

	@Override
	public Boolean execute(Connection conn) throws SQLException {
		PreparedStatement stmt = prepareStatement(
				conn,
				"update users set passwordhash = ? where id = ?");
		stmt.setString(1, passwordHash);
		stmt.setInt(2, user.getId());
		
		stmt.executeUpdate();
		
		return true;
	}
}
