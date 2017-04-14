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

/**
 * Filter to protect access to servlets requiring that the
 * user is logged in.
 */
public class RequireLogin extends AbstractLoginFilter implements Filter {
	@Override
	public void doFilter(ServletRequest req_, ServletResponse resp_, FilterChain chain)
			throws IOException, ServletException {
		if (!checkLogin(req_, resp_)) {
			return;
		}
		chain.doFilter(req_, resp_);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// nothing to do
	}

	@Override
	public void destroy() {
		// nothing to do
	}
}
