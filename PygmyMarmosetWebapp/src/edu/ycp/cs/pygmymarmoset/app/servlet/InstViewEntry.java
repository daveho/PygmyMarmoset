package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.util.ServletUtil;

@Route(pattern="/i/entry/*", view="/_view/instViewEntry.jsp")
@Navigation(parent=InstSubmission.class)
@CrumbSpec(
		text="Entry %e",
		items={PathInfoItem.COURSE_ID, PathInfoItem.PROJECT_ID, PathInfoItem.STUDENT_ID,
				PathInfoItem.SUBMISSION_ID, PathInfoItem.ENTRY_INDEX})
public class InstViewEntry extends AbstractServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Fifth argument is the entry index
		List<Integer> args = ServletUtil.getRequestArgs(req);
		if (args.size() < 5) {
			ServletUtil.sendBadRequest(req, resp, "Missing entry index");
			return;
		}
		
		Integer entryIndex = args.get(4);
		req.setAttribute("entryIndex", entryIndex);
		
		delegateToView(req, resp);
	}
}
