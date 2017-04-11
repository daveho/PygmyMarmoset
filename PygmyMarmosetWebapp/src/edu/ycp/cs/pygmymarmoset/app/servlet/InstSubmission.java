package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.controller.GetSubmissionIndexController;
import edu.ycp.cs.pygmymarmoset.app.model.Submission;
import edu.ycp.cs.pygmymarmoset.app.model.SubmissionEntry;

@Route(pattern="/i/submission/*", view="/_view/instSubmission.jsp")
@Navigation(parent=InstSubmissions.class)
@CrumbSpec(
		text="Submission %s",
		items={PathInfoItem.COURSE_ID, PathInfoItem.PROJECT_ID, PathInfoItem.STUDENT_ID, PathInfoItem.SUBMISSION_ID})
public class InstSubmission extends AbstractServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Submission submission = (Submission) req.getAttribute("submission");
		
		GetSubmissionIndexController getIndex = new GetSubmissionIndexController();
		List<SubmissionEntry> entries = getIndex.execute(submission);
		
		req.setAttribute("entries", entries);
		System.out.printf("Found %d entries in submission\n", entries.size());
		
		System.out.println("Rendering view...");
		delegateToView(req, resp);
		System.out.println("Done rendering view");
	}
}
