// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.model.LoginCredentials;
import edu.ycp.cs.pygmymarmoset.app.model.User;

public class AbstractLoginFilter {
	protected boolean checkLogin(ServletRequest req_, ServletResponse resp_) throws IOException {
		HttpServletRequest req = (HttpServletRequest) req_;
		User user = (User) req.getSession().getAttribute("user");
		if (user == null) {
			redirectToLoginPage(req, (HttpServletResponse) resp_);
			return false;
		}
		return true; // user is logged in
	}
	
	protected void redirectToLoginPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// User is not logged in: make a note of goal URL
		// so we can redirect there after successful login.
		// By putting a LoginCredentials object in the
		// session, the Login servlet will be able to see it.
		LoginCredentials creds = new LoginCredentials();
		creds.setGoal(req.getRequestURI());
		req.getSession().setAttribute("creds", creds);
		resp.sendRedirect(req.getContextPath() + "/login");
	}
}
