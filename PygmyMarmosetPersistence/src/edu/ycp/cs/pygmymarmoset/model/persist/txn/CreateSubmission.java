package edu.ycp.cs.pygmymarmoset.model.persist.txn;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.CountingInputStream;
import org.apache.commons.io.input.TeeInputStream;
import org.apache.commons.io.output.NullOutputStream;

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
		
		// Count the number of bytes in the uploaded file.
		CountingInputStream cin = new CountingInputStream(uploadData);
		
		// Use a TeeInputStream to transparently copy the uploaded file data
		// to the Blob.  Use a ZipInputStream on top of the TeeInputStream
		// to attempt to read the file contents as a zip file.  This allows
		// us to reliably determine whether or not the file, in its entirety,
		// is a zipfile.
		TeeInputStream tis = new TeeInputStream(cin, os);
		ZipInputStream zis = new ZipInputStream(tis);
		
		// Start by assuming the file is not a zip file.
		boolean isZip = false;
		try {
			try {
				try {
					// Read the entire zip file.
					readZip(zis);
					
					// Drain any remaining input, just to be safe
					// (i.e., don't assume that ZipInputStream read every
					// byte in the file.)
					drain(tis);
					
					// The entire zip file was read, so we can safely
					// assume that it is a zip file.
					isZip = true;
				} catch (ZipException e) {
					// Input does not appear to be a zipfile.
					// Drain all of the 
					drain(tis);
				}
			} finally {
				IOUtils.closeQuietly(uploadData);
				IOUtils.closeQuietly(os);
			}
		} catch (IOException e) {
			throw new SQLException("Error uploading file data to database", e);
		}
		
		stmt.setBlob(2, blob);
		stmt.executeUpdate();
		
		// Now we know how many bytes the blob contains
		// and whether or not it is a zipfile.
		PreparedStatement updateSize = prepareStatement(
				conn,
				"update submissions set size = ?, zip = ? where id = ?");
		updateSize.setLong(1, cin.getByteCount());
		updateSize.setBoolean(2, isZip);
		updateSize.setInt(3, submission.getId());
		updateSize.executeUpdate();
		
		return submission;
	}
	
	public static void readZip(ZipInputStream zis) throws IOException {
		for (;;) {
			ZipEntry entry = zis.getNextEntry();
			if (entry == null) {
				break;
			}
			// Read the entry's data.
			drain(zis);
		}
	}
	
	public static void drain(InputStream is) throws IOException {
		NullOutputStream sink = new NullOutputStream();
		IOUtils.copy(is, sink);
	}
}
