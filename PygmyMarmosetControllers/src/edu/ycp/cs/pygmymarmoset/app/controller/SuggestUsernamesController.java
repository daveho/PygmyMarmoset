package edu.ycp.cs.pygmymarmoset.app.controller;

import java.util.List;

import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseProvider;
import edu.ycp.cs.pygmymarmoset.model.persist.IDatabase;

public class SuggestUsernamesController {
	public List<String> execute(String term) {
		IDatabase db = DatabaseProvider.getInstance();
		return db.suggestUsernames(term);
	}
}
