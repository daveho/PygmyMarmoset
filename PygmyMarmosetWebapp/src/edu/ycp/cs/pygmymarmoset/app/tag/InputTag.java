package edu.ycp.cs.pygmymarmoset.app.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang3.StringEscapeUtils;

import edu.ycp.cs.pygmymarmoset.app.model.introspect.DBField;
import edu.ycp.cs.pygmymarmoset.app.model.introspect.Introspect;
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
		Object bean = TagUtil.getRequestAttribute(getJspContext(), obj);
		if (bean == null) {
			throw new IllegalStateException("Missing model object: " + obj);
		}

		Introspect<?> info = Introspect.getIntrospect(bean.getClass());
		DBField dbfield = info.getFieldForPropertyName(field);
		JspWriter out = getJspContext().getOut();
		
		String type;
		if (this.type != null) {
			type = this.type;
		} else {
			if (dbfield.isBoolean()) {
				type = "checkbox";
			} else {
				type = "text";
			}
		}
		
		String escapedValue = "";
		String propVal = BeanUtil.getProperty(bean, field);
		if (propVal != null) {
			escapedValue = StringEscapeUtils.escapeHtml4(propVal);
		}
		
		String size = null;
		if (type.equals("text") || type.equals("password")) {
			//System.out.println("Found field " + field);
			if (dbfield.getSize() > 0) {
				// Set the field width as 2/3 of the maximum size
				size = String.valueOf((dbfield.getSize() * 2) / 3);
				//System.out.println("Setting size to " + size);
			}
		}
		
		String inputName = obj + "." + field;
		
		out.print("<input name=\"");
		out.print(inputName);
		out.print("\"");
		if (id != null) {
			out.print(" id=\"");
			out.print(id);
			out.print("\"");
		}
		out.print(" type=\"");
		out.print(type);
		out.print("\"");
		if (size != null) {
			out.print(" size=\"");
			out.print(size);
			out.print("\"");
		}
		out.print(" value=\"");
		if (type.equals("checkbox")) {
			// Checkboxes are weird.
			// The value is supposed to distinguish them from
			// among a group of related checkboxes with the same name,
			// which is not at all how we want to use them.
			// So the value field is basically meaningless.
			out.print(inputName);
		} else {
			out.print(escapedValue);
		}
		out.print("\"");
		if (type.equals("checkbox")) {
			if (propVal.equals("true")) {
				out.print(" checked");
			}
		}
		out.print(">");
		out.print("</input>");
	}
}
