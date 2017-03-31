package edu.ycp.cs.pygmymarmoset.app.controller;

import edu.ycp.cs.pygmymarmoset.app.model.BCrypt;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseProvider;
import edu.ycp.cs.pygmymarmoset.model.persist.IDatabase;

public class CreateUserController {
	public boolean createUser(User user) {
		// Note that the value of the password hash
		// is assumed to be plaintext (i.e., it's what
		// was entered in the form by the user
		// creating the new user account.)
		String plaintextPasswd = user.getPasswordHash();
		String salt = BCrypt.gensalt(12);
		user.setPasswordHash(BCrypt.hashpw(plaintextPasswd, salt));
		
		IDatabase db = DatabaseProvider.getInstance();
		return db.createUser(user);
	}
}
