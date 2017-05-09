// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.main;

import org.eclipse.jetty.server.Server;

// Run interactively from Eclipse.
public class Main {
	private static final int PORT = 8080;

	public static void main(String[] args) throws Exception {
		Server server = Launcher.launch(true, PORT, "../PygmyMarmosetWebapp/war");
		server.dumpStdErr();
		server.join();
	}
}
