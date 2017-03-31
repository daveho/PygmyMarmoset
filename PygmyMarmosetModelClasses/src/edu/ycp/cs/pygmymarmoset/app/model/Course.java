package edu.ycp.cs.pygmymarmoset.app.model;

public class Course {
	@PrimaryKey
	private int id;
	
	@Desc(size=40)
	private String name;
	
	@Desc(size=80)
	private String title;

	// TODO: foreign key annotation
	private int termId;
	
	private int year;
	
	public Course() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public int getTermId() {
		return termId;
	}

	public void setTermId(int termId) {
		this.termId = termId;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
}
