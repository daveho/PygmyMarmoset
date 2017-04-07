package edu.ycp.cs.pygmymarmoset.app.servlet;

public class Crumb {
	private final String link;
	private final String text;
	
	public Crumb(String link, String text) {
		this.link = link;
		this.text = text;
	}

	public String getLink() {
		return link;
	}

	public String getText() {
		return text;
	}
}
