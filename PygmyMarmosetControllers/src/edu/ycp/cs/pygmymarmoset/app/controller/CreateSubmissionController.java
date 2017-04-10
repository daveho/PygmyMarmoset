package edu.ycp.cs.pygmymarmoset.app.controller;

import java.io.InputStream;

import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.model.Submission;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseProvider;
import edu.ycp.cs.pygmymarmoset.model.persist.IDatabase;

public class CreateSubmissionController {
	public Submission execute(Project project, User student, String fileName, InputStream uploadData) {
		if (fileName == null) {
			fileName = "unknown";
		}
		IDatabase db = DatabaseProvider.getInstance();
		return db.createSubmission(project, student, fileName, uploadData);
	}
}
