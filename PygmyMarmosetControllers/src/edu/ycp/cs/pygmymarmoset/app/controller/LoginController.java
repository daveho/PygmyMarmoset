// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.controller;

import edu.ycp.cs.pygmymarmoset.app.model.BCrypt;
import edu.ycp.cs.pygmymarmoset.app.model.LoginCredentials;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseProvider;
import edu.ycp.cs.pygmymarmoset.model.persist.IDatabase;

public class LoginController {
	public User execute(LoginCredentials creds) {
		IDatabase db = DatabaseProvider.getInstance();
		
		User user = db.findUserForUsername(creds.getUsername());
		if (user == null) {
			return null;
		}
		
		//System.out.printf("Found user: username=%s, passwordHash=%s\n", user.getUsername(), user.getPasswordHash());
		
		if (!BCrypt.checkpw(creds.getPassword(), user.getPasswordHash())) {
			return null;
		}
		
		// There's not really any danger of returning the User
		// object with the password hash intact, but it's also not
		// necessary, and if the user changes his/her password
		// during the session, it might not be accurate.  The
		// data in the database is the only authoritative password hash.
		user.setPasswordHash("");
		
		return user;
	}
}
