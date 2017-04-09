package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Term;

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
		// Set a reference to this servlet object: this is useful for
		// custom tags.
		req.setAttribute("_pmservlet", this);

		// forward request to the view JSP
		req.getRequestDispatcher(getView()).forward(req, resp);
	}
	
	/**
	 * Get trail of navigation {@link Crumb}s appropriate for
	 * specified request.
	 * 
	 * @param req the request
	 * @return trail of navigation {@link Crumb}s
	 */
	public List<Crumb> getTrail(HttpServletRequest req) {
		List<Crumb> trail = new ArrayList<>();
		
		Class<? extends AbstractServlet> cls = this.getClass();
		for (;;) {
			Crumb crumb = createCrumb(cls, req);
			trail.add(crumb);
			Navigation nav = cls.getAnnotation(Navigation.class);
			if (nav == null) {
				break;
			}
			cls = nav.parent();
		}
		
		Collections.reverse(trail);
		
		return trail;
	}

	/**
	 * Create a navigation {@link Crumb} for specified servlet class.
	 * 
	 * @param servletCls the servlet class
	 * @param req the request
	 * @return the navigation {@link Crumb}
	 */
	private Crumb createCrumb(Class<? extends AbstractServlet> servletCls, HttpServletRequest req) {
		CrumbSpec spec = servletCls.getAnnotation(CrumbSpec.class);
		if (spec == null) {
			throw new IllegalStateException("Class " + servletCls.getSimpleName() + " is missing @CrumbSpec annotation");
		}
		
		Route route = getRouteForClass(servletCls);
		PathInfoItem[] items = spec.items();
		String pattern = route.pattern();
		
		if (items.length == 0) {
			// This servlet has a completely static path:
			// its pattern should be useable as a URI.
			return new Crumb(pattern, spec.text());
		}
		
		// Servlet has dynamic path information.
		// Generate the URI from the pattern and model objects
		// in the request.
		StringBuilder uri = new StringBuilder();
		if (!pattern.endsWith("/*")) {
			throw new IllegalStateException("Route for servlet " + servletCls.getSimpleName() +
					" is dynamic, but pattern has no wildcard");
		}
		uri.append(pattern.substring(0, pattern.length() - 2));
		for (PathInfoItem item : items) {
			switch (item) {
			case COURSE_ID:
				uri.append("/");
				Course course = (Course) req.getAttribute("course");
				uri.append(course.getId());
				break;
			case PROJECT_ID:
				uri.append("/");
				uri.append("999"); // FIXME
				break;
			case USER_ID:
				throw new IllegalStateException(item + " not handled yet");
			default:
				throw new IllegalStateException("Unhandled path info item type: " + item);
			}
		}
		
		// Do placeholder substitutions (e.g., %c=course name, %t=term, etc.)
		String text = expandPlaceholders(spec.text(), req);
		
		return new Crumb(uri.toString(), text);
	}
	
	private static final Pattern PLACEHOLDER = Pattern.compile("%[a-z]");

	private String expandPlaceholders(String text, HttpServletRequest req) {
		StringBuffer buf = new StringBuffer();
		Matcher m = PLACEHOLDER.matcher(text);
		while (m.find()) {
			String placeholder = m.group();
			if (placeholder.equals("%c")) {
				Course course = (Course) req.getAttribute("course");
				m.appendReplacement(buf, course.getName());
			} else if (placeholder.equals("%t")) {
				Term term = (Term) req.getAttribute("term");
				Course course = (Course) req.getAttribute("course");
				m.appendReplacement(buf, term.getName() + " " + course.getYear());
			} else if (placeholder.equals("%p")) {
				m.appendReplacement(buf, "«TODO project name»"); // FIXME
			} else {
				throw new IllegalArgumentException("Unknown placeholder: " + placeholder);
			}
		}
		m.appendTail(buf);
		return buf.toString();
	}
}
