package edu.ycp.cs.pygmymarmoset.model.persist;

import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.User;

public interface IDatabase {
	public void createModelClassTable(Class<?> modelCls);
	public User findUserForUsername(String username);
	public boolean createCourse(Course course);
	public boolean createUser(User user);
}
