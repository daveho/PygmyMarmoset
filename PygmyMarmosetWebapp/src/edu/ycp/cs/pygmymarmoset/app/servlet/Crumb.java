// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.servlet;

public class Crumb {
	private final String link;
	private final String text;
	
	public Crumb(String link, String text) {
		this.link = link;
		this.text = text;
	}

	public String getLink() {
		return link;
	}

	public String getText() {
		return text;
	}
}
