// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.model.persist.txn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;
import edu.ycp.cs.pygmymarmoset.model.persist.Query;

public class CreateModelClassTable extends DatabaseRunnable<Boolean> {
	private Class<?> modelCls;
	
	public CreateModelClassTable(Class<?> modelCls) {
		super("create model class " + modelCls.getSimpleName());
		this.modelCls = modelCls;
	}

	@Override
	public Boolean execute(Connection conn) throws SQLException {
		PreparedStatement stmt = prepareStatement(conn, Query.getCreateTableStatement(modelCls));
		stmt.executeUpdate();
		return true;
	}
}
