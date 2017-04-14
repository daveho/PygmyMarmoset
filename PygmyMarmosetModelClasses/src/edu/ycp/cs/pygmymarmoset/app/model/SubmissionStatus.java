// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.model;

public enum SubmissionStatus {
	/** Submission received by the ontime deadline. */
	ONTIME,
	/** Submission received by the late deadline. */
	LATE,
	/** Submission received after the late deadline. */
	VERY_LATE,
}
