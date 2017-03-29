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
}
