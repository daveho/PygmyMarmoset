package edu.ycp.cs.pygmymarmoset.app.controller;

import edu.ycp.cs.pygmymarmoset.app.model.ISubmissionCollector;
import edu.ycp.cs.pygmymarmoset.app.model.Submission;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseProvider;
import edu.ycp.cs.pygmymarmoset.model.persist.IDatabase;

public class GetSubmissionDataController {
	public boolean execute(Submission submission, ISubmissionCollector collector) {
		IDatabase db = DatabaseProvider.getInstance();
		return db.getSubmissionData(submission, collector);
	}
}
