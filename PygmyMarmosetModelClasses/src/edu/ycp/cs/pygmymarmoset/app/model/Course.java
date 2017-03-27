package edu.ycp.cs.pygmymarmoset.app.model;

public class Course {
	@PrimaryKey
	private int id;
	
	@Desc(size=60)
	private String name;
	
	@Desc(size=40)
	private String term;
	
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

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}
}
