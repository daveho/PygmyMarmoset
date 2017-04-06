package edu.ycp.cs.pygmymarmoset.app.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import edu.ycp.cs.pygmymarmoset.app.controller.FindCourseController;
import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Pair;
import edu.ycp.cs.pygmymarmoset.app.model.Term;
import edu.ycp.cs.pygmymarmoset.app.servlet.ServletUtil;

/**
 * Filter to load the {@link Course}/{@link Term} pair for
 * <code>/i/course</code> servlets.  Note that no checks are
 * done to ensure that the user is authorized to access
 * the course.
 */
public class LoadCourse implements Filter {
	@Override
	public void doFilter(ServletRequest req_, ServletResponse resp_, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) req_;
		Object[] args = ServletUtil.parseUrlParams("i", req.getRequestURI());
		Integer courseId = (Integer) args[0];
		
		FindCourseController findCourse = new FindCourseController();
		Pair<Course, Term> courseAndTerm = findCourse.execute(courseId);
		req.setAttribute("course", courseAndTerm.getFirst());
		req.setAttribute("term", courseAndTerm.getSecond());
		
		chain.doFilter(req_, resp_);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// nothing to do
	}

	@Override
	public void destroy() {
		// nothing to do
	}

}
