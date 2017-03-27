package edu.ycp.cs.pygmymarmoset.model.persist;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ycp.cs.pygmymarmoset.app.model.User;

public class MariaDBDatabase implements IDatabase {
	private static Logger logger = LoggerFactory.getLogger(MariaDBDatabase.class);

	@Override
	public User findUserForUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	private<E> E execute(DatabaseRunnable<E> txn) {
		Connection conn = getConnection();
		try {
			return doExecute(conn, txn);
		} finally {
			DBUtil.closeQuietly(conn);
		}
	}
	
	private Connection getConnection() {
		// TODO Auto-generated method stub
		return null;
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
