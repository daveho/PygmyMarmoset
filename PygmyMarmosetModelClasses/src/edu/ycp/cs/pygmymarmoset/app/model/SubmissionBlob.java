package edu.ycp.cs.pygmymarmoset.app.model;

public class SubmissionBlob {
	@PrimaryKey
	private int id;
	
	private int submissionId;
	
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

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
}
