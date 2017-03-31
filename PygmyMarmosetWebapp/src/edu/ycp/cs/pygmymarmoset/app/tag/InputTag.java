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
		JspWriter out = getJspContext().getOut();
		
		String type = this.type != null ? this.type : "text";
		
		String escapedValue = "";
		Object bean = TagUtil.getRequestAttribute(getJspContext(), obj);
		if (bean == null) {
			throw new IllegalStateException("Missing model object: " + obj);
		}
		String propVal = BeanUtil.getProperty(bean, field);
		if (propVal != null) {
			escapedValue = StringEscapeUtils.escapeHtml4(propVal);
		}
		
		String size = null;
		if (type.equals("text") || type.equals("password")) {
			Introspect<?> info = Introspect.getIntrospect(bean.getClass());
			DBField dbfield = info.getFieldForPropertyName(field);
			System.out.println("Found field " + field);
			if (dbfield.getSize() > 0) {
				// Set the field width as 3/4 of the maximum size
				size = String.valueOf((dbfield.getSize() * 3) / 4);
				System.out.println("Setting size to " + size);
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
		out.print("\"");
		if (size != null) {
			out.print(" size=\"");
			out.print(size);
			out.print("\"");
		}
		out.print(" value=\"");
		out.print(escapedValue);
		out.print("\"></input>");
	}
}
