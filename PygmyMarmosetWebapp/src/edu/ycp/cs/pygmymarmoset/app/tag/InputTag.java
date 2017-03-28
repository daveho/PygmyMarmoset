package edu.ycp.cs.pygmymarmoset.app.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang3.StringEscapeUtils;

import edu.ycp.cs.pygmymarmoset.app.util.BeanUtil;

public class InputTag extends SimpleTagSupport {
	private String obj;
	private String field;
	
	public void setObj(String obj) {
		this.obj = obj;
	}
	
	public void setField(String field) {
		this.field = field;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		
		String type = "text"; // TODO: handle password fields
		
		String escapedValue = "";
		Object bean = getJspContext().getAttribute(obj);
		if (bean != null) {
			String propVal = BeanUtil.getProperty(bean, field);
			if (propVal != null) {
				escapedValue = StringEscapeUtils.escapeHtml4(escapedValue);
			}
		}
		
		out.print("<input name=\"");
		out.print(obj);
		out.print(".");
		out.print(field);
		out.print("\" type=\"");
		out.print(type);
		out.print("\" value=\"");
		out.print(escapedValue);
		out.print("\"></input>");
	}
}
