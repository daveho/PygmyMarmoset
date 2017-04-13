package edu.ycp.cs.pygmymarmoset.model.persist.txn;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.io.IOUtils;

import edu.ycp.cs.pygmymarmoset.app.model.ISubmissionCollector;
import edu.ycp.cs.pygmymarmoset.app.model.PersistenceException;
import edu.ycp.cs.pygmymarmoset.app.model.Submission;
import edu.ycp.cs.pygmymarmoset.app.model.SubmissionStatus;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;
import edu.ycp.cs.pygmymarmoset.model.persist.Query;

public class GetSubmissionData extends DatabaseRunnable<Boolean> {
	private Submission submission;
	private ISubmissionCollector collector;

	public GetSubmissionData(Submission submission, ISubmissionCollector collector) {
		super("get submission data");
		this.submission = submission;
		this.collector = collector;
	}

	@Override
	public Boolean execute(Connection conn) throws SQLException {
		PreparedStatement stmt = prepareStatement(
				conn,
				"select u.*, sb.data from users as u, submissions as s, submissionblobs as sb" +
				" where sb.submissionid = s.id and s.id = ? and s.userid = u.id");
		stmt.setInt(1, submission.getId());
		
		ResultSet resultSet = executeQuery(stmt);
		if (!resultSet.next()) {
			throw new PersistenceException("No submission with id " + submission.getId());
		}
		
		User user = new User();
		int index = Query.loadFields(user, resultSet);
		
		Blob blob = resultSet.getBlob(index);
		InputStream dataIn = blob.getBinaryStream();
		try {
			// Note that status should not be trusted
			collector.collect(user, submission, SubmissionStatus.ONTIME, dataIn);
		} finally {
			IOUtils.closeQuietly(dataIn);
		}
		
		return true;
	}
}
