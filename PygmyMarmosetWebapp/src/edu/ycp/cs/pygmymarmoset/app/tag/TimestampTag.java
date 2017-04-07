package edu.ycp.cs.pygmymarmoset.app.tag;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import edu.ycp.cs.pygmymarmoset.app.servlet.Constants;

public class TimestampTag extends SimpleTagSupport {
	private long val;
	
	public void setVal(long val) {
		this.val = val;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		SimpleDateFormat df = new SimpleDateFormat(Constants.DATETIME_FORMAT);
		String dateStr = df.format(new Date(val));
		JspWriter out = getJspContext().getOut();
		out.print(dateStr);
	}
}
