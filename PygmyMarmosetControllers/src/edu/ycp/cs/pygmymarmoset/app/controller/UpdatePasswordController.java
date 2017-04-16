// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.ycp.cs.pygmymarmoset.app.model.BCrypt;
import edu.ycp.cs.pygmymarmoset.app.model.UpdatedPassword;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.app.model.ValidationException;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseProvider;
import edu.ycp.cs.pygmymarmoset.model.persist.IDatabase;

public class UpdatePasswordController {
	public boolean execute(User user, UpdatedPassword updatedPasswd) {
		if (isMissing(updatedPasswd.getPassword()) || isMissing(updatedPasswd.getConfirm())) {
			throw new ValidationException("Please enter updated password and confirmation");
		} else if (!updatedPasswd.getPassword().equals(updatedPasswd.getConfirm())) {
			throw new ValidationException("Password and confirmation do not match");
		} else if (hasSpaces(updatedPasswd.getPassword())) {
			throw new ValidationException("Password must not contain spaces");
		}
		
		String salt = BCrypt.gensalt(12);
		String updatedHash = BCrypt.hashpw(updatedPasswd.getPassword(), salt);
		
		IDatabase db = DatabaseProvider.getInstance();
		return db.updateUserPasswordHash(user, updatedHash);
	}

	public static boolean isMissing(String s) {
		return s == null || s.trim().equals("");
	}
	
	private static final Pattern ANY_SPACES = Pattern.compile("\\s");
	
	public static boolean hasSpaces(String s) {
		Matcher m = ANY_SPACES.matcher(s);
		return m.find();
	}
}
