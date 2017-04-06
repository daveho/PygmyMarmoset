package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static Route getRouteForClass(Class<? extends AbstractServlet> servletCls) {
		Route route = servletCls.getAnnotation(Route.class);
		if (route == null) {
			throw new IllegalArgumentException("Servlet class " + servletCls.getSimpleName() +
					" is missing the @Route annotation");
		}
		return route;
	}
	
	public Route getRoute() {
		return getRouteForClass(this.getClass());
	}
	
	public String getPattern() {
		return getRoute().pattern();
	}
	
	public String getView() {
		return getRoute().view();
	}
	
	public void delegateToView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher(getView()).forward(req, resp);
	}
}
