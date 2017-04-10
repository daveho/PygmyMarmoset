package edu.ycp.cs.pygmymarmoset.app.model;

public class SubmissionBlob {
	@PrimaryKey
	private int id;
	
	private int submissionId;
	
	@Desc(size=100)
	private String fileName;
	
	@Blob
	private byte[] data;
	
	public SubmissionBlob() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSubmissionId() {
		return submissionId;
	}

	public void setSubmissionId(int submissionId) {
		this.submissionId = submissionId;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileName() {
		return fileName;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
}
