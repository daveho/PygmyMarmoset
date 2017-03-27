package edu.ycp.cs.pygmymarmoset.model.persist;

import java.sql.Connection;

public abstract class DatabaseRunnable<E> {
	private String name;
	
	public DatabaseRunnable(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public abstract E execute(Connection conn);
}
