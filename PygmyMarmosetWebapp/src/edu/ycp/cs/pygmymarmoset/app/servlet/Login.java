// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.controller.LoginController;
import edu.ycp.cs.pygmymarmoset.app.model.ErrorMessage;
import edu.ycp.cs.pygmymarmoset.app.model.LoginCredentials;
import edu.ycp.cs.pygmymarmoset.app.model.User;

@Route(pattern="/login", view="/_view/login.jsp")
public class Login extends AbstractFormServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected Params createParams(HttpServletRequest req) {
		return new Params(req)
				.add("creds", LoginCredentials.class);
	}
	
	@Override
	protected LogicOutcome doLogic(Params params, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		LoginCredentials creds = params.get("creds");
		LoginController controller = new LoginController();
		User user = controller.execute(creds);
		if (user != null) {
			// Successful login.  Put user in session and redirect to
			// goal URL (if there is one) or the index page.
			req.getSession().setAttribute("user", user);
			String target;
			if (creds.hasGoal()) {
				target = creds.getGoal();
			} else {
				target = req.getContextPath() + "/index";
			}
			resp.sendRedirect(target);

			return LogicOutcome.REDIRECT;
		}

		// Unsuccessful login, display error message
		ErrorMessage errmsg = new ErrorMessage();
		errmsg.setText("Unknown username/password, please try again");
		req.setAttribute("errmsg", errmsg);
		creds.setPassword(""); // assume password was entered incorrectly
		req.setAttribute("creds", creds);
		return LogicOutcome.STAY_ON_PAGE;
	}
}
