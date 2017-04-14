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

import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Pair;
import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;
import edu.ycp.cs.pygmymarmoset.model.persist.Query;

public class GetSingleStudentProjectActivity extends DatabaseRunnable<List<Pair<Project, Integer[]>>> {
	private Course course;
	private User student;

	public GetSingleStudentProjectActivity(Course course, User student) {
		super("get single student project activity");
		this.course = course;
		this.student = student;
	}

	@Override
	public List<Pair<Project, Integer[]>> execute(Connection conn) throws SQLException {
		PreparedStatement stmt = prepareStatement(
				conn,
				"select p.*, sum(y.ontime), sum(y.late), sum(y.verylate)" +
				" from (select s.projectid," +
				"              (case when s.timestamp <= pp.ontime then 1 else 0 end) as ontime," +
				"              (case when s.timestamp > pp.ontime and s.timestamp <= pp.late then 1 else 0 end) as late," +
				"              (case when s.timestamp > pp.late then 1 else 0 end) as verylate" +
				"        from (select * from projects where courseid = ?) as pp," +
				"             submissions as s" +
				"        where s.projectid = pp.id" +
				"          and s.userid = ?) as y," +
				"      projects as p" +
				"  where y.projectid = p.id" +
				" group by y.projectid" +
				" order by p.ontime desc, p.late desc, p.name asc"
				);
		stmt.setInt(1, course.getId());
		stmt.setInt(2, student.getId());
		
		ResultSet resultSet = executeQuery(stmt);
		List<Pair<Project, Integer[]>> result = new ArrayList<>();
		while (resultSet.next()) {
			Project project = new Project();
			int index = Query.loadFields(project, resultSet);
			Integer[] triple = new Integer[3];
			for (int i = 0; i < 3; i++) {
				triple[i] = resultSet.getInt(index + i);
			}
			result.add(new Pair<>(project, triple));
		}
		
		return result;
	}
}
