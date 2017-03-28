package edu.ycp.cs.pygmymarmoset.app.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	
	public static List<String> getPropertyNames(Object bean) {
		try {
			return doGetPropertyNames(bean);
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	private static List<String> doGetPropertyNames(Object bean) throws Exception {
		List<String> propertyNames = new ArrayList<>();
		BeanInfo info = Introspector.getBeanInfo(bean.getClass());
		for (PropertyDescriptor desc : info.getPropertyDescriptors()) {
			if (!desc.getName().equals("class")) {
				propertyNames.add(desc.getName());
			}
		}
		return propertyNames;
	}
}
