// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.model.introspect.DBField;
import edu.ycp.cs.pygmymarmoset.app.model.introspect.Introspect;
import edu.ycp.cs.pygmymarmoset.app.util.BeanUtil;

public class Params {
	private static Logger logger = LoggerFactory.getLogger(Params.class);

	private HttpServletRequest req;
	private Map<String, Object> modelObjects;
	
	public Params(HttpServletRequest req) {
		this.req = req;
		this.modelObjects = new HashMap<>();
	}
	
	/**
	 * Add a model object to the {@link Params}.
	 * If the named model object is already present in the
	 * session, it is used.  Otherwise, a new instance of
	 * the specified model class is used.  In either
	 * case, the model object is added to the request
	 * attributes (so it can be used by the view.)
	 * 
	 * @param name name of model object
	 * @param modelCls model class to be instantiated if there is not already a
	 *                 model object present in the session
	 * @return this object, for method chaining
	 */
	public Params add(String name, Class<?> modelCls) {
		Object reqObj = req.getSession().getAttribute(name);
		if (reqObj != null) {
			// Use the model object that already exists in the session attributes.
			modelObjects.put(name, reqObj);
//			System.out.println(name + " object exists in request");
		} else {
			// Create a new model object.
			reqObj = BeanUtil.newInstance(modelCls);
			modelObjects.put(name, reqObj);
//			System.out.println(name + " object does not exist in request");
		}
		// Add to request attributes.
		req.setAttribute(name, reqObj);
		return this;
	}
	
	/**
	 * Add a model object already present in the request
	 * to the parameters.  This is useful for editing
	 * a model object that is automatically loaded by a
	 * filter (i.e., editing the {@link Project} that was
	 * loaded by one of the load course filters.)
	 * 
	 * @param name name of the request attribute
	 * @param modelCls expected class of existing request object
	 * @return this object, for method chaining
	 */
	public Params addFromRequest(String name, Class<?> modelCls) {
		Object obj = req.getAttribute(name);
		if (obj == null) {
			throw new IllegalArgumentException("No such request attribute: " + name);
		}
		if (obj.getClass() != modelCls) {
			throw new IllegalArgumentException("Request attribute " + name +
					" has type " + obj.getClass().getSimpleName() +
					" but we expected " + modelCls.getSimpleName());
		}
		System.out.println("Adding object of type " + obj.getClass().getSimpleName() + " to Params as " + name);
		modelObjects.put(name, obj);
		return this;
	}
	
	public void unmarshal() {
		// TODO: handle missing fields
		for (Map.Entry<String, Object> entry : modelObjects.entrySet()) {
			String name = entry.getKey();
			Object obj = entry.getValue();
			
			Introspect<?> info = Introspect.getIntrospect(obj.getClass());
			
			//for (String propertyName : BeanUtil.getPropertyNames(obj)) {
			for (DBField f : info.getFields()) {
				String propertyName = f.getPropertyName();
				String paramName = name + "." + propertyName;
				String value = req.getParameter(paramName);
				
				//System.out.println("Looking for parameter " + paramName);
				//System.out.println("value=" + value);
				
				try {
					if (f.isBoolean()) {
						// Unmarshal the data from a checkbox.
						// This is a case where if the named parameter
						// value is present at all, it means that the checkbox
						// was checked (true), and if the value is not present,
						// then the checkbox was not checked (false).
						boolean isChecked = (value != null && !value.trim().equals(""));
						//System.out.println(paramName + "=" + isChecked);
						PropertyUtils.setProperty(obj, propertyName, isChecked);
					} else if (f.isTimestamp()) {
						if (value != null) {
							SimpleDateFormat df = new SimpleDateFormat(Constants.DATETIME_FORMAT);
							Date date = df.parse(value);
							PropertyUtils.setProperty(obj, propertyName, date.getTime());
						}
					} else {
						// Other types of fields can be unmarshaled from the
						// value of the parameter.
						if (value != null) {
							value = value.trim();
							if (!value.equals("")) {
								//System.out.println(paramName + "=" + value);
								BeanUtils.setProperty(obj, propertyName, value);
							}
						}
					}
				} catch (Exception e) {
					logger.warn("Exception unmarshaling parameter " + paramName, e);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public<E> E get(String name) {
		return (E) modelObjects.get(name);
	}
}
