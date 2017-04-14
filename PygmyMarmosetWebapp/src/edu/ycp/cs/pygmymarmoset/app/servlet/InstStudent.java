package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.model.Pair;
import edu.ycp.cs.pygmymarmoset.app.model.Project;

@Route(pattern="/i/student/*", view="/_view/instStudent.jsp")
@Navigation(parent=InstCourse.class)
@CrumbSpec(text="Student %S", items={PathInfoItem.COURSE_ID, PathInfoItem.STUDENT_ID})
public class InstStudent extends AbstractServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Pair<Project, Integer[]>> studentActivity;
		
		// TODO
		
		delegateToView(req, resp);
	}
}
