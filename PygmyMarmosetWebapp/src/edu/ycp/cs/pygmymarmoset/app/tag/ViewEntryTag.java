package edu.ycp.cs.pygmymarmoset.app.tag;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import edu.ycp.cs.pygmymarmoset.app.controller.GetSubmissionEntryController;
import edu.ycp.cs.pygmymarmoset.app.model.IReadBlob;
import edu.ycp.cs.pygmymarmoset.app.model.Submission;

public class ViewEntryTag extends SimpleTagSupport {
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
		
		out.print("<pre id=\"viewentry\">");
		
		// Normally, we wouldn't do a database operation as part of rendering
		// a view.  However, this avoids loading the entire zip entry (which
		// could be large) into memory.
		GetSubmissionEntryController getSubmissionEntry = new GetSubmissionEntryController();
		IReadBlob entryReader = new IReadBlob() {
			@Override
			public void readBlob(InputStream blobIn, String name) {
				try {
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
				} catch (IOException e) {
					throw new RuntimeException("Error reading submission entry data", e);
				}
			}
		};
		getSubmissionEntry.execute(submission, entryIndex, entryReader);
		
		out.print("</pre>");
	}
}
