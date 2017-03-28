package edu.ycp.cs.pygmymarmoset.app.util;

import org.apache.commons.beanutils.BeanUtils;

public class BeanUtil {
	/**
	 * Get a bean property as a string, returning null if the
	 * property is not defined.
	 * 
	 * @param bean a bean
	 * @param propertyName name of property to get
	 * @return the property value, or null if the property isn't defined
	 */
	public static String getProperty(Object bean, String propertyName) {
		try {
			return BeanUtils.getProperty(bean, propertyName);
		} catch (Exception e) {
			return null;
		}
	}
}
