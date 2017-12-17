// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.tag;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import edu.ycp.cs.pygmymarmoset.app.controller.GetSubmissionEntryController;
import edu.ycp.cs.pygmymarmoset.app.model.IReadBlob;
import edu.ycp.cs.pygmymarmoset.app.model.Submission;

public class ViewEntryTag extends SimpleTagSupport {
	private static final Map<String, String> HIGHLIGHTER_MAP = new HashMap<>();
	static {
		// These are brush names for prism.js
		HIGHLIGHTER_MAP.put(".css", "css");
		HIGHLIGHTER_MAP.put(".c", "c");
		HIGHLIGHTER_MAP.put(".cpp", "cpp");
		HIGHLIGHTER_MAP.put(".cxx", "cpp");
		HIGHLIGHTER_MAP.put(".C", "cpp");
		HIGHLIGHTER_MAP.put(".clj", "clojure");
		HIGHLIGHTER_MAP.put(".cljs", "clojure");
		HIGHLIGHTER_MAP.put(".cljx", "clojure");
		HIGHLIGHTER_MAP.put(".js", "javascript");
		HIGHLIGHTER_MAP.put(".ino", "arduino");
		HIGHLIGHTER_MAP.put(".sh", "bash");
		HIGHLIGHTER_MAP.put(".erl", "erlang");
		HIGHLIGHTER_MAP.put(".go", "go");
		HIGHLIGHTER_MAP.put(".groovy", "groovy");
		HIGHLIGHTER_MAP.put(".hs", "haskell");
		HIGHLIGHTER_MAP.put(".java", "java");
		HIGHLIGHTER_MAP.put(".m", "matlab");
		HIGHLIGHTER_MAP.put(".mk", "makefile");
		HIGHLIGHTER_MAP.put(".mak", "makefile");
		HIGHLIGHTER_MAP.put(".pl", "perl");
		HIGHLIGHTER_MAP.put(".pde", "processing");
		HIGHLIGHTER_MAP.put(".plg", "prolog");
		HIGHLIGHTER_MAP.put(".properties", "properties");
		HIGHLIGHTER_MAP.put(".py", "python");
		HIGHLIGHTER_MAP.put(".rb", "ruby");
		HIGHLIGHTER_MAP.put(".sass", "sass");
		HIGHLIGHTER_MAP.put(".scss", "scss");
		HIGHLIGHTER_MAP.put(".scm", "scheme");
		HIGHLIGHTER_MAP.put(".v", "verilog");
		HIGHLIGHTER_MAP.put(".vhdl", "vhdl");
	}
	
	protected static String findBrush(String fileName) {
		if (fileName.toLowerCase().equals("makefile")) {
			// Special case for Makefiles, which normally don't have
			// a file extension.
			return "makefile";
		}
		int extPos = fileName.lastIndexOf('.');
		if (extPos < 0) {
			return "none";
		}
		String ext = fileName.substring(extPos);
		String brush = HIGHLIGHTER_MAP.get(ext);
		return brush != null ? brush : "none";
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		PageContext pageCtx = (PageContext) getJspContext();
		HttpServletRequest req = (HttpServletRequest) pageCtx.getRequest();
		
		// Both of the objects needed to fetch the entry data
		// are guaranteed to be in the request already.  So,
		// no tag attributes are needed.
		Submission submission = (Submission) req.getAttribute("submission");
		Integer entryIndex = (Integer) req.getAttribute("entryIndex");
		
		JspWriter out = getJspContext().getOut();
		
		// Normally, we wouldn't do a database operation as part of rendering
		// a view.  However, this avoids loading the entire zip entry (which
		// could be large) into memory.
		GetSubmissionEntryController getSubmissionEntry = new GetSubmissionEntryController();
		IReadBlob entryReader = new IReadBlob() {
			@Override
			public void readBlob(InputStream blobIn, String name) {
				try {
					out.print("<h2>Entry ");
					out.print(name);
					out.println("</h2>");
					out.print("<pre id=\"viewentry\"><code class=\"line-numbers language-");
					out.print(findBrush(name));
					out.print("\"");
					out.print(">");
					InputStreamReader reader = new InputStreamReader(blobIn, "UTF-8");
					for (;;) {
						int c = reader.read();
						if (c < 0) {
							break;
						}
						if (c > 127 || c == '"' || c == '<' || c == '>' || c == '&') {
							out.write("&#");
							out.print((int) c);
							out.write(';');
						} else {
							out.write(c);
						}
					}
					out.print("</code></pre>");
				} catch (IOException e) {
					throw new RuntimeException("Error reading submission entry data", e);
				}
			}
		};
		getSubmissionEntry.execute(submission, entryIndex, entryReader);
	}
}
