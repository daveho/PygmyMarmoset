package edu.ycp.cs.pygmymarmoset.app.controller;

import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.model.Submission;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseProvider;
import edu.ycp.cs.pygmymarmoset.model.persist.IDatabase;

public class GetSubmissionController {
	public Submission execute(int submissionId) {
		IDatabase db = DatabaseProvider.getInstance();
		Submission sub = db.findSubmissionForSubmissionId(submissionId);
		return sub;
	}
}
