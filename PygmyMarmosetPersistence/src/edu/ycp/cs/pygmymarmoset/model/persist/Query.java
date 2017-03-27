package edu.ycp.cs.pygmymarmoset.model.persist;

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
	
	public static void main(String[] args) {
		System.out.println(getCreateTableStatement(User.class));
	}
}
