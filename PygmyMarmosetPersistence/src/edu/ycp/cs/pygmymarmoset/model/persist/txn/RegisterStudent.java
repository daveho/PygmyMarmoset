package edu.ycp.cs.pygmymarmoset.model.persist.txn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.ycp.cs.pygmymarmoset.app.model.BCrypt;
import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Role;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;
import edu.ycp.cs.pygmymarmoset.model.persist.Query;

public class RegisterStudent extends DatabaseRunnable<Boolean> {
	private User student;
	private Course course;
	private Role role;

	public RegisterStudent(User student, Course course, Role role) {
		super("register student in course");
		this.student = student;
		this.course = course;
		this.role = role;
	}

	@Override
	public Boolean execute(Connection conn) throws SQLException {
		PreparedStatement findStudent = prepareStatement(conn, "select u.* from users as u where u.username = ?");
		findStudent.setString(1, student.getUsername());
		
		// Find existing user, or create new User
		ResultSet r1 = executeQuery(findStudent);
		if (r1.next()) {
			// User already exists
			Query.loadFields(student, r1);
		} else {
			// insert new User
			String salt = BCrypt.gensalt(12);
			String plaintext = student.getPasswordHash();
			student.setPasswordHash(BCrypt.hashpw(plaintext, salt));
			InsertModelObject<User> insertUser = new InsertModelObject<User>(student);
			insertUser.execute(conn);
		}
		
		// Insert student Role
		Role studentRole = role;
		role.setUserId(student.getId()); // user id should be known now
		InsertModelObject<Role> insertRole = new InsertModelObject<>(studentRole);
		insertRole.execute(conn);
		
		// Success!
		return true;
	}

}
