package edu.ycp.cs.pygmymarmoset.model.persist;

import edu.ycp.cs.pygmymarmoset.app.model.User;

public interface IDatabase {
	public User findUserForUsername(String username);
}
