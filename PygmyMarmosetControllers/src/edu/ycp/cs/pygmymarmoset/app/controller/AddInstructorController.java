package edu.ycp.cs.pygmymarmoset.app.controller;

import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseProvider;
import edu.ycp.cs.pygmymarmoset.model.persist.IDatabase;

public class AddInstructorController {
	public boolean execute(Course course, String username, int section) {
		IDatabase db = DatabaseProvider.getInstance();
		return db.addInstructor(course, username, section);
	}
}
