package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.controller.GetSubmissionIndexController;
import edu.ycp.cs.pygmymarmoset.app.model.Submission;
import edu.ycp.cs.pygmymarmoset.app.model.SubmissionEntry;

public abstract class AbstractSubmissionServlet extends AbstractServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Submission submission = (Submission) req.getAttribute("submission");
		
		GetSubmissionIndexController getIndex = new GetSubmissionIndexController();
		List<SubmissionEntry> entries = getIndex.execute(submission);
		
		if (entries.isEmpty()) {
			// Either this was a zipfile with no entries (unlikely),
			// or it is a single file that isn't a zipfile (very likely).
			// Make it available as a single "entry".  Index 0 is
			// used to mean the entire contents of the submission blob.
			SubmissionEntry entry = new SubmissionEntry();
			entry.setIndex(0);
			entry.setName(submission.getFileName());
			entry.setSize(submission.getSize());
			entries.add(entry);
		}
		
		req.setAttribute("entries", entries);
		//System.out.printf("Found %d entries in submission\n", entries.size());
		
		delegateToView(req, resp);
	}
}
