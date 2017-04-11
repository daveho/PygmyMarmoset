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
	private User student;

	public GetStudentProjects(Course course, User student) {
		super("get projects for student");
		this.course = course;
		this.student = student;
	}

	@Override
	public List<Pair<Project, Integer>> execute(Connection conn) throws SQLException {
		// This query is done in a somewhat strange way (left join on
		// a subquery) because I was experiencing strange results
		// with a more straightforward approach.
		PreparedStatement stmt = prepareStatement(
				conn,
				"select p.*, x.*" +
				"  from projects as p" +
				" left join (select s.projectid, count(*)" +
				"             from submissions as s" +
				"            where s.userid = ?" +
				"            group by s.projectid) as x" +
				"   on p.id = x.projectid" +
				" where p.courseid = ? and p.visible = 1" +
				" order by p.ontime desc, p.late desc, p.name asc, p.id asc"
				);
		stmt.setInt(1, student.getId());
		stmt.setInt(2, course.getId());
		ResultSet resultSet = executeQuery(stmt);
		List<Pair<Project, Integer>> result = new ArrayList<>();
		while (resultSet.next()) {
			Project project = new Project();
			int index = Query.loadFields(project, resultSet);
			Number maxSub = (Number) resultSet.getObject(index+1); // count(*)
			if (maxSub == null) {
				maxSub = 0;
			}
			result.add(new Pair<>(project, maxSub.intValue()));
		}
		return result;
	}

}
