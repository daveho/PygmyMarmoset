// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZipUtil {
	private static final Pattern DRIVE_LETTER = Pattern.compile("^[A-Za-z]:");
	private static final Pattern LEADING_SLASH = Pattern.compile("^/+");

	public static String sanitizeName(String name) {
		// Get rid of drive letter, if there is one
		Matcher m = DRIVE_LETTER.matcher(name);
		if (m.find()) {
			name = name.substring(2);
		}
		name = name.replace('\\', '/');

		// Get rid of leading slashes, if any
		Matcher m2 = LEADING_SLASH.matcher(name);
		if (m2.find()) {
			String s = m.group(0);
			name = name.substring(s.length());
		}
		
		return name;
	}
}
