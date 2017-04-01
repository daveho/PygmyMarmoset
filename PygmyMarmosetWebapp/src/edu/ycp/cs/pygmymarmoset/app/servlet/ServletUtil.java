package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.model.ErrorMessage;

public class ServletUtil {
	public static void sendForbidden(HttpServletRequest req, HttpServletResponse resp, String errmsgText) throws ServletException, IOException {
		resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
		ErrorMessage errmsg = new ErrorMessage();
		errmsg.setText(errmsgText);
		req.setAttribute("errmsg", errmsg);
		req.getRequestDispatcher("/_view/forbidden.jsp").forward(req, resp);
	}
	
	public static Object[] parsePathInfo(String spec, String pathInfo) throws ServletException {
		Object[] result = new Object[spec.length()];
		if (pathInfo.startsWith("/")) {
			pathInfo = pathInfo.substring(1);
		}
		String[] items = pathInfo.split("/");
		if (items.length < spec.length()) {
			throw new ServletException("Invalid path info " + pathInfo);
		}
		for (int i = 0; i < spec.length(); i++) {
			switch (spec.charAt(i)) {
			case 'i':
				result[i] = Integer.parseInt(items[i]);
				break;
			default:
				throw new ServletException("Illegal spec field: " + spec.charAt(i));
			}
		}
		return result;
	}
}
