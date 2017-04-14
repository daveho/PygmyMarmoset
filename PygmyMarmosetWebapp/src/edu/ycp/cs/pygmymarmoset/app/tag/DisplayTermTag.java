// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Term;

public class DisplayTermTag extends SimpleTagSupport {
	private Course course;
	
	public void setCourse(Object obj) {
		System.out.println("Setting course to " + obj);
		this.course = (Course) obj;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		List<Term> terms = TagUtil.getRequestAttribute(getJspContext(), "terms");
		
		Term courseTerm = null;
		for (Term term : terms) {
			if (course.getTermId() == term.getId()) {
				courseTerm = term;
				break;
			}
		}
		
		JspWriter out = getJspContext().getOut();
		if (courseTerm != null) {
			out.write(courseTerm.getName());
		} else {
			out.write("???");
		}
		out.write(" ");
		out.write(String.valueOf(course.getYear()));
	}
}
