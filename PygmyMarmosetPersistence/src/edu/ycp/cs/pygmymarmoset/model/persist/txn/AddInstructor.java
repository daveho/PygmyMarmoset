package edu.ycp.cs.pygmymarmoset.model.persist.txn;

import java.sql.Connection;
import java.sql.SQLException;

import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.PersistenceException;
import edu.ycp.cs.pygmymarmoset.app.model.Role;
import edu.ycp.cs.pygmymarmoset.app.model.RoleType;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;

public class AddInstructor extends DatabaseRunnable<Boolean> {
	private Course course;
	private String username;
	private int section;

	public AddInstructor(Course course, String username, int section) {
		super("add instructor");
		this.course = course;
		this.username = username;
		this.section = section;
	}

	@Override
	public Boolean execute(Connection conn) throws SQLException {
		FindUserForUsername findUser = new FindUserForUsername(username);
		User user = findUser.execute(conn);
		if (user == null) {
			throw new PersistenceException("No such user: " + username);
		}
		
		Role role = new Role();
		role.setUserId(user.getId());
		role.setCourseId(course.getId());
		role.setType(RoleType.INSTRUCTOR);
		role.setSection(section);
		InsertModelObject<Role> insertRole = new InsertModelObject<>(role);
		return insertRole.execute(conn);
	}

}
