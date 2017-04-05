package edu.ycp.cs.pygmymarmoset.model.persist.txn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Pair;
import edu.ycp.cs.pygmymarmoset.app.model.Role;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;
import edu.ycp.cs.pygmymarmoset.model.persist.Query;

public class GetRoster extends DatabaseRunnable<List<Pair<User, Role>>> {
	private Course course;

	public GetRoster(Course course) {
		super("get course roster");
		this.course = course;
	}
	
	@Override
	public List<Pair<User, Role>> execute(Connection conn) throws SQLException {
		PreparedStatement stmt = prepareStatement(
				conn,
				"select u.*, r.* from users as u, roles as r" +
				" where u.id = r.userid" +
				"   and r.courseid = ?" +
				" order by r.type desc, u.lastname asc, u.firstname asc, u.username asc");
		stmt.setInt(1, course.getId());
		ResultSet resultSet = executeQuery(stmt);
		List<Pair<User, Role>> result = new ArrayList<>();
		while (resultSet.next()) {
			User user = new User();
			int index = Query.loadFields(user, resultSet);
			Role role = new Role();
			Query.loadFields(role, resultSet, index);
			result.add(new Pair<>(user, role));
		}
		return result;
	}
}
