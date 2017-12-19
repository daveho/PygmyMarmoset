// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.controller;

import java.util.zip.ZipOutputStream;

import edu.ycp.cs.pygmymarmoset.app.model.ISubmissionCollector;
import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseProvider;
import edu.ycp.cs.pygmymarmoset.model.persist.IDatabase;

public class GetOntimeAndLateSubmissionsController {
	public void execute(Project project, ZipOutputStream zout) {
		ISubmissionCollector collector = new SubmissionArchiveCollector(zout);
		IDatabase db = DatabaseProvider.getInstance();
		db.getOntimeAndLateSubmissions(project, collector);
	}
}
