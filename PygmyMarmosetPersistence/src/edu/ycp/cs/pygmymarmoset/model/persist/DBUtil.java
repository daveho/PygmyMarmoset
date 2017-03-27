package edu.ycp.cs.pygmymarmoset.model.persist;

import java.sql.Connection;
import java.sql.SQLException;

public class DBUtil {
	public static void closeQuietly(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO: log
			}
		}
	}
}
