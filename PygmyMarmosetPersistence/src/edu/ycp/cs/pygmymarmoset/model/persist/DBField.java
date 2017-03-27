package edu.ycp.cs.pygmymarmoset.model.persist;

import java.util.HashMap;
import java.util.Map;

public class DBField {
	private String name;
	private int size;
	private boolean fixed;
	private Class<?> javaType;
	
	public DBField() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isFixed() {
		return fixed;
	}

	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}

	public void setJavaType(Class<?> type) {
		this.javaType = type;
	}
	
	public Class<?> getJavaType() {
		return javaType;
	}
	
	private static Map<Class<?>, String> javaToSqlType = new HashMap<>();
	static {
		javaToSqlType.put(Integer.class, "integer(10)");
		javaToSqlType.put(Integer.TYPE, "integer(10)");
		javaToSqlType.put(Boolean.class, "tinyint");
		javaToSqlType.put(Boolean.TYPE, "tinyint");
	}
	
	public String getSqlType() {
		if (javaType == String.class) {
			if (size == 0) {
				throw new IllegalStateException("String type with size=0");
			}
			// Determine whether we want char or varchar.
			StringBuilder buf = new StringBuilder();
			if (fixed) {
				buf.append("char");
			} else {
				buf.append("varchar");
			}
			buf.append("(");
			buf.append(size);
			buf.append(")");
			return buf.toString();
		} else {
			String sqlType = javaToSqlType.get(javaType);
			if (sqlType == null) {
				throw new IllegalStateException("Unknown SQL type for Java type " + javaType.getSimpleName());
			}
			return sqlType;
		}
	}
}
