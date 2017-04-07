package edu.ycp.cs.pygmymarmoset.model.persist.txn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;
import edu.ycp.cs.pygmymarmoset.model.persist.Query;

public class GetProjectsInCourse extends DatabaseRunnable<java.util.List<Project>> {
	private Course course;

	public GetProjectsInCourse(Course course) {
		super("get projects in course");
		this.course = course;
	}
	
	@Override
	public List<Project> execute(Connection conn) throws SQLException {
		PreparedStatement stmt = prepareStatement(
				conn,
				"select p.* from projects as p" +
				" where p.courseid = ? " +
				" order by p.ontime desc, p.late desc, p.name asc");
		stmt.setInt(1, course.getId());
		ResultSet resultSet = executeQuery(stmt);
		List<Project> result = new ArrayList<>();
		while (resultSet.next()) {
			Project project = new Project();
			Query.loadFields(project, resultSet);
			//System.out.printf("Got project %s: %s (visible=%s)\n", project.getName(), project.getDescription(), project.isVisible());
			result.add(project);
		}
		return result;
	}
}
