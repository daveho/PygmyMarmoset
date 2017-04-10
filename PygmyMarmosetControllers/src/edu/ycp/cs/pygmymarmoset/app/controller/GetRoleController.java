package edu.ycp.cs.pygmymarmoset.app.controller;

import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Role;
import edu.ycp.cs.pygmymarmoset.app.model.RoleType;
import edu.ycp.cs.pygmymarmoset.app.model.Roles;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseProvider;
import edu.ycp.cs.pygmymarmoset.model.persist.IDatabase;

public class GetRoleController {
	/**
	 * Get {@link Roles} for specified {@link User} in specified {@link Course}.
	 * Note that if the user is a superuser, an instructor role in
	 * the course will be created automatically, even though it does not
	 * actually exist in the database.  The avoids the need for special
	 * cases to check superuser privilege when checking for instructor
	 * privilege.
	 * 
	 * @param user the {@link User}
	 * @param course the {@link Course}
	 * @return the {@link Roles} for the user in the course
	 */
	public Roles execute(User user, Course course) {
		IDatabase db = DatabaseProvider.getInstance();
		Roles roles = db.getUserRolesInCourse(user, course);
		if (!roles.isInstructor() && user.isSuperUser()) {
			Role instructorRole = new Role();
			instructorRole.setId(0); // not a real Role
			instructorRole.setUserId(user.getId());
			instructorRole.setCourseId(course.getId());
			instructorRole.setType(RoleType.INSTRUCTOR);
			instructorRole.setSection(0); // not a real Role
			roles.addRole(instructorRole);
		}
		return roles;
	}
}
