package edu.ycp.cs.pygmymarmoset.app.filter;

import javax.servlet.Filter;

import edu.ycp.cs.pygmymarmoset.app.model.Project;

/**
 * Load {@link Project} for instructor servlets which
 * access a specific project.  Assumes that LoadCourse
 * has already loaded the {@link Course}.
 */
public class InstLoadProject extends AbstractProjectFilter implements Filter {
	public InstLoadProject() {
		super(1); // for instructor URLs, project is second argument
	}
}
