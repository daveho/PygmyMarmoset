package edu.ycp.cs.pygmymarmoset.model.persist.txn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs.pygmymarmoset.app.model.Term;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;
import edu.ycp.cs.pygmymarmoset.model.persist.Query;

public class GetAllTerms extends DatabaseRunnable<List<Term>> {
	public GetAllTerms() {
		super("get all terms");
	}
	
	@Override
	public List<Term> execute(Connection conn) throws SQLException {
		PreparedStatement stmt = prepareStatement(conn, "select t.* from terms as t");
		ResultSet resultSet = executeQuery(stmt);
		List<Term> terms = new ArrayList<>();
		while (resultSet.next()) {
			Term term = new Term();
			Query.loadFields(term, resultSet);
			terms.add(term);
		}
		return terms;
	}
}
