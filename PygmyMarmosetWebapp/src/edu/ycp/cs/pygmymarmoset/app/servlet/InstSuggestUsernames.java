package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.controller.SuggestUsernamesController;

public class InstSuggestUsernames extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static class Suggestion {
		@SuppressWarnings("unused")
		public String label;
		@SuppressWarnings("unused")
		public String value;
		public Suggestion(String s) {
			label = s;
			value = s;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String term = req.getParameter("term");
		//System.out.println("term="+term);
		
		SuggestUsernamesController suggestUsernames = new SuggestUsernamesController();
		List<String> suggestions = suggestUsernames.execute(term);
		
		Suggestion[] result = new Suggestion[suggestions.size()];
		for (int i = 0; i < suggestions.size(); i++) {
			result[i] = new Suggestion(suggestions.get(i));
		}
		//System.out.println("Returning " + result.length + " suggestions");
		
		resp.setContentType("application/json");
		JSON.getObjectMapper().writeValue(resp.getWriter(), result);
	}
}
