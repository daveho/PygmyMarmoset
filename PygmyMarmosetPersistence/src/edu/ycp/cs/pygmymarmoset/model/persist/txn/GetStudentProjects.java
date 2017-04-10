package edu.ycp.cs.pygmymarmoset.model.persist.txn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Pair;
import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;
import edu.ycp.cs.pygmymarmoset.model.persist.Query;

public class GetStudentProjects extends DatabaseRunnable<List<Pair<Project, Integer>>> {
	private Course course;
	private User user;

	public GetStudentProjects(Course course, User user) {
		super("get projects for student");
		this.course = course;
		this.user = user;
	}

	@Override
	public List<Pair<Project, Integer>> execute(Connection conn) throws SQLException {
		PreparedStatement stmt = prepareStatement(
				conn,
				"select p.*, max(s.submissionnumber)" +
				" from projects as p left join submissions as s" +
				"   on p.id = s.userid" +
				" where p.courseid = ?" +
				"  and s.userid is null or s.userid = ?" +
				"  and p.visible = 1" +
				" group by p.id" +
				" order by p.ontime desc, p.late desc, p.name asc, p.id asc");
		stmt.setInt(1, course.getId());
		stmt.setInt(2, user.getId());
		ResultSet resultSet = executeQuery(stmt);
		List<Pair<Project, Integer>> result = new ArrayList<>();
		while (resultSet.next()) {
			Project project = new Project();
			int index = Query.loadFields(project, resultSet);
			if (!project.isVisible()) {
				// Due to the left outer join, invisible projects
				// can be returned, so don't include them.
				continue;
			}
			Integer maxSub = (Integer) resultSet.getObject(index);
			if (maxSub == null) {
				maxSub = 0;
			}
			result.add(new Pair<>(project, maxSub));
		}
		return result;
	}

}
