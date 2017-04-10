package edu.ycp.cs.pygmymarmoset.app.model;

/**
 * Used to represent an entry in a submission zipfile.
 */
public class SubmissionEntry {
	private String name;
	private long size;
	private int index;
	
	public SubmissionEntry() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
