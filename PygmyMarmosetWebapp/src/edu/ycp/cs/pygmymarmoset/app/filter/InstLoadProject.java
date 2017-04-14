// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.filter;

import javax.servlet.Filter;

import edu.ycp.cs.pygmymarmoset.app.model.Project;

/**
 * Load {@link Project} for instructor servlets which
 * access a specific project.  Assumes that LoadCourse
 * has already loaded the {@link Course}.
 */
public class InstLoadProject extends AbstractProjectFilter implements Filter {
	public InstLoadProject() {
		super(1); // for instructor URLs, project is second argument
	}
}
