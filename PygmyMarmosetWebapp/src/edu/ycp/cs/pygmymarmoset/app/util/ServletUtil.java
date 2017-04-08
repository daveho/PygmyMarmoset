package edu.ycp.cs.pygmymarmoset.app.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.model.ErrorMessage;

public class ServletUtil {
	public static void sendForbidden(HttpServletRequest req, HttpServletResponse resp, String errmsgText) throws ServletException, IOException {
		sendErrorResponse(req, resp, "Access is forbidden", errmsgText, HttpServletResponse.SC_FORBIDDEN);
	}

	public static void sendBadRequest(HttpServletRequest req, HttpServletResponse resp, String errmsgText) throws ServletException, IOException {
		sendErrorResponse(req, resp, "Bad request", errmsgText, HttpServletResponse.SC_BAD_REQUEST);
	}
	
	private static void sendErrorResponse(
			HttpServletRequest req, HttpServletResponse resp, String title, String errmsgText, int statusCode)
					throws ServletException, IOException {
		resp.setStatus(statusCode);
		ErrorMessage errmsg = new ErrorMessage();
		errmsg.setText(errmsgText);
		req.setAttribute("title", title);
		req.setAttribute("errmsg", errmsg);
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
}
