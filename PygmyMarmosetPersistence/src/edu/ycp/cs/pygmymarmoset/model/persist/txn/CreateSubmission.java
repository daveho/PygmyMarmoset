package edu.ycp.cs.pygmymarmoset.model.persist.txn;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.io.IOUtils;

import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.model.Submission;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;

public class CreateSubmission extends DatabaseRunnable<Submission> {
	private Project project;
	private User student;
	private String fileName;
	private InputStream uploadData;

	public CreateSubmission(Project project, User student, String fileName, InputStream uploadData) {
		super("create submission");
		this.project = project;
		this.student = student;
		this.fileName = fileName;
		this.uploadData = uploadData;
	}
	
	@Override
	public Submission execute(Connection conn) throws SQLException {
		// Find the maximum submission id
		PreparedStatement findMax = prepareStatement(
				conn,
				"select max(s.submissionnumber) from submissions as s" +
				" where s.projectid = ? and s.userid = ?");
		findMax.setInt(1, project.getId());
		findMax.setInt(2, student.getId());
		ResultSet findMaxResultSet = executeQuery(findMax);
		int maxSub;
		if (findMaxResultSet.next()) {
			maxSub = findMaxResultSet.getInt(1);
		} else {
			maxSub = 0;
		}
		
		// Create a new Submission object and insert it
		Submission submission = new Submission();
		submission.setProjectId(project.getId());
		submission.setUserId(student.getId());
		submission.setSubmissionNumber(maxSub + 1);
		submission.setTimestamp(System.currentTimeMillis());
		submission.setFileName(fileName);
		InsertModelObject<Submission> insertSubmission = new InsertModelObject<Submission>(submission);
		insertSubmission.execute(conn);
		
		// Insert new SubmissionBlob record
		PreparedStatement stmt = prepareStatement(conn, "insert into submissionblobs (submissionid, data) values (?, ?)");
		stmt.setInt(1, submission.getId());
		Blob blob = conn.createBlob();
		OutputStream os = blob.setBinaryStream(1);
		try {
			try {
				IOUtils.copy(uploadData, os);
			} finally {
				IOUtils.closeQuietly(os);
			}
		} catch (IOException e) {
			throw new SQLException("Error uploading file data to database", e);
		}
		stmt.setBlob(2, blob);
		stmt.executeUpdate();
		
		return submission;
	}
}
