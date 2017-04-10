package edu.ycp.cs.pygmymarmoset.model.persist.txn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.model.Submission;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;
import edu.ycp.cs.pygmymarmoset.model.persist.Query;

public class GetSubmissions extends DatabaseRunnable<List<Submission>> {
	private Project project;
	private User student;

	public GetSubmissions(Project project, User student) {
		super("get project submissions for student");
		
		this.project = project;
		this.student = student;
	}

	@Override
	public List<Submission> execute(Connection conn) throws SQLException {
		PreparedStatement stmt = prepareStatement(
				conn,
				"select s.* from submissions as s" +
				" where s.projectid = ?" +
				" and s.userid = ?" +
				" order by submissionnumber desc"
				);
		stmt.setInt(1, project.getId());
		stmt.setInt(2, student.getId());
		ResultSet resultSet = executeQuery(stmt);
		List<Submission> result = new ArrayList<>();
		while (resultSet.next()) {
			Submission submission = new Submission();
			Query.loadFields(submission, resultSet);
			result.add(submission);
		}
		return result;
	}

}
