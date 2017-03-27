package edu.ycp.cs.pygmymarmoset.app.model;

public class Submission {
	private int id;
	private int projectId;
	private int userId;
	private int submissionNumber;
	private long timestamp;
	private boolean isZip;
	
	public Submission() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getSubmissionNumber() {
		return submissionNumber;
	}

	public void setSubmissionNumber(int submissionNumber) {
		this.submissionNumber = submissionNumber;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isZip() {
		return isZip;
	}

	public void setZip(boolean isZip) {
		this.isZip = isZip;
	}
}
