package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ycp.cs.pygmymarmoset.app.util.BeanUtil;

public class Params {
	private static Logger logger = LoggerFactory.getLogger(Params.class);
	
	private Map<String, Object> modelObjects;
	
	public Params() {
		modelObjects = new HashMap<>();
	}
	
	public Params add(String name, Object obj) {
		modelObjects.put(name, obj);
		return this;
	}
	
	public void unmarshal(HttpServletRequest req) {
		// TODO: handle missing fields
		for (Map.Entry<String, Object> entry : modelObjects.entrySet()) {
			String name = entry.getKey();
			Object obj = entry.getValue();
			
			for (String propertyName : BeanUtil.getPropertyNames(obj)) {
				String paramName = name + "." + propertyName;
				String value = req.getParameter(paramName);
				if (value != null) {
					value = value.trim();
					if (!value.equals("")) {
						try {
							BeanUtils.setProperty(obj, propertyName, value);
						} catch (Exception e) {
							logger.warn("Exception unmarshaling parameter " + paramName, e);
						}
					}
				}
			}
		}
	}

	public Object get(String name) {
		return modelObjects.get(name);
	}
}
