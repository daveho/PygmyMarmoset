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
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;

public class SuggestUsernames extends DatabaseRunnable<List<String>> {
	private String term;
	
	public SuggestUsernames(String term) {
		super("suggest usernames");
		this.term = term;
	}

	@Override
	public List<String> execute(Connection conn) throws SQLException {
		String q = "select u.username from users as u" +
				" where u.username like ?" +
				" order by u.username asc";
		PreparedStatement stmt = prepareStatement(
				conn,
				q);
		//System.out.println("Query is " +q);
		//System.out.println("term=" + term);
		stmt.setString(1, term + "%");
		ResultSet resultSet = executeQuery(stmt);
		List<String> result = new ArrayList<>();
		while (resultSet.next()) {
			result.add(resultSet.getString(1));
		}
		return result;
	}
}
