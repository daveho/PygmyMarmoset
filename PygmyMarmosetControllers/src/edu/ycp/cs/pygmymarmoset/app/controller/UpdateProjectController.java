package edu.ycp.cs.pygmymarmoset.app.controller;

import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseProvider;
import edu.ycp.cs.pygmymarmoset.model.persist.IDatabase;

public class UpdateProjectController {
	public boolean execute(Project project) {
		IDatabase db = DatabaseProvider.getInstance();
		return db.updateProject(project);
	}
}
