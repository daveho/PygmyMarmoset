package edu.ycp.cs.pygmymarmoset.app.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.PageContext;

public class TagUtil {
	public static Object getRequestAttribute(JspContext jspCtx, String attrName) {
		PageContext pageCtx = (PageContext) jspCtx;
		HttpServletRequest req = (HttpServletRequest) pageCtx.getRequest();
		return req.getAttribute(attrName);
	}
}
