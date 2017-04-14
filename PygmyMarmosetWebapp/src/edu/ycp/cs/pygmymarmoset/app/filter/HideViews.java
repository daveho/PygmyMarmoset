// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.util.ServletUtil;

/**
 * Filter to reject any attempts to access view JSPs directly.
 */
public class HideViews implements Filter {
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		ServletUtil.sendNotFound(
				(HttpServletRequest)req,
				(HttpServletResponse)resp,
				"These aren't the droids you're looking for.");
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// nothing to do
	}

	@Override
	public void destroy() {
		// nothing to do
	}
}
