// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.model;

/**
 * Project activity fields (for sorting).
 */
public enum ProjectActivityField {
	ROLE_TYPE,
	LAST_NAME,
	FIRST_NAME,
	SECTION,
	USERNAME;
	
	public static ProjectActivityField find(String name) {
		return Sort.find(values(), name);
	}
	
	public static ProjectActivityField[] getDefaultSortOrder() {
		return values();
	}
	
	public static ProjectActivityField[] sortBy(ProjectActivityField primary) {
		return Sort.sortBy(getDefaultSortOrder(), primary);
	}
}
