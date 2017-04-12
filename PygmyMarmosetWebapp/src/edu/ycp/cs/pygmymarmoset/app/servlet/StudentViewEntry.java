package edu.ycp.cs.pygmymarmoset.app.servlet;

@Route(pattern="/u/entry/*", view="/_view/studentViewEntry.jsp")
@Navigation(parent=StudentSubmission.class)
@CrumbSpec(
		text="Entry %e",
		items={PathInfoItem.COURSE_ID, PathInfoItem.STUDENT_ID, PathInfoItem.PROJECT_ID,
				PathInfoItem.SUBMISSION_ID, PathInfoItem.ENTRY_INDEX})
public class StudentViewEntry extends AbstractViewEntryServlet {
	private static final long serialVersionUID = 1L;
	
	public StudentViewEntry() {
		super(4);
	}
}
