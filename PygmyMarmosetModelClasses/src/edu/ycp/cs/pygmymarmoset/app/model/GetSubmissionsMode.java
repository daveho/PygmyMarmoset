// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.model;

/**
 * Mode for retrieving submissions for project.
 */
public enum GetSubmissionsMode {
	/** Retrieve all ontime and late submissions (excluding verylate). */
	ONTIME_AND_LATE,
	/** Retrieve all submissions. */
	ALL,
}
