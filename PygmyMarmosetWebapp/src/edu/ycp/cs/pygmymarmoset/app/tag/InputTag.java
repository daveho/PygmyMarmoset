// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017,2018 David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.tag;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.lang3.StringEscapeUtils;

import edu.ycp.cs.pygmymarmoset.app.model.introspect.DBField;
import edu.ycp.cs.pygmymarmoset.app.servlet.Constants;
import edu.ycp.cs.pygmymarmoset.app.util.BeanUtil;

public class InputTag extends BeanTag {
	private String type;
	private String id;
	private String autocomplete = "true"; // by default, allow autocompletion of password fields
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setAutocomplete(String autocomplete) {
		this.autocomplete = autocomplete;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		super.doTag();
		
		JspWriter out = getJspContext().getOut();
		
		String obj = getObj();
		String field = getField();
		DBField dbfield = getDbfield();
		Object bean = getBean();
		
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
		
		if (dbfield.isTimestamp()) {
			// Convert timestamp (millis since epoch) to
			// text date/time.
			if (escapedValue.equals("0")) {
				// This is an uninitialized date/time, so just turn it into
				// a blank.
				escapedValue = "";
			} else if (!escapedValue.equals("")) {
				long tsval;
				try {
					tsval = Long.parseLong(escapedValue);
					Timestamp ts = new Timestamp(tsval);
					SimpleDateFormat df = new SimpleDateFormat(Constants.DATETIME_FORMAT);
					escapedValue = df.format(ts);
				} catch (Exception e) {
					// Just leave it blank
					escapedValue = "";
				}
			}
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
		if (type.equals("password") && !Boolean.valueOf(autocomplete)) {
			out.print(" autocomplete=\"new-password\"");
		}
		// Disable autocompletion for timestamp fields: it interferes with the
		// datetime picker UI.
		if (dbfield.isTimestamp()) {
			out.print(" autocomplete=\"off\"");
		}
		out.print(">");
	}
}
