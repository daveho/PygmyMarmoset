package edu.ycp.cs.pygmymarmoset.app.model;

public class Submission {
	@PrimaryKey
	private int id;
	
	@NonUnique
	private int projectId;
	
	@NonUnique
	private int userId;
	
	private int submissionNumber;
	
	@Timestamp
	private long timestamp;
	
	@Desc(size=100)
	private String fileName;
	
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
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileName() {
		return fileName;
	}
}
