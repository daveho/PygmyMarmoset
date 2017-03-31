package edu.ycp.cs.pygmymarmoset.app.model.introspect;

import java.util.HashMap;
import java.util.Map;

public class DBField {
	private String name;
	private int size;
	private boolean fixed;
	private boolean allowNull;
	private boolean primaryKey;
	private Class<?> javaType;
	private String propertyName;
	private int index;
	
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
	
	public boolean isAllowNull() {
		return allowNull;
	}
	
	public void setAllowNull(boolean allowNull) {
		this.allowNull = allowNull;
	}
	
	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setJavaType(Class<?> type) {
		this.javaType = type;
	}
	
	public Class<?> getJavaType() {
		return javaType;
	}
	
	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}



	private static Map<Class<?>, String> javaToSqlType = new HashMap<>();
	static {
		javaToSqlType.put(Integer.class, "int");
		javaToSqlType.put(Integer.TYPE, "int");
		javaToSqlType.put(Boolean.class, "tinyint");
		javaToSqlType.put(Boolean.TYPE, "tinyint");
		javaToSqlType.put(Long.class, "bigint");
		javaToSqlType.put(Long.TYPE, "bigint");
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
		} else if (Enum.class.isAssignableFrom(javaType)) {
			// Enums are mapped to intss in the database
			return "int";
		} else {
			String sqlType = javaToSqlType.get(javaType);
			if (sqlType == null) {
				throw new IllegalStateException("Unknown SQL type for Java type " + javaType.getSimpleName());
			}
			return sqlType;
		}
	}
}
