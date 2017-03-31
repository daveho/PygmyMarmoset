package edu.ycp.cs.pygmymarmoset.model.persist.txn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;
import edu.ycp.cs.pygmymarmoset.model.persist.Query;

public class GetAllCourses extends DatabaseRunnable<List<Course>> {
	public GetAllCourses() {
		super("get all courses");
	}

	@Override
	public List<Course> execute(Connection conn) throws SQLException {
		PreparedStatement stmt = prepareStatement(conn, "select c.* from courses as c");
		ResultSet resultSet = executeQuery(stmt);
		List<Course> courses = new ArrayList<>();
		while (resultSet.next()) {
			Course course = new Course();
			Query.loadFields(course, resultSet);
			courses.add(course);
		}
		
		// TODO: sort in reverse chronological order by term/year
		
		return courses;
	}

}
