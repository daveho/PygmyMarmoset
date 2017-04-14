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

import edu.ycp.cs.pygmymarmoset.app.model.ISubmissionCollector;
import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.model.Submission;
import edu.ycp.cs.pygmymarmoset.app.model.SubmissionStatus;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;
import edu.ycp.cs.pygmymarmoset.model.persist.Query;

public class GetOntimeAndLateSubmissions extends DatabaseRunnable<Boolean> {
	private Project project;
	private ISubmissionCollector collector;
	
	public GetOntimeAndLateSubmissions(Project project, ISubmissionCollector collector) {
		super("get ontime and late submissions");
		this.project = project;
		this.collector = collector;
	}

	@Override
	public Boolean execute(Connection conn) throws SQLException {
		// Find the last ontime and late submissions for each student.
		PreparedStatement stmt = prepareStatement(
				conn,
				"select u.*, y.*, sb.data" +
				" from users as u," + 
				"      (select s.*, 1 as ontime" +
				"         from submissions as s" +
				"          inner join (select ss.id, ss.userid, max(ss.timestamp) as maxts" +
				"                        from submissions as ss" +
				"                        where ss.projectid = ? and ss.timestamp <= ?" +
				"                       group by ss.userid) as x" +
				"           on s.userid = x.userid and s.timestamp = x.maxts" +
				"       union " +
				"       select s.*, 0 as ontime" +
				"         from submissions as s" +
				"          inner join (select ss.id, ss.userid, max(ss.timestamp) as maxts" +
				"                        from submissions as ss" +
				"                        where ss.projectid = ? and ss.timestamp > ? and ss.timestamp <= ?" +
				"                       group by ss.userid) as x" +
				"           on s.userid = x.userid and s.timestamp = x.maxts) as y," +
				"    submissionblobs as sb" +
				" where u.id = y.userid" +
				"   and y.id = sb.id"
				);
		stmt.setInt(1, project.getId());
		stmt.setLong(2, project.getOntime());
		stmt.setInt(3, project.getId());
		stmt.setLong(4, project.getOntime());
		stmt.setLong(5, project.getLate());
		
		ResultSet resultSet = executeQuery(stmt);
		
		while (resultSet.next()) {
			// User fields are first
			User user = new User();
			int index = Query.loadFields(user, resultSet);
			// Then come the Submission fields
			Submission submission = new Submission();
			index = Query.loadFields(submission, resultSet, index);
			// Ontime field is immediately after the Submission fields
			boolean ontime = (resultSet.getInt(index) == 1);
			SubmissionStatus status = ontime ? SubmissionStatus.ONTIME : SubmissionStatus.LATE;
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
