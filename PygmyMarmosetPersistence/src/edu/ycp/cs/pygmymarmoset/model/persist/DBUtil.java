package edu.ycp.cs.pygmymarmoset.model.persist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBUtil {
	private static final Logger logger = LoggerFactory.getLogger(DBUtil.class);
	
	public static void closeQuietly(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.warn("Couldn't close connection", e);
			}
		}
	}

	public static void closeQuietly(PreparedStatement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				logger.warn("Couldn't close prepared statement", e);
			}
		}
	}
}
