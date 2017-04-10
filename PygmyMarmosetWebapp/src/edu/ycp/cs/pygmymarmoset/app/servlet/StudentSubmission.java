package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.controller.GetSubmissionIndexController;
import edu.ycp.cs.pygmymarmoset.app.model.IndexEntry;
import edu.ycp.cs.pygmymarmoset.app.model.Submission;

@Route(pattern="/u/submission/*", view="/_view/studentSubmission.jsp")
public class StudentSubmission extends AbstractServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Submission submission = (Submission) req.getAttribute("submission");
		
		GetSubmissionIndexController getIndex = new GetSubmissionIndexController();
		List<IndexEntry> entries = getIndex.execute(submission);
		
		req.setAttribute("entries", entries);
		
		delegateToView(req, resp);
	}
}
