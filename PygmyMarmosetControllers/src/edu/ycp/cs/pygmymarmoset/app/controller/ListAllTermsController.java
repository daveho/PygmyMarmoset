package edu.ycp.cs.pygmymarmoset.app.controller;

import java.util.List;

import edu.ycp.cs.pygmymarmoset.app.model.Term;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseProvider;
import edu.ycp.cs.pygmymarmoset.model.persist.IDatabase;

public class ListAllTermsController {
	public List<Term> getAllTerms() {
		IDatabase db = DatabaseProvider.getInstance();
		return db.getAllTerms();
	}
}
