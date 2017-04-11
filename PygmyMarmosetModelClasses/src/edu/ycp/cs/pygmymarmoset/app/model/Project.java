package edu.ycp.cs.pygmymarmoset.app.model;

public class Project {
	@PrimaryKey
	private int id;
	
	@NonUnique
	private int courseId;

	@Unique(with="courseId") // project name must be unique within course
	@Desc(size=40)
	private String name;
	
	@Desc(size=80)
	private String description;
	
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
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
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
