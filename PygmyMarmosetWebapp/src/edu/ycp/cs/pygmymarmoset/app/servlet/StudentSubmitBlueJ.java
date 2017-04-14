// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;

import edu.ycp.cs.pygmymarmoset.app.controller.CreateSubmissionController;
import edu.ycp.cs.pygmymarmoset.app.controller.FindCourseAndProjectController;
import edu.ycp.cs.pygmymarmoset.app.controller.FindUserInCourseController;
import edu.ycp.cs.pygmymarmoset.app.controller.LoginController;
import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.LoginCredentials;
import edu.ycp.cs.pygmymarmoset.app.model.Pair;
import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.model.Roles;
import edu.ycp.cs.pygmymarmoset.app.model.User;
import edu.ycp.cs.pygmymarmoset.app.model.ValidationException;
import edu.ycp.cs.pygmymarmoset.app.util.ServletUtil;

/**
 * Receive a submission from a BlueJ submitter.
 * All of the automated submission tools used at YCP
 * use this uploader.
 */
@Route(pattern="/bluej/SubmitProjectViaBlueJSubmitter", view="")
@MultipartConfig(location="/tmp", fileSizeThreshold=1024*1024, maxFileSize=16*1024*1024)
public class StudentSubmitBlueJ extends AbstractServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Pattern SEMESTER_PAT =
			Pattern.compile("^([\\w+]+)\\s+(\\d+)$");

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String username = getRequiredParameter(req, "campusUID");
			String password = getRequiredParameter(req, "password");
			String courseName = getRequiredParameter(req, "courseName");
			String semester = getRequiredParameter(req, "semester");
			String projectNumber = getRequiredParameter(req, "projectNumber");
			
			// Authenticate the user
			LoginCredentials creds = new LoginCredentials();
			creds.setUsername(username);
			creds.setPassword(password);
			LoginController login = new LoginController();
			User user = login.execute(creds);
			if (user == null) {
				ServletUtil.sendForbidden(req, resp, "Invalid username/password");
				return;
			}
			
			// Split semester into term and year.
			semester = semester.trim();
			Matcher m = SEMESTER_PAT.matcher(semester);
			if (!m.matches()) {
				throw new ValidationException("Invalid semester: " + semester);
			}
			String termName = m.group(1);
			Integer year = Integer.parseInt(m.group(2));
			
			// Find the course and project
			FindCourseAndProjectController findCourseAndProject = new FindCourseAndProjectController();
			Pair<Course, Project> courseAndProject = findCourseAndProject.execute(courseName, termName, year, projectNumber);
			if (courseAndProject == null) {
				ServletUtil.sendNotFound(req, resp, "No matching course/project found");
				return;
			}
			
			// Verify that the user is a member of the course
			FindUserInCourseController findUser = new FindUserInCourseController();
			Pair<User, Roles> userAndRoles = findUser.execute(courseAndProject.getFirst(), user.getId());
			if (userAndRoles == null) {
				ServletUtil.sendForbidden(req, resp, "User is not a member of course");
				return;
			}
			
			// Get the submitted file
			Part filePart = req.getPart("submittedFiles");
			if (filePart == null) {
				ServletUtil.sendBadRequest(req, resp, "No file data sent");
				return;
			}

			// At this point we can do the submission!
			CreateSubmissionController createSubmission = new CreateSubmissionController();
			InputStream uploadData = filePart.getInputStream();
			try {
				createSubmission.execute(
						courseAndProject.getSecond(),
						user,
						filePart.getSubmittedFileName(),
						uploadData);
				
				// Success!
				resp.setStatus(HttpServletResponse.SC_OK);
				resp.setContentType("text/plain");
				PrintWriter pw = resp.getWriter();
				pw.println("Successful submission");
			} finally {
				IOUtils.closeQuietly(uploadData);
			}
		} catch (ValidationException e) {
			ServletUtil.sendBadRequest(req, resp, e.getMessage());
			return;
		}
	}

	private String getRequiredParameter(HttpServletRequest req, String paramName) {
		String value = req.getParameter(paramName);
		if (value == null) {
			throw new ValidationException("Missing parameter: " + paramName);
		}
		return value;
	}
}
