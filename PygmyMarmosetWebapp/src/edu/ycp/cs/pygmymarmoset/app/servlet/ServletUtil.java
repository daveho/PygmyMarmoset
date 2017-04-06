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

	/**
	 * <code>getPathInfo()</code> doesn't work for requests to dynamically
	 * registered servlets.  So we use this instead.
	 * @param spec argument specification
	 * @param path pass <code>req.getRequestURI()</code> here
	 * @return array of arguments
	 * @throws ServletException 
	 */
	public static Object[] parseUrlParams(String spec, String path) throws ServletException {
		Object[] args = new Object[spec.length()];
		if (path.startsWith("/")) {
			path = path.substring(1);
		}
		String[] items = path.split("/");
		System.out.println("Items are: " + String.join(":", items));
		if (items.length < spec.length()) {
			throw new ServletException("Too few elements in path " + path + " (need " + spec.length() + " args)");
		}
		for (int i = spec.length() - 1, j = items.length - 1; i >= 0; i--, j--) {
			switch (spec.charAt(i)) {
			case 'i':
				args[i] = Integer.parseInt(items[j]);
				break;
			default:
				throw new ServletException("Illegal spec field: " + spec.charAt(i));
			}
		}
		return args;
	}
}
