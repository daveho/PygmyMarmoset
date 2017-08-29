// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.model;

/**
 * Object representing the per-student result of a bulk registration.
 * The main issues are distinguishing students for whom new accounts
 * were created from existing students, and recording generated
 * passwords for new student accounts. 
 */
public class BulkRegistrationOutcome {
	private boolean newUser;
	private String username;
	private String generatedPassword;
	
	public BulkRegistrationOutcome() {
		
	}
	
	public void setNewUser(boolean newUser) {
		this.newUser = newUser;
	}
	
	public boolean isNewUser() {
		return newUser;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setGeneratedPassword(String generatedPassword) {
		this.generatedPassword = generatedPassword;
	}
	
	public String getGeneratedPassword() {
		return generatedPassword;
	}
}
