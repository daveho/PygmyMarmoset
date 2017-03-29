package edu.ycp.cs.pygmymarmoset.model.persist;

import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.model.Role;
import edu.ycp.cs.pygmymarmoset.app.model.Submission;
import edu.ycp.cs.pygmymarmoset.app.model.User;

public class CreateDatabase {
	public static void main(String[] args) throws Exception {
		configureLog4j();
		
		IDatabase db = DatabaseProvider.getInstance();
		
		createTable(db, Course.class);
		createTable(db, Project.class);
		createTable(db, Role.class);
		createTable(db, Submission.class);
		createTable(db, User.class);
	}
	
	private static<E> void createTable(IDatabase db, Class<E> modelCls) {
		Introspect<E> info = Introspect.getIntrospect(modelCls);
		System.out.print("Creating table " + info.getTableName() + "...");
		System.out.flush();
		db.createModelClassTable(modelCls);
		System.out.println("done");
	}

	/**
	 * Configure log4j to log to stdout.
	 */
	public static void configureLog4j() {
		// See: http://robertmaldon.blogspot.com/2007/09/programmatically-configuring-log4j-and.html
		org.apache.log4j.Logger rootLogger = org.apache.log4j.Logger.getRootLogger();
		if (!rootLogger.getAllAppenders().hasMoreElements()) {
			rootLogger.setLevel(org.apache.log4j.Level.WARN);
			rootLogger.addAppender(
					new org.apache.log4j.ConsoleAppender(
							new org.apache.log4j.PatternLayout("%-5p [%t]: %m%n")));
		}
	}
}
