package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Route(pattern="/a/index", view="/_view/adminIndex.jsp")
@Navigation(parent=Index.class)
@CrumbSpec(text="Admin")
public class AdminIndex extends AbstractServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		delegateToView(req, resp);
	}
}
