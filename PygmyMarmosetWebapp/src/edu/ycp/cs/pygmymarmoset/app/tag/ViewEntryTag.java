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
		// These are brush names for syntaxhighlighter.js
		HIGHLIGHTER_MAP.put(".pl", "perl");
		HIGHLIGHTER_MAP.put(".c", "cpp");
		HIGHLIGHTER_MAP.put(".cpp", "cpp");
		HIGHLIGHTER_MAP.put(".cxx", "cpp");
		HIGHLIGHTER_MAP.put(".java", "java");
		HIGHLIGHTER_MAP.put(".rb", "ruby");
		HIGHLIGHTER_MAP.put(".py", "python");
		HIGHLIGHTER_MAP.put(".js", "js");
		HIGHLIGHTER_MAP.put(".clj", "clojure");
		HIGHLIGHTER_MAP.put(".cljc", "clojure");
		HIGHLIGHTER_MAP.put(".cljs", "clojure");
		HIGHLIGHTER_MAP.put(".cljx", "clojure");
		HIGHLIGHTER_MAP.put(".mak", "Makefile");
	}
	
	protected static String findBrush(String fileName) {
		if (fileName.toLowerCase().equals("makefile")) {
			// Special case for Makefiles, which normally don't have
			// a file extension.
			return "Makefile";
		}
		int extPos = fileName.lastIndexOf('.');
		if (extPos < 0) {
			return "plain";
		}
		String ext = fileName.substring(extPos);
		String brush = HIGHLIGHTER_MAP.get(ext);
		return brush != null ? brush : "plain";
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
					out.print("</h2>");
					out.print("<pre id=\"viewentry\"");
					out.print(" class=\"brush: ");
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
					out.print("</pre>");
				} catch (IOException e) {
					throw new RuntimeException("Error reading submission entry data", e);
				}
			}
		};
		getSubmissionEntry.execute(submission, entryIndex, entryReader);
	}
}
