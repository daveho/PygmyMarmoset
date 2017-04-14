// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.model;

public class ValidationException extends PygmyMarmosetException {
	private static final long serialVersionUID = 1L;

	public ValidationException(String msg) {
		super(msg);
	}
	
	public ValidationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
