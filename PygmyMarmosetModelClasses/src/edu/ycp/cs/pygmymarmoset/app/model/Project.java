package edu.ycp.cs.pygmymarmoset.app.model;

public class Project {
	@PrimaryKey
	private int id;
	
	private int courseId;

	@Desc(size=60)
	private String name;
	
	@Timestamp
	private long ontime;
	
	@Timestamp
	private long late;
	
	private boolean visible;

	public Project() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getOntime() {
		return ontime;
	}

	public void setOntime(long ontime) {
		this.ontime = ontime;
	}

	public long getLate() {
		return late;
	}

	public void setLate(long late) {
		this.late = late;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
