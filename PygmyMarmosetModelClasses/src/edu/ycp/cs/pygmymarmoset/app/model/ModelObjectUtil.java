// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.model;

public class ModelObjectUtil {
	public static boolean isSet(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof String && ((String)obj).trim().equals("")) {
			return false;
		}
		return true;
	}
}
