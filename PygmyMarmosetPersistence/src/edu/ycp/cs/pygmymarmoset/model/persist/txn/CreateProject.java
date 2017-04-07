package edu.ycp.cs.pygmymarmoset.model.persist.txn;

import java.sql.Connection;
import java.sql.SQLException;

import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.model.PygmyMarmosetException;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;

public class CreateProject extends DatabaseRunnable<Boolean> {
	private Course course;
	private Project project;
	
	public CreateProject(Course course, Project project) {
		super("create project");
		this.course = course;
		this.project = project;
	}

	@Override
	public Boolean execute(Connection conn) throws SQLException {
		throw new PygmyMarmosetException("TODO - implement");
	}
}
