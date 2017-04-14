// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ycp.cs.pygmymarmoset.app.model.PygmyMarmosetException;

public abstract class AbstractFormServlet extends AbstractServlet {
	private static final Logger logger = LoggerFactory.getLogger(AbstractFormServlet.class);
	
	public enum LogicOutcome {
		STAY_ON_PAGE,
		REDIRECT,
	}
	
	private static final long serialVersionUID = 1L;
	
	protected abstract Params createParams(HttpServletRequest req);
	protected abstract LogicOutcome doLogic(Params params, HttpServletRequest req, HttpServletResponse resp) throws IOException;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		createParams(req);
		
		// Allow logic?
		
		delegateToView(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Params params = createParams(req);
		LogicOutcome outcome = LogicOutcome.STAY_ON_PAGE;
		try {
			params.unmarshal(); // read data from form into model object(s)
			outcome = doLogic(params, req, resp);
		} catch (PygmyMarmosetException e) {
			logger.error("Error executing form logic", e);
			req.setAttribute("errmsg", e.getErrorMessage());
		}
		if (outcome == LogicOutcome.STAY_ON_PAGE) {
			delegateToView(req, resp);
		}
	}
}
