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

import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.model.ProjectActivityField;
import edu.ycp.cs.pygmymarmoset.app.model.Role;
import edu.ycp.cs.pygmymarmoset.app.model.Triple;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;
import edu.ycp.cs.pygmymarmoset.model.persist.Query;

public class GetStudentProjectActivity extends DatabaseRunnable<List<Triple<User, Integer[], Role>>> {
	private Project project;
	private ProjectActivityField[] sortOrder;
	
	public GetStudentProjectActivity(Project project, ProjectActivityField[] sortOrder) {
		super("get student project activity");
		this.project = project;
		this.sortOrder = sortOrder;
	}
	
	private static Map<ProjectActivityField, String> SORT_MAP = new HashMap<>();
	static {
		SORT_MAP.put(ProjectActivityField.FIRST_NAME, "u.firstname asc");
		SORT_MAP.put(ProjectActivityField.LAST_NAME, "u.lastname asc");
		SORT_MAP.put(ProjectActivityField.ROLE_TYPE, "u.role_type desc");
		SORT_MAP.put(ProjectActivityField.SECTION, "u.role_section asc");
		SORT_MAP.put(ProjectActivityField.USERNAME, "u.username asc");
	}
	
	private String getOrderBy() {
		return Query.getOrderBy(sortOrder, SORT_MAP);
	}

	@Override
	public List<Triple<User, Integer[], Role>> execute(Connection conn) throws SQLException {
		String query =
				"select u.*, y.num_ontime, y.num_late, y.num_verylate" +
				"  from (select uu.*, " + Query.selectAlias(Role.class, "r", "role_") + " from users as uu, roles as r where uu.id = r.userid and r.courseid = ?) as u" +
				"       left join" +
				"       (select x.userid, sum(x.ontime) as num_ontime, sum(x.late) as num_late, sum(x.verylate) as num_verylate" +
				"          from (select s.userid," +
				"                       (case when s.timestamp <= ? then 1 else 0 end) as ontime," +
				"                       (case when s.timestamp > ? and s.timestamp <= ? then 1 else 0 end) as late," +
				"                       (case when s.timestamp > ? then 1  else 0 end) as verylate" +
				"                  from submissions as s" +
				"                 where s.projectid = ?) as x" +
				"           group by x.userid) as y" +
				"         on u.id = y.userid" +
				"   order by " + getOrderBy();
		System.out.println("Query: " + query);
		PreparedStatement stmt = prepareStatement(
				conn,
				query);
		stmt.setInt(1, project.getCourseId());
		stmt.setLong(2, project.getOntime());
		stmt.setLong(3, project.getOntime());
		stmt.setLong(4, project.getLate());
		stmt.setLong(5, project.getLate());
		stmt.setInt(6, project.getId());
		
		ResultSet resultSet = executeQuery(stmt);
		List<Triple<User, Integer[], Role>> result = new ArrayList<>();
		while (resultSet.next()) {
			// Load user fields
			User user = new User();
			int index = Query.loadFields(user, resultSet);
			// Load Role fields
			Role role = new Role();
			index = Query.loadFields(role, resultSet, index);
			// Load number of ontime/late/verylate submissions
			Integer[] triple;
			Number numOntime = (Number) resultSet.getObject(index);
			if (numOntime == null) {
				// There are no submissions yet
				triple = new Integer[]{0, 0, 0};
			} else {
				// Get number of ontime/late/verylate submissions
				triple = new Integer[3];
				for (int i = 0; i < 3; i++) {
					triple[i] = ((Number)resultSet.getObject(index+i)).intValue();
				}
			}
			result.add(new Triple<>(user, triple, role));
		}
		
		return result;
	}
}
