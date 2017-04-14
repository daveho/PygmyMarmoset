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
import edu.ycp.cs.pygmymarmoset.app.model.Term;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;
import edu.ycp.cs.pygmymarmoset.model.persist.Query;

public class GetAllCourses extends DatabaseRunnable<List<Pair<Course,Term>>> {
	public GetAllCourses() {
		super("get all courses");
	}

	@Override
	public List<Pair<Course,Term>> execute(Connection conn) throws SQLException {
		// Courses are returned in reverse chronological order
		PreparedStatement stmt = prepareStatement(
				conn,
				"select c.*, t.* from courses as c, terms as t" +
				" where c.termid = t.id" +
				" order by c.year desc," +
				"  t.seq desc," +
				"  c.name asc"
				);
		ResultSet resultSet = executeQuery(stmt);
		List<Pair<Course,Term>> courses = new ArrayList<>();
		while (resultSet.next()) {
			Course course = new Course();
			int index = Query.loadFields(course, resultSet);
			Term term = new Term();
			Query.loadFields(term, resultSet, index);
			courses.add(new Pair<>(course, term));
		}
		
		return courses;
	}

}
