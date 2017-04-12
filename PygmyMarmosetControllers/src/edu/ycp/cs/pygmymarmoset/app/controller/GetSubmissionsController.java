package edu.ycp.cs.pygmymarmoset.app.controller;

import java.util.List;

import edu.ycp.cs.pygmymarmoset.app.model.Pair;
import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.model.Submission;
import edu.ycp.cs.pygmymarmoset.app.model.SubmissionStatus;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseProvider;
import edu.ycp.cs.pygmymarmoset.model.persist.IDatabase;

public class GetSubmissionsController {
	public List<Pair<Submission, SubmissionStatus>> execute(Project project, User student) {
		IDatabase db = DatabaseProvider.getInstance();
		return db.getSubmissions(project, student);
	}
}
