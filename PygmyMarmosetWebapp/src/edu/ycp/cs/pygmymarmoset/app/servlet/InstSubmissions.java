package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Route(pattern="/i/submissions/*", view="/_view/instSubmissions.jsp")
@Navigation(parent=InstProject.class)
@CrumbSpec(text="Submissions for %S", items={PathInfoItem.COURSE_ID, PathInfoItem.PROJECT_ID, PathInfoItem.STUDENT_ID})
public class InstSubmissions extends AbstractServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		delegateToView(req, resp);
	}
}
