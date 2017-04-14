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

import org.apache.commons.beanutils.PropertyUtils;

import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;
import edu.ycp.cs.pygmymarmoset.model.persist.Query;

public class InsertModelObject<E> extends DatabaseRunnable<Boolean> {
	private E modelObj;

	public InsertModelObject(E modelObj) {
		super("create new " + modelObj.getClass().getSimpleName());
		this.modelObj = modelObj;
	}
	
	@Override
	public Boolean execute(Connection conn) throws SQLException {
		String insert = Query.getInsertStatement(modelObj.getClass());
		PreparedStatement stmt = prepareStatement(conn, insert, PreparedStatement.RETURN_GENERATED_KEYS);
		Query.storeFieldsNoId(modelObj, stmt);
		stmt.executeUpdate();
		ResultSet resultSet = getGeneratedKeys(stmt);
		if (!resultSet.next()) {
			throw new SQLException("Couldn't get generated keys for inserted " + modelObj.getClass().getSimpleName());
		}
		try {
			PropertyUtils.setProperty(modelObj, "id", resultSet.getInt(1));
		} catch (Exception e) {
			throw new SQLException("Couldn't set id of " + modelObj.getClass().getSimpleName(), e);
		}
		// Success!
		return true;
	}
}
