package edu.ycp.cs.pygmymarmoset.model.persist;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs.pygmymarmoset.app.model.Desc;
import edu.ycp.cs.pygmymarmoset.app.model.User;

public class Introspect<E> {
	private Class<E> cls;
	private String name;
	private List<DBField> fields;

	public Introspect(Class<E> cls) throws IntrospectionException, NoSuchFieldException, SecurityException {
		this.cls = cls;
		analyze();
	}
	
	private void analyze() throws IntrospectionException, NoSuchFieldException, SecurityException {
		name = cls.getSimpleName();
		fields = new ArrayList<>();
		BeanInfo beanInfo = Introspector.getBeanInfo(cls);
		PropertyDescriptor[] fieldDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor propertyDesc : fieldDescriptors) {
			if (!propertyDesc.getName().equals("class")) {
				DBField dbField = new DBField();
				dbField.setName(propertyDesc.getName().toLowerCase());
				dbField.setJavaType(propertyDesc.getPropertyType());
				// Note that we require a declared field of the name
				// indicated by the bean property descriptor.
				// This is used to look up the @Desc annotation for
				// the field, if there is one.
				Field f = cls.getDeclaredField(propertyDesc.getName());
				if (f != null) {
					Desc desc = f.getAnnotation(Desc.class);
					if (desc != null) {
						dbField.setSize(desc.size());
						dbField.setFixed(desc.fixed());
					}
				}
				fields.add(dbField);
			}
		}
	}
	
	public String getName() {
		return name;
	}
	
	public String getTableName() {
		return pluralize(name.toLowerCase());
	}
	
	public List<DBField> getFields() {
		return fields;
	}

	private static String pluralize(String name) {
		if (name.endsWith("y")) {
			return name.substring(0, name.length() - 1) + "ies";
		} else {
			return name + "s";
		}
	}
	
	public static void main(String[] args) throws Exception {
		Introspect<User> info = new Introspect<>(User.class);
		System.out.println("name=" + info.getName());
		System.out.println("tableName=" + info.getTableName());
		for (DBField f : info.getFields()) {
			System.out.printf("  Field %s, type=%s, sqltype=%s, size=%d, fixed=%s\n",
					f.getName(),
					f.getJavaType(),
					f.getSqlType(),
					f.getSize(),
					f.isFixed());
		}
	}
}
