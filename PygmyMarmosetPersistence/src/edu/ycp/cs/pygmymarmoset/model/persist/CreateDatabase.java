// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.model.persist;

import java.util.Scanner;

import edu.ycp.cs.pygmymarmoset.app.model.BCrypt;
import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.model.Role;
import edu.ycp.cs.pygmymarmoset.app.model.Submission;
import edu.ycp.cs.pygmymarmoset.app.model.SubmissionBlob;
import edu.ycp.cs.pygmymarmoset.app.model.Term;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.app.model.introspect.Introspect;

public class CreateDatabase {
    public static void main(String[] args) throws Exception {
		configureLog4j();
		
		Scanner keyboard = new Scanner(System.in);

		System.out.println("Please enter details for initial user account");
		String username = ConfigurationUtil.ask(keyboard, "Enter username:");
		String firstName = ConfigurationUtil.ask(keyboard, "Enter first name:");
		String lastName = ConfigurationUtil.ask(keyboard, "Enter last name:");
		String passwd = ConfigurationUtil.ask(keyboard, "Enter password:");
    	
		IDatabase db = DatabaseProvider.getInstance();
		
		createTable(db, Course.class);
		createTable(db, Project.class);
		createTable(db, Role.class);
		createTable(db, Submission.class);
		createTable(db, SubmissionBlob.class);
		createTable(db, User.class);
		createTable(db, Term.class);
		
		User user = new User();
		user.setUsername(username);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		String salt = BCrypt.gensalt(12);
		user.setPasswordHash(BCrypt.hashpw(passwd, salt));
		user.setSuperUser(true);
		db.createUser(user);
		
		// FIXME: shouldn't hard code these
		createTerm(db, "Spring", 1);
		createTerm(db, "Summer", 2);
		createTerm(db, "Fall", 3);
	}

	private static<E> void createTable(IDatabase db, Class<E> modelCls) {
		Introspect<E> info = Introspect.getIntrospect(modelCls);
		System.out.print("Creating table " + info.getTableName() + "...");
		System.out.flush();
		db.createModelClassTable(modelCls);
		System.out.println("done");
	}
	
	private static void createTerm(IDatabase db, String name, int seq) {
		Term term = new Term();
		term.setName(name);
		term.setSeq(seq);
		db.createTerm(term);
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
