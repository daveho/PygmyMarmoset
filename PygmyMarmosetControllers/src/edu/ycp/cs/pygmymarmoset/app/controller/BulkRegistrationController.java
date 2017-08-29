package edu.ycp.cs.pygmymarmoset.app.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import edu.ycp.cs.pygmymarmoset.app.model.BulkRegistrationOutcome;
import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.PasswordUtil;
import edu.ycp.cs.pygmymarmoset.app.model.Role;
import edu.ycp.cs.pygmymarmoset.app.model.RoleType;
import edu.ycp.cs.pygmymarmoset.app.model.SectionNumber;
import edu.ycp.cs.pygmymarmoset.app.model.User;

public class BulkRegistrationController {
	private static final int LAST_NAME = 1;
	private static final int FIRST_NAME = 2;
	private static final int EMAIL = 11;
	
	public List<BulkRegistrationOutcome> execute(Course course, InputStream in, SectionNumber secNum) throws IOException {
		List<BulkRegistrationOutcome> result = new ArrayList<>();
		
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
				BulkRegistrationOutcome outcome = new BulkRegistrationOutcome();
				outcome.setUsername(username);
				outcome.setGeneratedPassword(""); // in case student account already exists
				
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
					
					String passwd = PasswordUtil.createRandomPassword();
					// Note that CreateUserController expects the password hash to
					// be the actual plaintext password
					student.setPasswordHash(passwd);

					CreateUserController createUser = new CreateUserController();
					createUser.createUser(student);
					
					outcome.setNewUser(true);
					outcome.setGeneratedPassword(passwd);
					
					create++;
				}
				
				// Create Role
				Role role = new Role();
				role.setCourseId(course.getId());
				role.setSection(secNum.getSection());
				role.setType(RoleType.STUDENT);
				role.setUserId(student.getId());
				
				// Register the student
				RegisterStudentController registerStudent = new RegisterStudentController();
				registerStudent.execute(student, course, role);
				
				result.add(outcome);
			}
			count++;
		}
		System.out.println("Read " + count + " record(s)");
		System.out.println("Created " + create + " new Users");
		
		return result;
	}
}
