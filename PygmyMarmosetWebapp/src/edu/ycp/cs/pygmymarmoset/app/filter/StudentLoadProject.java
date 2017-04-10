package edu.ycp.cs.pygmymarmoset.app.filter;

import javax.servlet.Filter;

public class StudentLoadProject extends AbstractProjectFilter implements Filter {
	public StudentLoadProject() {
		super(2); // for student URLs, project id is the third argument
	}
}
