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
	private boolean unique;
	private boolean nonUnique;
	private String with;
	private boolean timestamp;
	private boolean blob;
	
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

	/**
	 * @return true if this field has a unique index, possibly in combination
	 *         with other fields
	 */
	public boolean isUnique() {
		return unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}
	
	/**
	 * @return true if this field has a non-unique index, possibly in combination
	 *         with other fields
	 */
	public boolean isNonUnique() {
		return nonUnique;
	}
	
	public void setNonUnique(boolean nonUnique) {
		this.nonUnique = nonUnique;
	}

	/**
	 * @return comma-separated list of property names of fields
	 *         with which this field is part of an index (unique or non-unique)
	 */
	public String getWith() {
		return with;
	}

	public void setWith(String with) {
		this.with = with;
	}

	public void setTimestamp(boolean timestamp) {
		this.timestamp = timestamp;
	}
	
	public boolean isTimestamp() {
		return timestamp;
	}
	
	public void setBlob(boolean blob) {
		this.blob = blob;
	}
	
	public boolean isBlob() {
		return blob;
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
		} else if (javaType == byte[].class) {
			// Make sure the @Blob annotation is present
			if (!blob) {
				throw new IllegalStateException("byte[] field not annotated with @Blob");
			}
			return "mediumblob";
		} else {
			String sqlType = javaToSqlType.get(javaType);
			if (sqlType == null) {
				throw new IllegalStateException("Unknown SQL type for Java type " + javaType.getSimpleName());
			}
			return sqlType;
		}
	}

	public boolean isBoolean() {
		return javaType == Boolean.class || javaType == Boolean.TYPE;
	}

	public boolean isEnum() {
		return java.lang.Enum.class.isAssignableFrom(javaType);
	}
}
