package edu.ycp.cs.pygmymarmoset.app.model;

public enum RoleType {
	STUDENT,
	INSTRUCTOR;
	
	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
}
