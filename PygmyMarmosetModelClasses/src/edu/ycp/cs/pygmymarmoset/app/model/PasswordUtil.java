// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.model;

import java.security.SecureRandom;
import java.util.Random;

public class PasswordUtil {
	private static Random rng = new Random(new SecureRandom().nextLong());
	
	private static final int PASSWD_LEN = 8;
	private static final String CHARS = "abcdefghijkmnpqrstuvwxyz23456789";
	
	public static String createRandomPassword() {
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < PASSWD_LEN; i++) {
			buf.append(CHARS.charAt(rng.nextInt(CHARS.length())));
		}
		return buf.toString();
	}

	public static String hashPassword(String plaintextPasswd) {
		String salt = BCrypt.gensalt(12);
		String hash = BCrypt.hashpw(plaintextPasswd, salt);
		return hash;
	}
}
