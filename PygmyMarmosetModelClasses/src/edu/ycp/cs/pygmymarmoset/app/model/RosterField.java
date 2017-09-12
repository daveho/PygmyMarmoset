// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.model;

/**
 * Roster fields (for sorting class roster).
 */
public enum RosterField {
	ROLE_TYPE,
	LAST_NAME,
	FIRST_NAME,
	SECTION,
	USERNAME;
	
	/**
	 * Find {@link RosterField} value for name, ignoring case.
	 * 
	 * @param name roster field name
	 * @return the {@link RosterField}
	 * @throws IllegalArgumentException if the name is not known
	 */
	public static RosterField find(String name) {
		return Sort.find(values(), name);
	}
	
	/**
	 * Get the default sort order (sequence of {@link RosterField} values).
	 * @return default sort order
	 */
	public static RosterField[] getDefaultSortOrder() {
		return values();
	}
	
	/**
	 * Get a sort order where the specified {@link RosterField} is the
	 * primary sorting criterion.
	 * 
	 * @param primary the primary sort criterion
	 * @return the resulting sort order
	 */
	public static RosterField[] sortBy(RosterField primary) {
		return Sort.sortBy(getDefaultSortOrder(), primary);
	}
}
