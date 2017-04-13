package edu.ycp.cs.pygmymarmoset.app.model;

import java.io.InputStream;

public interface ISubmissionCollector {
	public void collect(User user, Submission submission, SubmissionStatus status, InputStream data);
}
