// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

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
