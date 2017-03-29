package edu.ycp.cs.pygmymarmoset.model.persist.txn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;
import edu.ycp.cs.pygmymarmoset.model.persist.Query;

public class CreateCourse extends DatabaseRunnable<Boolean> {
	private Course course;

	public CreateCourse(Course course) {
		super("create course");
		this.course = course;
	}

	@Override
	public Boolean execute(Connection conn) throws SQLException {
		String insert = Query.getInsertStatement(Course.class);
		//System.out.println("Insert statement: " + insert);
		PreparedStatement stmt = prepareStatement(conn, insert, PreparedStatement.RETURN_GENERATED_KEYS);
		Query.storeFieldsNoId(course, stmt);
		stmt.executeUpdate();
		ResultSet genKeys = getGeneratedKeys(stmt);
		if (genKeys.next()) {
			course.setId(genKeys.getInt(1));
		} else {
			throw new SQLException("Could not retrieve generated key for course");
		}
		return true;
	}

}
