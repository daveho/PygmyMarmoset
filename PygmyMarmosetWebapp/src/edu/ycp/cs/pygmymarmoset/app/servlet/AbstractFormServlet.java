package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.model.PygmyMarmosetException;

public abstract class AbstractFormServlet extends HttpServlet {
	public enum LogicOutcome {
		STAY_ON_PAGE,
		REDIRECT,
	}
	
	private static final long serialVersionUID = 1L;
	
	private String viewPath;

	public AbstractFormServlet(String viewPath) {
		this.viewPath = viewPath;
	}
	
	protected abstract Params createParams(HttpServletRequest req);
	protected abstract LogicOutcome doLogic(Params params, HttpServletRequest req, HttpServletResponse resp) throws IOException;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		createParams(req);
		
		// Allow logic?
		
		req.getRequestDispatcher(viewPath).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Params params = createParams(req);
		LogicOutcome outcome = LogicOutcome.STAY_ON_PAGE;
		try {
			params.unmarshal(); // read data from form into model object(s)
			outcome = doLogic(params, req, resp);
		} catch (PygmyMarmosetException e) {
			req.setAttribute("errmsg", e.getErrorMessage());
		}
		if (outcome == LogicOutcome.STAY_ON_PAGE) {
			req.getRequestDispatcher(viewPath).forward(req, resp);
		}
	}
}
