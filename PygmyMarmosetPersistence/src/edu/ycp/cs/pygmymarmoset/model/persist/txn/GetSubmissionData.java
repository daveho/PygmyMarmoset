// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

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

public class GetSubmissionData extends DatabaseRunnable<Boolean> {
	private Submission submission;
	private IReadBlob reader;

	public GetSubmissionData(Submission submission, IReadBlob reader) {
		super("get submission data");
		this.submission = submission;
		this.reader = reader;
	}

	@Override
	public Boolean execute(Connection conn) throws SQLException {
		PreparedStatement stmt = prepareStatement(
				conn,
				"select sb.data from submissionblobs as sb" +
				" where sb.submissionid = ?");
		stmt.setInt(1, submission.getId());
		
		ResultSet resultSet = executeQuery(stmt);
		if (!resultSet.next()) {
			throw new PersistenceException("No submission with id " + submission.getId());
		}
		
		Blob blob = resultSet.getBlob(1);
		InputStream dataIn = blob.getBinaryStream();
		try {
			reader.readBlob(dataIn, submission.getFileName());
		} finally {
			IOUtils.closeQuietly(dataIn);
		}
		
		return true;
	}
}
