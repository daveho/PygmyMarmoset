package edu.ycp.cs.pygmymarmoset.app.controller;

import java.util.List;

import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Pair;
import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseProvider;
import edu.ycp.cs.pygmymarmoset.model.persist.IDatabase;

public class GetStudentProjectsController {
	/**
	 * Get pairs of ({@link Project}, submission count) for specified
	 * student in specified {@link Course}.
	 * 
	 * @param course the {@link Course}
	 * @param student the student
	 * @return list of pairs of ({@link Project}, submission count)
	 */
	public List<Pair<Project, Integer>> getStudentProjects(Course course, User student) {
		IDatabase db = DatabaseProvider.getInstance();
		return db.getStudentProjects(course, student);
	}
}
