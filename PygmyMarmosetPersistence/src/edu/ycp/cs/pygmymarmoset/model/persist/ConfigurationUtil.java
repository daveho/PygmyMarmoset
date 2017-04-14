// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.model.persist;

import java.util.Scanner;

public class ConfigurationUtil {
	public static String ask(Scanner keyboard, String prompt) {
		return ConfigurationUtil.ask(keyboard, prompt, null);
	}

	public static int askInt(Scanner keyboard, String prompt) {
		System.out.print(prompt);
		return Integer.parseInt(keyboard.nextLine().trim());
	}

	public static String askString(Scanner keyboard, String prompt) {
		System.out.print(prompt);
		return keyboard.nextLine();
	}

	public static String ask(Scanner keyboard, String prompt, String defval) {
		System.out.println(prompt);
		System.out.print("[default: " + (defval != null ? defval : "") + "] ==> ");
		String value = keyboard.nextLine();
		if (value.trim().equals("") && defval != null) {
			value = defval;
		}
		return value;
	}
}
