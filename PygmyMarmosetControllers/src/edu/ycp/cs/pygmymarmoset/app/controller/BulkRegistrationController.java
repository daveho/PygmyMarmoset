package edu.ycp.cs.pygmymarmoset.app.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.SectionNumber;
import edu.ycp.cs.pygmymarmoset.app.model.User;

public class BulkRegistrationController {
	private static final int LAST_NAME = 1;
	private static final int FIRST_NAME = 2;
	private static final int EMAIL = 11;
	
	public void execute(Course course, InputStream in, SectionNumber secNum) throws IOException {
		InputStreamReader reader = new InputStreamReader(in, Charset.forName("UTF-8"));
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
		int count = 0;
		int create = 0;
		for (CSVRecord record : records) {
			// First record is the header, record with fewer than 12 values
			// is the ending "Count of students" record
			if (count > 0 && record.size() >= 12) {
				String lastName = record.get(LAST_NAME);
				String firstName = record.get(FIRST_NAME);
				String email = record.get(EMAIL);
				
				if (!email.endsWith("@ycp.edu")) {
					// FIXME: report more gracefully
					throw new IOException("Non-YCP email address: " + email);
				}
				String username = email.substring(0, email.length() - "@ycp.edu".length());
				
				User student;
				
				// See if student exists
				FindUserController findUser = new FindUserController();
				student = findUser.execute(username);
				
				if (student == null) {
					// Student doesn't exist yet
					student = new User();
					student.setFirstName(firstName);
					student.setLastName(lastName);
					student.setUsername(username);
					student.setSuperUser(false);
					
					// TODO: create user
					create++;
				}
			}
			count++;
		}
		System.out.println("Read " + count + " record(s)");
		System.out.println("Created " + create + " new Users");
	}
}
