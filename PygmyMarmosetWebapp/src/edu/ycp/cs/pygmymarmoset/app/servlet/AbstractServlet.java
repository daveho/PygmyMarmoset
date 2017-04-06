package edu.ycp.cs.pygmymarmoset.app.servlet;

import javax.servlet.http.HttpServlet;

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
}
