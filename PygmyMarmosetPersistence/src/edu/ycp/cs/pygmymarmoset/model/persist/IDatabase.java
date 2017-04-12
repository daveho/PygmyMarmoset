package edu.ycp.cs.pygmymarmoset.model.persist;

import java.io.InputStream;
import java.util.List;

import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.IReadBlob;
import edu.ycp.cs.pygmymarmoset.app.model.Pair;
import edu.ycp.cs.pygmymarmoset.app.model.Project;
import edu.ycp.cs.pygmymarmoset.app.model.Role;
import edu.ycp.cs.pygmymarmoset.app.model.RoleType;
import edu.ycp.cs.pygmymarmoset.app.model.Roles;
import edu.ycp.cs.pygmymarmoset.app.model.Submission;
import edu.ycp.cs.pygmymarmoset.app.model.SubmissionStatus;
import edu.ycp.cs.pygmymarmoset.app.model.Term;
import edu.ycp.cs.pygmymarmoset.app.model.Triple;
import edu.ycp.cs.pygmymarmoset.app.model.User;

public interface IDatabase {
	public void createModelClassTable(Class<?> modelCls);
	public User findUserForUsername(String username);
	public boolean createCourse(Course course);
	public boolean createUser(User user);
	public List<Pair<Course, Term>> getAllCourses();
	public boolean createTerm(Term term);
	public List<Term> getAllTerms();
	public Pair<Course,Term> findCourseForCourseId(int courseId);
	public boolean registerStudent(User student, Course course, Role role);
	public List<Pair<User, Role>> getRoster(Course course);
	public boolean addInstructor(Course course, String username, int section);
	public Roles getUserRolesInCourse(User user, Course course);
	public List<String> suggestUsernames(String term);
	public boolean createProject(Course course, Project project);
	public List<Project> getProjectsInCourse(Course course);
	public List<Triple<Course, Term, RoleType>> getCoursesForUser(User user);
	public Project findProjectForProjectId(int projectId);
	public List<Pair<Project, Integer>> getStudentProjects(Course course, User student);
	public Pair<User, Roles> findUserInCourse(Course course, int userId);
	public List<Pair<Submission, SubmissionStatus>> getSubmissions(Project project, User student);
	public Submission createSubmission(Project project, User student, String fileName, InputStream uploadData);
	public Submission findSubmissionForSubmissionId(int submissionId);
	public boolean readSubmissionBlob(Submission submission, IReadBlob reader);
	public List<Pair<User, Integer[]>> getStudentProjectActivity(Project project);
	public boolean updateProject(Project project);
}
