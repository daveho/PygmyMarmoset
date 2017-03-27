package edu.ycp.cs.pygmymarmoset.model.persist;

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
}
