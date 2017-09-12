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

import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.model.SectionNumber;
import edu.ycp.cs.pygmymarmoset.app.model.Triple;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;
import edu.ycp.cs.pygmymarmoset.model.persist.Query;

public class GetStudentProjectActivity extends DatabaseRunnable<List<Triple<User, Integer[], SectionNumber>>> {
	private Project project;
	
	public GetStudentProjectActivity(Project project) {
		super("get student project activity");
		this.project = project;
	}

	@Override
	public List<Triple<User, Integer[], SectionNumber>> execute(Connection conn) throws SQLException {
		PreparedStatement stmt = prepareStatement(
				conn,
				"select u.*, y.num_ontime, y.num_late, y.num_verylate" +
				"  from (select uu.*, r.section from users as uu, roles as r where uu.id = r.userid and r.courseid = ?) as u" +
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
				""
				);
		stmt.setInt(1, project.getCourseId());
		stmt.setLong(2, project.getOntime());
		stmt.setLong(3, project.getOntime());
		stmt.setLong(4, project.getLate());
		stmt.setLong(5, project.getLate());
		stmt.setInt(6, project.getId());
		
		ResultSet resultSet = executeQuery(stmt);
		List<Triple<User, Integer[], SectionNumber>> result = new ArrayList<>();
		while (resultSet.next()) {
			// Load user fields
			User user = new User();
			int index = Query.loadFields(user, resultSet);
			// Section number appears just after the User fields
			SectionNumber secNum = new SectionNumber();
			secNum.setSection(resultSet.getInt(index++));
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
			result.add(new Triple<>(user, triple, secNum));
		}
		
		return result;
	}
}
