package edu.ycp.cs.pygmymarmoset.app.controller;

import java.util.List;

import edu.ycp.cs.pygmymarmoset.app.model.Pair;
import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseProvider;
import edu.ycp.cs.pygmymarmoset.model.persist.IDatabase;

public class GetStudentProjectActivityController {
	public List<Pair<User, Integer>> execute(Project project) {
		IDatabase db = DatabaseProvider.getInstance();
		return db.getStudentProjectActivity(project);
	}
}
