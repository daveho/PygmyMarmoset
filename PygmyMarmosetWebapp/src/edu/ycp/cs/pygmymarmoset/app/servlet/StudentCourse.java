package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Route(pattern="/u/course/*", view="/_view/studentCourse.jsp")
@Navigation(parent=Index.class)
@CrumbSpec(text="%c, %t", items={PathInfoItem.COURSE_ID})
public class StudentCourse extends AbstractServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		delegateToView(req, resp);
	}
}
