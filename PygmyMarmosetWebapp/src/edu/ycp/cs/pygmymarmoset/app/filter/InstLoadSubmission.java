// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.util.ServletUtil;

public class InstLoadSubmission extends AbstractSubmissionFilter implements Filter {
	@Override
	protected Integer getSubmissionId(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Submission id is the fourth parameter
		List<Integer> args = ServletUtil.getRequestArgs(req);
		if (args.size() < 4) {
			ServletUtil.sendBadRequest(req, resp, "Submission id is required");
			return null;
		}
		Integer submissionId = args.get(3);
		return submissionId;
	}
}
