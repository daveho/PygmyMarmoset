// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import edu.ycp.cs.pygmymarmoset.app.model.introspect.DBField;
import edu.ycp.cs.pygmymarmoset.app.model.introspect.Introspect;

public abstract class BeanTag extends SimpleTagSupport {
	private String obj;
	private String field;
	private Object bean;
	private Introspect<? extends Object> info;
	private DBField dbfield;
	
	public void setObj(String obj) {
		this.obj = obj;
	}
	
	public void setField(String field) {
		this.field = field;
	}
	
	public DBField getDbfield() {
		return dbfield;
	}
	
	public String getObj() {
		return obj;
	}
	
	public String getField() {
		return field;
	}
	
	public Object getBean() {
		return bean;
	}

	@Override
	public void doTag() throws JspException, IOException {
		this.bean = TagUtil.getRequestAttribute(getJspContext(), obj);
		if (this.bean == null) {
			throw new IllegalStateException("Missing model object: " + obj);
		}

		this.info = Introspect.getIntrospect(bean.getClass());
		this.dbfield = info.getFieldForPropertyName(field);
		if (dbfield == null) {
			throw new IllegalStateException("Could not find " + field + " property in " + bean.getClass().getSimpleName());
		}
	}
}
