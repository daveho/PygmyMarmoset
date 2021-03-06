// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.controller.CreateUserController;
import edu.ycp.cs.pygmymarmoset.app.model.User;

@Route(pattern="/a/createUser", view="/_view/adminCreateUser.jsp")
@Navigation(parent=AdminIndex.class)
@CrumbSpec(text="Create user")
public class AdminCreateUser extends AbstractFormServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected Params createParams(HttpServletRequest req) {
		return new Params(req)
				.add("newUser", User.class);
	}

	@Override
	protected LogicOutcome doLogic(Params params, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		User user = params.get("newUser");
		String plaintextPasswd = user.getPasswordHash();
		CreateUserController controller = new CreateUserController();
		try {
			controller.createUser(user);
		} catch (Throwable e) {
			// Insertion failed: revert the password back
			// to the plaintext password
			user.setPasswordHash(plaintextPasswd);
			throw e;
		}
		// Success!
		req.setAttribute("resultmsg", "User " + user.getUsername() + " created successfully");
		// Create empty User object to blank out form fields
		req.setAttribute("newUser", new User());
		return LogicOutcome.STAY_ON_PAGE;
	}
}
