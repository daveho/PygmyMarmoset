package edu.ycp.cs.pygmymarmoset.model.persist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.model.persist.txn.CreateModelClassTable;
import edu.ycp.cs.pygmymarmoset.model.persist.txn.FindUserForUsername;

public class MariaDBDatabase implements IDatabase {
	public static final String JDBC_DRIVER_CLASS = "org.mariadb.jdbc.Driver";
	static {
		try {
			Class.forName(JDBC_DRIVER_CLASS);
		} catch (Exception e) {
			throw new IllegalStateException("Couldn't load MariaDB driver", e);
		}
	}
	
	private static Logger logger = LoggerFactory.getLogger(MariaDBDatabase.class);
	
	// TODO: don't hard-code
	public static final String JDBC_URL =
			"jdbc:mysql://localhost/pygmymarmoset?user=root&password=root";

	@Override
	public void createModelClassTable(Class<?> modelCls) {
		execute(new CreateModelClassTable(modelCls));
	}
	
	@Override
	public User findUserForUsername(String username) {
		return execute(new FindUserForUsername(username));
	}

	private Connection createConnection() {
		try {
			return DriverManager.getConnection(JDBC_URL);
		} catch (SQLException e) {
			throw new PersistenceException("Couldn't create JDBC connection", e);
		}
	}
	
	private void releaseConnection(Connection conn) {
		DBUtil.closeQuietly(conn);
	}
	
	private<E> E execute(DatabaseRunnable<E> txn) {
		Connection conn = createConnection();
		try {
			return doExecute(conn, txn);
		} finally {
			txn.cleanup();
			releaseConnection(conn);
		}
	}

	private<E> E doExecute(Connection conn, DatabaseRunnable<E> txn) {
		final int MAX_ATTEMPTS = 10;
		int attempts = 0;
		
		while (attempts < MAX_ATTEMPTS) {
			try {
				conn.setAutoCommit(false);
				E result = txn.execute(conn);
				conn.commit();
				
				return result;
			} catch (SQLException e) {
				if (isDeadlock(e)) {
					logger.warn("Deadlock detected, retrying transaction " + txn.getName(), e);
					attempts++;
				} else {
					throw new PersistenceException("Transaction " + txn.getName() + " failed", e);
				}
			}
		}
		
		throw new PersistenceException("Gave up retrying transaction " + txn.getName() + " after " + attempts + " attempts");
	}

	private boolean isDeadlock(SQLException e) {
		String sqlState = e.getSQLState();
		return sqlState != null && (sqlState.equals("40001") || sqlState.equals("41000") || sqlState.equals("23000"));
	}

}
