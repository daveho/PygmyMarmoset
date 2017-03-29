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
	private String type;
	private String id;
	
	public void setObj(String obj) {
		this.obj = obj;
	}
	
	public void setField(String field) {
		this.field = field;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		
		String type = this.type != null ? this.type : "text";
		
		String escapedValue = "";
		Object bean = TagUtil.getRequestAttribute(getJspContext(), obj);
		if (bean != null) {
			String propVal = BeanUtil.getProperty(bean, field);
			if (propVal != null) {
				escapedValue = StringEscapeUtils.escapeHtml4(propVal);
			}
		}
		
		out.print("<input name=\"");
		out.print(obj);
		out.print(".");
		out.print(field);
		out.print("\"");
		if (id != null) {
			out.print(" id=\"");
			out.print(id);
			out.print("\"");
		}
		out.print(" type=\"");
		out.print(type);
		out.print("\" value=\"");
		out.print(escapedValue);
		out.print("\"></input>");
	}
}
