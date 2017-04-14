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

import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Pair;
import edu.ycp.cs.pygmymarmoset.app.model.Term;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;
import edu.ycp.cs.pygmymarmoset.model.persist.Query;

public class FindCourseForCourseId extends DatabaseRunnable<Pair<Course,Term>> {
	private int courseId;
	
	public FindCourseForCourseId(int courseId) {
		super("find course for course id");
		this.courseId = courseId;
	}

	@Override
	public Pair<Course, Term> execute(Connection conn) throws SQLException {
		PreparedStatement stmt = prepareStatement(
				conn,
				"select c.*, t.* from courses as c, terms as t" +
				" where c.id = ?" +
				" and c.termid = t.id");
		stmt.setInt(1, courseId);
		ResultSet resultSet = executeQuery(stmt);
		if (!resultSet.next()) {
			return null;
		}
		Course course = new Course();
		Term term = new Term();
		int index = Query.loadFields(course, resultSet);
		Query.loadFields(term, resultSet, index);
		return new Pair<>(course, term);
	}

}
