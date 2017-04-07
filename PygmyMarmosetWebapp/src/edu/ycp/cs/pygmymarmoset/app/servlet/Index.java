package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Route(pattern="/u/index", view="/_view/index.jsp")
@CrumbSpec(text="Home")
public class Index extends AbstractServlet {
	private static final long serialVersionUID = 1L;

	/*
	static {
		AbstractServlet.registerCrumbFactory(Index.class, AbstractServlet.staticCrumbFactory(Index.class, "Home"));
	}
	*/

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		delegateToView(req, resp);
	}
}
