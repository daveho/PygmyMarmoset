// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

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
