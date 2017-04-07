package edu.ycp.cs.pygmymarmoset.app.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import edu.ycp.cs.pygmymarmoset.app.servlet.AbstractServlet;
import edu.ycp.cs.pygmymarmoset.app.servlet.Crumb;

public class CrumbsTag extends SimpleTagSupport {
	@Override
	public void doTag() throws JspException, IOException {
		PageContext pageCtx = (PageContext) getJspContext();
		HttpServletRequest req = (HttpServletRequest) pageCtx.getRequest();
		
		// The delegateToView method sets the _pmservlet request attribute,
		// which is a reference to the AbstractServlet that handled the
		// request.
		AbstractServlet servlet = (AbstractServlet) req.getAttribute("_pmservlet");
		
		// Get breadcrumbs
		List<Crumb> trail = servlet.getTrail(req);
		
		// Render breadcrumbs.
		JspWriter out = getJspContext().getOut();
		out.print("<div class=\"navtrail\">");
		for (int i = 0; i < trail.size(); i++) {
			Crumb crumb = trail.get(i);
			if (i > 0) {
				out.print(" &raquo; ");
			}
			boolean generateLink = i != trail.size() - 1; // don't print last crumb as a link
			if (generateLink) {
				out.print("<a class=\"crumb\" href=\"");
				out.print(pageCtx.getServletContext().getContextPath());
				out.print(crumb.getLink());
				out.print("\">");
			}
			out.print(crumb.getText());
			if (generateLink) {
				out.print("</a>");
			}
		}
		out.print("</div>");
	}
}
