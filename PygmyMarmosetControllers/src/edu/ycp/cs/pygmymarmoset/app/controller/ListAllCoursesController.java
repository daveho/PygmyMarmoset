package edu.ycp.cs.pygmymarmoset.app.controller;

import java.util.List;

import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseProvider;
import edu.ycp.cs.pygmymarmoset.model.persist.IDatabase;

public class ListAllCoursesController {

	public List<Course> execute() {
		IDatabase db = DatabaseProvider.getInstance();
		return db.getAllCourses();
	}

}
