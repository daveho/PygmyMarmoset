package edu.ycp.cs.pygmymarmoset.app.model;

public class Term {
	@PrimaryKey
	private int id;
	
	@Unique
	@Desc(size=40)
	private String name;
	
	@Unique
	private int seq;
	
	public Term() {
		
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

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}
}
