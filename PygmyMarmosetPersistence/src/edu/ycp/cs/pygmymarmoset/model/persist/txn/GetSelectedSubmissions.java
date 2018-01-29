// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017,2018 David H. Hovemeyer <david.hovemeyer@gmail.com>
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

import edu.ycp.cs.pygmymarmoset.app.model.GetSubmissionsMode;
import edu.ycp.cs.pygmymarmoset.app.model.ISubmissionCollector;
import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.model.Submission;
import edu.ycp.cs.pygmymarmoset.app.model.SubmissionStatus;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;
import edu.ycp.cs.pygmymarmoset.model.persist.Query;

public class GetSelectedSubmissions extends DatabaseRunnable<Boolean> {
	private Project project;
	private ISubmissionCollector collector;
	private GetSubmissionsMode mode;

	public GetSelectedSubmissions(Project project, ISubmissionCollector collector, GetSubmissionsMode mode) {
		super("Get selected submissions for project");
		this.project = project;
		this.collector = collector;
		this.mode = mode;
	}

	@Override
	public Boolean execute(Connection conn) throws SQLException {
		StringBuilder query = new StringBuilder();

		query.append(
				"select u.*, s.*, 0 as status, sb.data" +
				"  from submissions as s," +
				"       (select s.userid as userid, max(s.timestamp) as maxts" +
				"          from submissions as s" +
				"         where s.projectid = ?" +                     // project id
				"           and s.timestamp <= ?" +                    // ontime ts
				"         group by s.userid) as y," +
				"       users as u," +
				"       submissionblobs as sb" +
				" where s.projectid = ?" +                             // project id
				"   and u.id = y.userid" +
				"   and s.userid = u.id" +
				"   and s.timestamp = y.maxts" +
				"   and s.id = sb.submissionid" +
				"" +
				" union" +
				"" +
				" select u.*, s.*, 1 as status, sb.data" +
				"  from submissions as s," +
				"       (select s.userid as userid, max(s.timestamp) as maxts" +
				"          from submissions as s" +
				"         where s.projectid = ?" +                     // project id
				"           and s.timestamp > ?" +                     // ontime ts
				"           and s.timestamp <= ?" +                    // late ts
				"         group by s.userid) as y," +
				"       users as u," +
				"       submissionblobs as sb" +
				" where s.projectid = ?" +                             // project id
				"   and u.id = y.userid" +
				"   and s.userid = u.id" +
				"   and s.timestamp = y.maxts" +
				"   and s.id = sb.submissionid");
		
		if (mode == GetSubmissionsMode.ALL) {
			query.append(
					" union" +
					"" +
					" select u.*, s.*, 2 as status, sb.data" +
					"  from submissions as s," +
					"       (select s.userid as userid, max(s.timestamp) as maxts" +
					"          from submissions as s" +
					"         where s.projectid = ?" +                  // project id
					"           and s.timestamp > ?" +                  // late ts
					"         group by s.userid) as y," +
					"       users as u," +
					"       submissionblobs as sb" +
					" where s.projectid = ?" +                          // project id
					"   and u.id = y.userid" +
					"   and s.userid = u.id" +
					"   and s.timestamp = y.maxts" +
					"   and s.id = sb.submissionid");
		}
		
		PreparedStatement stmt = prepareStatement(conn, query.toString());

		stmt.setInt(1, project.getId());
		stmt.setLong(2, project.getOntime());
		stmt.setInt(3, project.getId());
		stmt.setInt(4, project.getId());
		stmt.setLong(5, project.getOntime());
		stmt.setLong(6, project.getLate());
		stmt.setInt(7, project.getId());
		if (mode == GetSubmissionsMode.ALL) {
			stmt.setInt(8, project.getId());
			stmt.setLong(9, project.getLate());
			stmt.setInt(10, project.getId());
		}
		
		ResultSet resultSet = executeQuery(stmt);
		
		SubmissionStatus[] statuses = SubmissionStatus.values();
		
		while (resultSet.next()) {
			// User fields are first
			User user = new User();
			int index = Query.loadFields(user, resultSet);
			// Then come the Submission fields
			Submission submission = new Submission();
			index = Query.loadFields(submission, resultSet, index);
			// Status field is immediately after the Submission fields
			SubmissionStatus status = statuses[resultSet.getInt(index)];
			// Blob data is after the ontime field
			Blob dataBlob = resultSet.getBlob(index + 1);
			InputStream dataIn = dataBlob.getBinaryStream();
			try {
				collector.collect(user, submission, status, dataIn);
			} finally {
				IOUtils.closeQuietly(dataIn);
			}
		}
		
		return true;
	}
}
