// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.model;

public class UpdatedPassword {
	private String password;
	private String confirm;
	
	public UpdatedPassword() {
		
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	
	public String getConfirm() {
		return confirm;
	}
}
