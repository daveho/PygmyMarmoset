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
		
		return user;
	}
}
