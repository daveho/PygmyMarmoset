// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.controller.FindUserInCourseController;
import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.ErrorMessage;
import edu.ycp.cs.pygmymarmoset.app.model.Pair;
import edu.ycp.cs.pygmymarmoset.app.model.Roles;
import edu.ycp.cs.pygmymarmoset.app.model.User;

public class ServletUtil {
	public static void sendForbidden(HttpServletRequest req, HttpServletResponse resp, String errmsgText)
			throws ServletException, IOException {
		sendErrorResponse(req, resp, "Access is forbidden", errmsgText, HttpServletResponse.SC_FORBIDDEN);
	}

	public static void sendBadRequest(HttpServletRequest req, HttpServletResponse resp, String errmsgText)
			throws ServletException, IOException {
		sendErrorResponse(req, resp, "Bad request", errmsgText, HttpServletResponse.SC_BAD_REQUEST);
	}

	public static void sendNotFound(HttpServletRequest req, HttpServletResponse resp, String errmsgText)
			throws ServletException, IOException {
		sendErrorResponse(req, resp, "Not found", errmsgText, HttpServletResponse.SC_NOT_FOUND);
	}
	
	private static void sendErrorResponse(
			HttpServletRequest req, HttpServletResponse resp, String title, String errmsgText, int statusCode)
					throws ServletException, IOException {
		resp.setStatus(statusCode);
		ErrorMessage errmsg = new ErrorMessage();
		errmsg.setText(errmsgText);
		req.setAttribute("title", title);
		req.setAttribute("errmsg", errmsg);
		req.setAttribute("httpStatus", statusCode);
		req.getRequestDispatcher("/_view/errorResponse.jsp").forward(req, resp);
	}

	/**
	 * Get the arguments specified in the request URI's path info.
	 * @param req the request
	 * @return the list of arguments
	 */
	public static List<Integer> getRequestArgs(HttpServletRequest req) {
		// Arguments are added to the request attributes automatically
		// by the PathInfoArgs filter.
		@SuppressWarnings("unchecked")
		List<Integer> args = (List<Integer>) req.getAttribute("args");
		return args;
	}

	public static boolean loadStudent(HttpServletRequest req, HttpServletResponse resp, Course course, int studentIdArg)
			throws ServletException, IOException {
		List<Integer> args = getRequestArgs(req);
		if (args.size() >= studentIdArg + 1) {
			Integer studentId = args.get(studentIdArg);
			FindUserInCourseController findUser = new FindUserInCourseController();
			Pair<User, Roles> studentInfo = findUser.execute(course, studentId);
			if (studentInfo == null) {
				sendNotFound(req, resp, "No such student");
				return false;
			}
			req.setAttribute("student", studentInfo.getFirst());
			req.setAttribute("studentRoles", studentInfo.getSecond());
		}
		return true;
	}
}
