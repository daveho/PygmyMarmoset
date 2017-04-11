package edu.ycp.cs.pygmymarmoset.model.persist.txn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs.pygmymarmoset.app.model.Pair;
import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;
import edu.ycp.cs.pygmymarmoset.model.persist.Query;

public class GetStudentProjectActivity extends DatabaseRunnable<List<Pair<User, Integer>>> {
	private Project project;
	
	public GetStudentProjectActivity(Project project) {
		super("get student project activity");
		this.project = project;
	}

	@Override
	public List<Pair<User, Integer>> execute(Connection conn) throws SQLException {
		PreparedStatement stmt = prepareStatement(
				conn,
				"select u.*, x.*" +
				" from users as u" +
				" left join (select s.userid, count(*)" +
				"             from submissions as s" +
				"             where s.projectid = ?" +
				"             group by s.userid) as x" +
				" on u.id = x.userid" +
				" order by u.lastname asc, u.firstname asc, u.username asc"
				);
		stmt.setInt(1, project.getId());
		
		ResultSet resultSet = executeQuery(stmt);
		List<Pair<User, Integer>> result = new ArrayList<>();
		while (resultSet.next()) {
			User user = new User();
			int index = Query.loadFields(user, resultSet);
			Number numSubs = (Number) resultSet.getObject(index + 1); // count(*)
			if (numSubs == null) {
				numSubs = 0;
			}
			Pair<User, Integer> pair = new Pair<>(user, numSubs.intValue());
			result.add(pair);
		}
		
		return result;
	}
}
