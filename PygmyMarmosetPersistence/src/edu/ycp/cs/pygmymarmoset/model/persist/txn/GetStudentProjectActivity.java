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

public class GetStudentProjectActivity extends DatabaseRunnable<List<Pair<User, Integer[]>>> {
	private Project project;
	
	public GetStudentProjectActivity(Project project) {
		super("get student project activity");
		this.project = project;
	}

	@Override
	public List<Pair<User, Integer[]>> execute(Connection conn) throws SQLException {
		PreparedStatement stmt = prepareStatement(
				conn,
				"select u.*, y.num_ontime, y.num_late, y.num_verylate" +
				"  from users as u" +
				"       left join" +
				"       (select x.userid, sum(x.ontime) as num_ontime, sum(x.late) as num_late, sum(x.verylate) as num_verylate" +
				"          from (select s.userid," +
				"                       (case when s.timestamp <= ? then 1 else 0 end) as ontime," +
				"                       (case when s.timestamp > ? and s.timestamp <= ? then 1 else 0 end) as late," +
				"                       (case when s.timestamp > ? then 1  else 0 end) as verylate" +
				"                  from submissions as s" +
				"                 where s.projectid = ?) as x" +
				"           group by x.userid) as y" +
				"         on u.id = y.userid"
				);
		stmt.setLong(1, project.getOntime());
		stmt.setLong(2, project.getOntime());
		stmt.setLong(3, project.getLate());
		stmt.setLong(4, project.getLate());
		stmt.setInt(5, project.getId());
		
		ResultSet resultSet = executeQuery(stmt);
		List<Pair<User, Integer[]>> result = new ArrayList<>();
		while (resultSet.next()) {
			User user = new User();
			int index = Query.loadFields(user, resultSet);
			Integer[] triple;
			Number numOntime = (Number) resultSet.getObject(index);
			if (numOntime == null) {
				// There are no submissions yet
				triple = new Integer[]{0, 0, 0};
			} else {
				// Get number of ontime/late/verylate submissions
				triple = new Integer[3];
				for (int i = 0; i < 3; i++) {
					triple[i] = ((Number)resultSet.getObject(index+i)).intValue();
				}
			}
			result.add(new Pair<>(user, triple));
		}
		
		return result;
	}
}
