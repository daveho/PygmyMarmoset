// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

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
