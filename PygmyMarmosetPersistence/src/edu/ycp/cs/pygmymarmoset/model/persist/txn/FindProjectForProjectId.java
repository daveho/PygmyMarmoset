// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.model.persist.txn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;
import edu.ycp.cs.pygmymarmoset.model.persist.Query;

public class FindProjectForProjectId extends DatabaseRunnable<Project> {
	private int projectId;

	public FindProjectForProjectId(int projectId) {
		super("find project for project id");
		this.projectId = projectId;
	}

	@Override
	public Project execute(Connection conn) throws SQLException {
		PreparedStatement stmt = prepareStatement(conn, "select p.* from projects as p where p.id = ?");
		stmt.setInt(1, projectId);
		ResultSet resultSet = executeQuery(stmt);
		if (!resultSet.next()) {
			return null;
		}
		Project project = new Project();
		Query.loadFields(project, resultSet);
		return project;
	}

}
