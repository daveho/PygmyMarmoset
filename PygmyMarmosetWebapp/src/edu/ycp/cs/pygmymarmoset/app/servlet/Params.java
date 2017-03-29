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
	 * the specified model class is used.
	 * 
	 * @param name name of model object
	 * @param modelCls model class to be instantiated if there is not already a
	 *                 model object present in the session
	 * @return this object, for method chaining
	 */
	public Params add(String name, Class<?> modelCls) {
		Object reqObj = req.getSession().getAttribute(name);
		if (reqObj != null) {
			modelObjects.put(name, reqObj);
		} else {
			Object obj = BeanUtil.newInstance(modelCls);
			modelObjects.put(name, obj);
		}
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

	@SuppressWarnings("unchecked")
	public<E> E get(String name) {
		return (E) modelObjects.get(name);
	}
}
