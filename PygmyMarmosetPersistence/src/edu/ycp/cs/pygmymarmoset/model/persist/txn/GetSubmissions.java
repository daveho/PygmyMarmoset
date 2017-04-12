package edu.ycp.cs.pygmymarmoset.model.persist.txn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs.pygmymarmoset.app.model.Pair;
import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.model.Submission;
import edu.ycp.cs.pygmymarmoset.app.model.SubmissionStatus;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;
import edu.ycp.cs.pygmymarmoset.model.persist.Query;

public class GetSubmissions extends DatabaseRunnable<List<Pair<Submission, SubmissionStatus>>> {
	private Project project;
	private User student;

	public GetSubmissions(Project project, User student) {
		super("get project submissions for student");
		
		this.project = project;
		this.student = student;
	}

	@Override
	public List<Pair<Submission, SubmissionStatus>> execute(Connection conn) throws SQLException {
		PreparedStatement stmt = prepareStatement(
				conn,
				"select s.*," +
				"  case when s.timestamp <= ? then 0" + // ontime
				"       when s.timestamp > ? and s.timestamp <= ? then 1" + // late
				"       else 2 end as status" +
				" from submissions as s" +
				" where s.projectid = ?" +
				" and s.userid = ?" +
				" order by submissionnumber desc"
				);
		stmt.setLong(1, project.getOntime());
		stmt.setLong(2, project.getOntime());
		stmt.setLong(3, project.getLate());
		stmt.setInt(4, project.getId());
		stmt.setInt(5, student.getId());
		ResultSet resultSet = executeQuery(stmt);
		List<Pair<Submission, SubmissionStatus>> result = new ArrayList<>();
		SubmissionStatus[] statuses = SubmissionStatus.values();
		while (resultSet.next()) {
			Submission submission = new Submission();
			int index = Query.loadFields(submission, resultSet);
			//result.add(submission);
			SubmissionStatus subStatus = statuses[resultSet.getInt(index)];
			result.add(new Pair<>(submission, subStatus));
		}
		return result;
	}
}
