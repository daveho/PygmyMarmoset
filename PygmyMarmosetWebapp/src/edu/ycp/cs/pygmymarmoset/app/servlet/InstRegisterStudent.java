package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.model.User;

public class InstRegisterStudent extends AbstractFormServlet {
	private static final long serialVersionUID = 1L;
	
	public InstRegisterStudent() {
		super("/_view/instRegisterStudent.jsp");
	}

	@Override
	protected Params createParams(HttpServletRequest req) {
		Params params = new Params(req)
				.add("student", User.class);
		return params;
	}

	@Override
	protected LogicOutcome doLogic(Params params, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// TODO: implement
		return LogicOutcome.STAY_ON_PAGE;
	}

}
