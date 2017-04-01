package edu.ycp.cs.pygmymarmoset.app.model;

public class Pair<FType, SType> {
	private FType first;
	private SType second;
	
	public Pair() {
		
	}
	
	public Pair(FType first, SType second) {
		this.first = first;
		this.second = second;
	}

	public FType getFirst() {
		return first;
	}

	public void setFirst(FType first) {
		this.first = first;
	}

	public SType getSecond() {
		return second;
	}

	public void setSecond(SType second) {
		this.second = second;
	}
}
