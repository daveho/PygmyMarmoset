package edu.ycp.cs.pygmymarmoset.app.servlet;

@Route(pattern="/i/submissions/*", view="/_view/instSubmissions.jsp")
@Navigation(parent=InstProject.class)
@CrumbSpec(text="Submissions for %S", items={PathInfoItem.COURSE_ID, PathInfoItem.PROJECT_ID, PathInfoItem.STUDENT_ID})
public class InstSubmissions extends AbstractSubmissionsServlet {
	private static final long serialVersionUID = 1L;
	
	// logic handled by superclass
}
