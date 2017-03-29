package edu.ycp.cs.pygmymarmoset.model.persist;

public class DatabaseProvider {
	private static final IDatabase instance = new MariaDBDatabase();
	
	public static IDatabase getInstance() {
		return instance;
	}
}
