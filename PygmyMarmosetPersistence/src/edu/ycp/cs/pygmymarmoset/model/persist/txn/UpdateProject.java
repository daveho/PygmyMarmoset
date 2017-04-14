// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.model.persist.txn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;
import edu.ycp.cs.pygmymarmoset.model.persist.Query;

public class UpdateProject extends DatabaseRunnable<Boolean> {
	private Project project;
	
	public UpdateProject(Project project) {
		super("update project");
		this.project = project;
	}

	@Override
	public Boolean execute(Connection conn) throws SQLException {
		String update = Query.getUpdateStatement(Project.class);
		//System.out.println("update: " + update);
		PreparedStatement stmt = prepareStatement(conn, update);
		Query.storeFieldsForUpdate(project, stmt);
		stmt.executeUpdate();
		return true;
	}
}
