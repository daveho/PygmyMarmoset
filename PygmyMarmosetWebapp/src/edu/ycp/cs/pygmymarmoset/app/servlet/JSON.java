package edu.ycp.cs.pygmymarmoset.app.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSON {
	private static final ObjectMapper theObjectMapper = new ObjectMapper();

	public static ObjectMapper getObjectMapper() {
		return theObjectMapper;
	}
}
