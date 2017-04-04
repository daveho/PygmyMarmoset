package edu.ycp.cs.pygmymarmoset.model.persist;

import java.util.List;

import edu.ycp.cs.pygmymarmoset.app.model.Course;
import edu.ycp.cs.pygmymarmoset.app.model.Pair;
import edu.ycp.cs.pygmymarmoset.app.model.Role;
import edu.ycp.cs.pygmymarmoset.app.model.Term;
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
}
