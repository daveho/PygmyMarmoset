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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Pair;
import edu.ycp.cs.pygmymarmoset.app.model.Role;
import edu.ycp.cs.pygmymarmoset.app.model.RosterField;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;
import edu.ycp.cs.pygmymarmoset.model.persist.Query;

public class GetRoster extends DatabaseRunnable<List<Pair<User, Role>>> {
	private Course course;
	private RosterField[] sortOrder;

	public GetRoster(Course course, RosterField[] sortOrder) {
		super("get course roster");
		this.course = course;
		this.sortOrder = sortOrder;
	}
	
	private static final Map<RosterField, String> SORT_MAP = new HashMap<>();
	static {
		SORT_MAP.put(RosterField.ROLE_TYPE, "r.type desc");
		SORT_MAP.put(RosterField.LAST_NAME, "u.lastname asc");
		SORT_MAP.put(RosterField.FIRST_NAME, "u.firstname asc");
		SORT_MAP.put(RosterField.SECTION, "r.section asc");
		SORT_MAP.put(RosterField.USERNAME, "u.username asc");
	}
	
	private String getOrderBy() {
		return Query.getOrderBy(sortOrder, SORT_MAP);
	}
	
	@Override
	public List<Pair<User, Role>> execute(Connection conn) throws SQLException {
		PreparedStatement stmt = prepareStatement(
				conn,
				"select u.*, r.* from users as u, roles as r" +
				" where u.id = r.userid" +
				"   and r.courseid = ?" +
				" order by " + getOrderBy());
		stmt.setInt(1, course.getId());
		ResultSet resultSet = executeQuery(stmt);
		List<Pair<User, Role>> result = new ArrayList<>();
		while (resultSet.next()) {
			User user = new User();
			int index = Query.loadFields(user, resultSet);
			Role role = new Role();
			Query.loadFields(role, resultSet, index);
			result.add(new Pair<>(user, role));
		}
		return result;
	}
}
