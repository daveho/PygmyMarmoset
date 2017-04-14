// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.model.persist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class DatabaseRunnable<E> {
	private String name;
	private List<Object> cleanupStack;
	
	public DatabaseRunnable(String name) {
		this.name = name;
		this.cleanupStack = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	
	public abstract E execute(Connection conn) throws SQLException;
	
	public PreparedStatement prepareStatement(Connection conn, String sql) throws SQLException {
		return prepareStatement(conn, sql, 0);
	}

	public PreparedStatement prepareStatement(Connection conn, String sql, int flags) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(sql, flags);
		cleanupStack.add(stmt);
		return stmt;
	}

	public ResultSet executeQuery(PreparedStatement stmt) throws SQLException {
		ResultSet resultSet = stmt.executeQuery();
		cleanupStack.add(resultSet);
		return resultSet;
	}
	
	public ResultSet getGeneratedKeys(PreparedStatement stmt) throws SQLException {
		ResultSet resultSet = stmt.getGeneratedKeys();
		cleanupStack.add(resultSet);
		return resultSet;
	}
	
	public void cleanup() {
		while (!cleanupStack.isEmpty()) {
			int last = cleanupStack.size() - 1;
			Object obj = cleanupStack.remove(last);
			if (obj instanceof PreparedStatement) {
				DBUtil.closeQuietly((PreparedStatement) obj);
			} else if (obj instanceof ResultSet) {
				DBUtil.closeQuietly((ResultSet) obj);
			}
		}
	}
}
