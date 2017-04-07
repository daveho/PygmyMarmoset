package edu.ycp.cs.pygmymarmoset.app.model;

public class Triple<FType, SType, TType> extends Pair<FType, SType> {
	private TType third;
	
	public Triple() {
		
	}
	
	public Triple(FType first, SType second, TType third) {
		super(first, second);
		this.third = third;
	}
	
	public void setThird(TType third) {
		this.third = third;
	}
	
	public TType getThird() {
		return third;
	}
}
