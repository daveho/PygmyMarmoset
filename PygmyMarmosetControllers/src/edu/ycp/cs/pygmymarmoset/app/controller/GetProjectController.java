package edu.ycp.cs.pygmymarmoset.app.controller;

import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseProvider;
import edu.ycp.cs.pygmymarmoset.model.persist.IDatabase;

public class GetProjectController {
	public Project execute(int projectId) {
		IDatabase db = DatabaseProvider.getInstance();
		return db.findProjectForProjectId(projectId);
	}
}
