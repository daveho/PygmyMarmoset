// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.servlet;

import edu.ycp.cs.pygmymarmoset.app.model.GetSubmissionsMode;

@Route(pattern="/i/downloadOntime/*")
public class InstDownloadOntime extends AbstractDownloadSubmissionsServlet {
	private static final long serialVersionUID = 1L;
	
	public InstDownloadOntime() {
		// matches Marmoset's naming convention of "p<project name>.zip"
		super(GetSubmissionsMode.ONTIME_AND_LATE, "p", "");
	}
}
