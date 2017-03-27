package edu.ycp.cs.pygmymarmoset.model.persist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class DatabaseRunnable<E> {
	private String name;
	private List<Object> cleanupStack;
	
	public DatabaseRunnable(String name) {
		this.name = name;
		this.cleanupStack = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	
	public abstract E execute(Connection conn) throws SQLException;
	
	public PreparedStatement prepareStatement(Connection conn, String sql) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(sql);
		cleanupStack.add(stmt);
		return stmt;
	}
	
	public void cleanup() {
		while (!cleanupStack.isEmpty()) {
			int last = cleanupStack.size() - 1;
			Object obj = cleanupStack.remove(last);
			if (obj instanceof PreparedStatement) {
				DBUtil.closeQuietly((PreparedStatement) obj);
			}
		}
	}
}
