package edu.ycp.cs.pygmymarmoset.model.persist.txn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import edu.ycp.cs.pygmymarmoset.model.persist.DatabaseRunnable;
import edu.ycp.cs.pygmymarmoset.model.persist.Query;

public class CreateModelClassTable extends DatabaseRunnable<Boolean> {
	private Class<?> modelCls;
	
	public CreateModelClassTable(Class<?> modelCls) {
		super("create model class " + modelCls.getSimpleName());
		this.modelCls = modelCls;
	}

	@Override
	public Boolean execute(Connection conn) throws SQLException {
		PreparedStatement stmt = prepareStatement(conn, Query.getCreateTableStatement(modelCls));
		stmt.executeUpdate();
		return true;
	}
}
