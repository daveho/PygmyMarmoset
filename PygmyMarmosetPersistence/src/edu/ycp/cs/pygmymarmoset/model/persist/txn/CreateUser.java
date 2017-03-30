package edu.ycp.cs.pygmymarmoset.model.persist.txn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;
import edu.ycp.cs.pygmymarmoset.model.persist.Query;

public class CreateUser extends DatabaseRunnable<Boolean> {
	private User user;

	public CreateUser(User user) {
		super("create user");
		this.user = user;
	}
	
	@Override
	public Boolean execute(Connection conn) throws SQLException {
		String insert = Query.getInsertStatement(User.class);
		PreparedStatement stmt = prepareStatement(conn, insert, PreparedStatement.RETURN_GENERATED_KEYS);
		Query.storeFieldsNoId(user, stmt);
		stmt.executeUpdate();
		ResultSet genKeys = getGeneratedKeys(stmt);
		if (!genKeys.next()) {
			throw new SQLException("Could not get generated keys for inserter user");
		}
		user.setId(genKeys.getInt(1));
		return true;
	}
}
