package edu.ycp.cs.pygmymarmoset.model.persist.txn;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.io.IOUtils;

import edu.ycp.cs.pygmymarmoset.app.model.IReadBlob;
import edu.ycp.cs.pygmymarmoset.app.model.PersistenceException;
import edu.ycp.cs.pygmymarmoset.app.model.Submission;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;

public class ReadSubmissionBlob extends DatabaseRunnable<Boolean> {
	private Submission submission;
	private IReadBlob reader;

	public ReadSubmissionBlob(Submission submission, IReadBlob reader) {
		super("read submission blob");
		this.submission = submission;
		this.reader = reader;
	}

	@Override
	public Boolean execute(Connection conn) throws SQLException {
		PreparedStatement stmt = prepareStatement(conn, "select sb.data from submissionblobs as sb where sb.submissionid = ?");
		stmt.setInt(1, submission.getId());
		ResultSet resultSet = executeQuery(stmt);
		if (!resultSet.next()) {
			throw new PersistenceException("No blob data for submission " + submission.getId());
		}
		Blob blob = resultSet.getBlob(1);
		InputStream blobIn = blob.getBinaryStream();
		try {
			// Read the blob data!
			reader.readBlob(blobIn, submission.getFileName());
			return true;
		} finally {
			IOUtils.closeQuietly(blobIn);
		}
	}
}
