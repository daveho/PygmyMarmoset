package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class Params {
	private Map<String, String> pairs;
	private List<String> missing;
	
	public Params() {
		pairs = new HashMap<>();
		missing = new ArrayList<>();
	}
	
	public Params getExpected(HttpServletRequest req, String... names) {
		for (String name : names) {
			String val = req.getParameter(name);
			if (val == null || val.trim().equals("")) {
				missing.add(name);
			} else {
				pairs.put(name, val);
			}
		}
		return this;
	}

	public Params getOptional(HttpServletRequest req, String... names) {
		for (String name : names) {
			String val = req.getParameter(name);
			if (val != null) {
				pairs.put(name, val);
			}
		}
		return this;
	}
	
	public boolean hasMissing() {
		return !missing.isEmpty();
	}
	
	public List<String> getMissing() {
		return missing;
	}
}
