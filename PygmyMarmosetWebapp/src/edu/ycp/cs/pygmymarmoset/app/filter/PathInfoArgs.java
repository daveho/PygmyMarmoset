// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Filter to parse integer arguments in the URI's path info
 * and store them as the "args" request attribute.
 */
public class PathInfoArgs implements Filter {
	private static Pattern PATH_INFO_PAT =
			Pattern.compile("[A-Za-z]/(\\d+(/\\d+)*)$");
	
	@Override
	public void doFilter(ServletRequest req_, ServletResponse resp_, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) req_;
		Matcher m = PATH_INFO_PAT.matcher(req.getRequestURI());
		List<Integer> intArgs;
		if (!m.find()) {
			// No arguments
			intArgs = Collections.emptyList();
		} else {
			// Parse arguments
			List<String> args = Arrays.asList(m.group(1).split("/"));
			intArgs = args.stream()
					.map((s) -> Integer.parseInt(s))
					.collect(Collectors.toCollection(() -> new ArrayList<Integer>()));
		}
		req.setAttribute("args", intArgs);
		chain.doFilter(req_, resp_);
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
