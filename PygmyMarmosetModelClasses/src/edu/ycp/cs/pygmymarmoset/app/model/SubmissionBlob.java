// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.model;

/**
 * Note that this class exists only for introspection.
 * It may not be instantiated.
 */
public class SubmissionBlob {
	@PrimaryKey
	private int id;
	
	@Unique
	private int submissionId;
	
	@Blob
	private byte[] data;
	
	public SubmissionBlob() {
		throw new IllegalStateException(SubmissionBlob.class.getSimpleName() + " is not instantiable");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSubmissionId() {
		return submissionId;
	}

	public void setSubmissionId(int submissionId) {
		this.submissionId = submissionId;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
}
