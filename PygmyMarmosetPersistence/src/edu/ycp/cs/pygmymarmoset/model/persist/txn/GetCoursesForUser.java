package edu.ycp.cs.pygmymarmoset.model.persist.txn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.RoleType;
import edu.ycp.cs.pygmymarmoset.app.model.Term;
import edu.ycp.cs.pygmymarmoset.app.model.Triple;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;
import edu.ycp.cs.pygmymarmoset.model.persist.Query;

public class GetCoursesForUser extends DatabaseRunnable<List<Triple<Course, Term, RoleType>>> {
	private User user;
	
	public GetCoursesForUser(User user) {
		super("get courses for user");
		this.user = user;
	}
	
	@Override
	public List<Triple<Course, Term, RoleType>> execute(Connection conn) throws SQLException {
		// This query will yield the user's most privileged role type
		// if there are multiple roles for the same course.
		PreparedStatement stmt = prepareStatement(
				conn,
				"select c.*, t.*, max(r.type)" +
				" from courses as c, terms as t, roles as r" +
				" where c.id = r.courseid" +
				"  and r.userid = ?" +
				"  and t.id = c.termid" +
				" group by r.courseid" +
				" order by c.year desc, t.seq desc, c.name asc, c.title asc, c.id asc");
		stmt.setInt(1, user.getId());
		ResultSet resultSet = executeQuery(stmt);
		RoleType[] roleTypes = RoleType.values();
		List<Triple<Course, Term, RoleType>> result = new ArrayList<>();
		while (resultSet.next()) {
			Course course = new Course();
			int index = Query.loadFields(course, resultSet);
			Term term = new Term();
			index = Query.loadFields(term, resultSet, index);
			RoleType roleType = roleTypes[resultSet.getInt(index)];
			result.add(new Triple<>(course, term, roleType));
		}
		return result;
	}
}
