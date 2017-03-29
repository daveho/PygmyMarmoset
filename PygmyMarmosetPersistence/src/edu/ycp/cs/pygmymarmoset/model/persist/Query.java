package edu.ycp.cs.pygmymarmoset.model.persist;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.beanutils.BeanUtils;

import edu.ycp.cs.pygmymarmoset.app.model.User;

public class Query {
	public static<E> String getCreateTableStatement(Class<E> modelCls) {
		Introspect<E> info = Introspect.getIntrospect(modelCls);
		
		StringBuilder buf = new StringBuilder();
		
		buf.append("create table ");
		buf.append(info.getTableName());
		buf.append(" (");
		
		DBField pk = null;
		int count = 0;
		for (DBField f : info.getFields()) {
			if (count > 0) {
				buf.append(", ");
			}
			buf.append(f.getName());
			buf.append(" ");
			buf.append(f.getSqlType());
			if (!f.isAllowNull()) {
				buf.append(" not null");
			}
			if (f.isPrimaryKey()) {
				buf.append(" auto_increment");
				pk = f;
			}
			count++;
		}
		if (pk != null) {
			buf.append(", primary key (");
			buf.append(pk.getName());
			buf.append(")");
		}
		
		buf.append(")");
		buf.append(" character set 'utf8' collate 'utf8_general_ci'");
		
		return buf.toString();
	}

	public static<E> String getInsertStatement(Class<E> modelCls) {
		Introspect<E> info = Introspect.getIntrospect(modelCls);
		StringBuilder buf = new StringBuilder();
		
		buf.append("insert into ");
		buf.append(info.getTableName());
		buf.append(" (");
		int count = 0;
		for (DBField field : info.getFields()) {
			if (field.isPrimaryKey()) {
				continue; // these are auto-generated
			}
			if (count > 0) {
				buf.append(", ");
			}
			buf.append(field.getName());
			count++;
		}
		buf.append(") values (");
		for (int i = 0; i < count; i++) {
			if (i > 0) {
				buf.append(", ");
			}
			buf.append("?");
		}
		buf.append(")");
		
		return buf.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(getCreateTableStatement(User.class));
	}

	public static<E> void loadFields(E modelObj, ResultSet resultSet) {
		try {
			doLoadFields(modelObj, resultSet);
		} catch (Exception e) {
			throw new PersistenceException("Could not load model object fields from result set", e);
		}
	}
	
	private static<E> void doLoadFields(E modelObj, ResultSet resultSet) throws Exception {
		@SuppressWarnings("unchecked")
		Class<E> cls = (Class<E>) modelObj.getClass();
		Introspect<E> info = Introspect.getIntrospect(cls);
		for (DBField field : info.getFields()) {
			BeanUtils.setProperty(modelObj, field.getPropertyName(), resultSet.getObject(field.getIndex()));
		}
	}

	public static<E> void storeFieldsNoId(E modelObj, PreparedStatement stmt) {
		try {
			doStoreFieldsNoId(modelObj, stmt);
		} catch (Exception e) {
			throw new PersistenceException("Could not store model object field values in prepared statement", e);
		}
	}
	
	private static<E> void doStoreFieldsNoId(E modelObj, PreparedStatement stmt) throws Exception {
		@SuppressWarnings("unchecked")
		Class<E> cls = (Class<E>) modelObj.getClass();
		Introspect<E> info = Introspect.getIntrospect(cls);
		int index = 1;
		for (DBField field : info.getFields()) {
			if (field.isPrimaryKey()) {
				continue; // skip id
			}
			Object value = BeanUtils.getProperty(modelObj, field.getPropertyName());
			stmt.setObject(index, value);
			//System.out.println("Set field at index " + index);
			index++;
		}
	}
}
