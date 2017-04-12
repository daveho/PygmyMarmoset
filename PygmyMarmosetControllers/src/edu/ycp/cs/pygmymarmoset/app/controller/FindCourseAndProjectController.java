package edu.ycp.cs.pygmymarmoset.app.controller;

import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Pair;
import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseProvider;
import edu.ycp.cs.pygmymarmoset.model.persist.IDatabase;

public class FindCourseAndProjectController {

	public Pair<Course, Project> execute(String courseName, String termName, Integer year, String projectName) {
		IDatabase db = DatabaseProvider.getInstance();
		return db.findCourseAndProject(courseName, termName, year, projectName);
	}

}
