package edu.ycp.cs.pygmymarmoset.app.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.PageContext;

public class TagUtil {
	@SuppressWarnings("unchecked")
	public static<E> E getRequestAttribute(JspContext jspCtx, String attrName) {
		PageContext pageCtx = (PageContext) jspCtx;
		HttpServletRequest req = (HttpServletRequest) pageCtx.getRequest();
		return (E) req.getAttribute(attrName);
	}
}
