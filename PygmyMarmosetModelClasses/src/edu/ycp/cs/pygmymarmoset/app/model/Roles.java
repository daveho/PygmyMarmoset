// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User to represent a {@link User}'s {@link Role}s in a
 * {@link Course}.  There can be more than one in the case
 * that an instructor is teaching multiple sections.
 * (Students should only ever be registered in a single
 * section.)  Note that a {@link Roles} object can be
 * "empty" if the user has no roles in the course.
 * Use the {@link #isEmpty()} method to check for this.
 */
public class Roles {
	private List<Role> roles;
	
	public Roles() {
		this.roles = new ArrayList<>();
	}
	
	public void addRole(Role role) {
		roles.add(role);
	}

	public void addAll(List<Role> roles) {
		this.roles.addAll(roles);
	}
	
	public List<Role> getRoles() {
		return Collections.unmodifiableList(roles);
	}

	public boolean isInstructor() {
		return roles.stream().anyMatch((r) -> r.getType() == RoleType.INSTRUCTOR);
	}
	
	public boolean isEmpty() {
		return roles.isEmpty();
	}
}
