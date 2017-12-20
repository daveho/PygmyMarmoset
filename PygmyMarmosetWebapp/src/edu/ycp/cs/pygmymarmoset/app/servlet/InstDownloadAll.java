// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.servlet;

import edu.ycp.cs.pygmymarmoset.app.model.GetSubmissionsMode;

/**
 * Bulk download of all submissions (ontime, late, verylate) for project.
 */
@Route(pattern="/i/downloadAll/*")
public class InstDownloadAll extends AbstractDownloadSubmissionsServlet {
	private static final long serialVersionUID = 1L;

	public InstDownloadAll() {
		// Not sure if "-all" suffix matches Marmoset's naming.
		super(GetSubmissionsMode.ALL, "", "-all");
	}
}
