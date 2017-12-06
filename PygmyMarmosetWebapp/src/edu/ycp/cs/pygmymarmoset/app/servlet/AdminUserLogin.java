// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.controller.FindUserController;
import edu.ycp.cs.pygmymarmoset.app.model.LoginCredentials;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.app.util.ServletUtil;

@Route(pattern="/a/userLogin", view="/_view/adminUserLogin.jsp")
@Navigation(parent=AdminIndex.class)
@CrumbSpec(text="Login as another user")
public class AdminUserLogin extends AbstractFormServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected Params createParams(HttpServletRequest req) {
		return new Params(req)
				.add("creds", LoginCredentials.class);
	}

	@Override
	protected LogicOutcome doLogic(Params params, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		LoginCredentials creds = params.get("creds");
		
		// Find the user the admin user wants to authenticate as
		FindUserController findUser = new FindUserController();
		User otherUser = findUser.execute(creds.getUsername());
		
		if (otherUser == null) {
			req.setAttribute("errmsg", "No such user: " + creds.getUsername());
			return LogicOutcome.STAY_ON_PAGE;
		} else {
			// Clear out the session
			ServletUtil.clearSession(req.getSession());
			// Update the user recorded in the session
			req.getSession().setAttribute("user", otherUser);
			// Redirect to index
			resp.sendRedirect(req.getContextPath() + "/index");
			return LogicOutcome.REDIRECT;
		}
	}
}
