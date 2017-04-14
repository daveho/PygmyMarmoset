// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.model.persist.txn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.ycp.cs.pygmymarmoset.app.model.Submission;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;
import edu.ycp.cs.pygmymarmoset.model.persist.Query;

public class FindSubmissionForSubmissionId extends DatabaseRunnable<Submission> {
	private int submissionId;
	
	public FindSubmissionForSubmissionId(int submissionId) {
		super("find submission");
		this.submissionId = submissionId;
	}

	@Override
	public Submission execute(Connection conn) throws SQLException {
		PreparedStatement stmt = prepareStatement(conn, "select s.* from submissions as s where s.id = ?");
		stmt.setInt(1, submissionId);
		ResultSet resultSet = executeQuery(stmt);
		if (!resultSet.next()) {
			return null;
		}
		Submission submission = new Submission();
		Query.loadFields(submission, resultSet);
		return submission;
	}
}
