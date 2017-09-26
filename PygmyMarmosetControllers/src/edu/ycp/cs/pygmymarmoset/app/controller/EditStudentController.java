package edu.ycp.cs.pygmymarmoset.app.controller;

import edu.ycp.cs.pygmymarmoset.app.model.PygmyMarmosetException;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseProvider;

public class EditStudentController {
	public void execute(User student) {
		User orig = DatabaseProvider.getInstance().findUserForUsername(student.getUsername());
		if (orig == null) {
			throw new PygmyMarmosetException("Unknown user: " + student.getUsername());
		}
		if (student.getPasswordHash().equals("")) {
			// retain current password
			student.setPasswordHash(orig.getPasswordHash());
		}
		student.setId(orig.getId());
		
		DatabaseProvider.getInstance().updateModelObject(student);
	}
}
