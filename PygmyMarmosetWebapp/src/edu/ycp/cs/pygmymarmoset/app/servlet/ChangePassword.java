// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.controller.UpdatePasswordController;
import edu.ycp.cs.pygmymarmoset.app.model.UpdatedPassword;
import edu.ycp.cs.pygmymarmoset.app.model.User;

@Route(pattern="/passwd", view="/_view/changePassword.jsp")
@Navigation(parent=Index.class)
@CrumbSpec(text="Change password")
public class ChangePassword extends AbstractFormServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected Params createParams(HttpServletRequest req) {
		return new Params(req)
				.add("updatedPasswd", UpdatedPassword.class);
	}

	@Override
	protected LogicOutcome doLogic(Params params, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		User user = (User) req.getSession().getAttribute("user");
		UpdatedPassword updatedPasswd = params.get("updatedPasswd");
		UpdatePasswordController update = new UpdatePasswordController();
		update.execute(user, updatedPasswd);
		req.setAttribute("resultmsg", "Password for " + user.getUsername() + " updated successfully");
		return LogicOutcome.STAY_ON_PAGE;
	}
}
