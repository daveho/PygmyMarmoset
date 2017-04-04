package edu.ycp.cs.pygmymarmoset.app.controller;

import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseProvider;
import edu.ycp.cs.pygmymarmoset.model.persist.IDatabase;

public class FindUserController {
	public User execute(String username) {
		IDatabase db = DatabaseProvider.getInstance();
		return db.findUserForUsername(username);
	}
}
