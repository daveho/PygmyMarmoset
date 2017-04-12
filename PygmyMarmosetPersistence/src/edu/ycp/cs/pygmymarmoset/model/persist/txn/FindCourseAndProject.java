package edu.ycp.cs.pygmymarmoset.model.persist.txn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Pair;
import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;
import edu.ycp.cs.pygmymarmoset.model.persist.Query;

public class FindCourseAndProject extends DatabaseRunnable<Pair<Course, Project>> {
	private String courseName;
	private String termName;
	private Integer year;
	private String projectName;

	public FindCourseAndProject(String courseName, String termName, Integer year, String projectName) {
		super("find course and project");
		this.courseName = courseName;
		this.termName = termName;
		this.year = year;
		this.projectName = projectName;
	}

	@Override
	public Pair<Course, Project> execute(Connection conn) throws SQLException {
		PreparedStatement stmt = prepareStatement(
				conn,
				"select c.*, p.*" +
				" from courses as c, projects as p, terms as t" +
				" where c.id = p.courseid" +
				" and c.name = ?" +
				" and c.termid = t.id" +
				" and t.name = ?" +
				" and c.year = ?" +
				" and c.id = p.courseid" +
				" and p.name = ?");
		stmt.setString(1, courseName);
		stmt.setString(2, termName);
		stmt.setInt(3, year);
		stmt.setString(4, projectName);
		
		ResultSet resultSet = executeQuery(stmt);
		if (!resultSet.next()) {
			// did not find matching course and project
			return null;
		}
		
		Course course = new Course();
		int index = Query.loadFields(course, resultSet);
		Project project = new Project();
		Query.loadFields(project, resultSet, index);
		
		return new Pair<>(course, project);
	}
}
