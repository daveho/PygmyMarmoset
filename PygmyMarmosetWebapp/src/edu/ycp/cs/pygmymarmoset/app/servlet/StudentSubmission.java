package edu.ycp.cs.pygmymarmoset.app.servlet;

@Route(pattern="/u/submission/*", view="/_view/studentSubmission.jsp")
@Navigation(parent=StudentProject.class)
@CrumbSpec(
		text="Submission %s",
		items={PathInfoItem.COURSE_ID, PathInfoItem.STUDENT_ID, PathInfoItem.PROJECT_ID, PathInfoItem.SUBMISSION_ID})
public class StudentSubmission extends AbstractSubmissionServlet {
	private static final long serialVersionUID = 1L;

	// logic is implemented in superclass
}
