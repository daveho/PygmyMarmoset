package edu.ycp.cs.pygmymarmoset.app.model;

public class Role {
	@PrimaryKey
	private int id;
	
	@Unique(with="courseId,section")
	private int userId;
	
	private int courseId;
	
	private RoleType type;
	
	private int section;
	
	public Role() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public RoleType getType() {
		return type;
	}

	public void setType(RoleType type) {
		this.type = type;
	}

	public int getSection() {
		return section;
	}

	public void setSection(int section) {
		this.section = section;
	}
}
