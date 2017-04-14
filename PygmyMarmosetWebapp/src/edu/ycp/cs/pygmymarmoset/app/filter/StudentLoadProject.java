// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.filter;

import javax.servlet.Filter;

public class StudentLoadProject extends AbstractProjectFilter implements Filter {
	public StudentLoadProject() {
		super(2); // for student URLs, project id is the third argument
	}
}
