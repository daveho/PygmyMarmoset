package edu.ycp.cs.pygmymarmoset.app.model;

public class PersistenceException extends PygmyMarmosetException {
	private static final long serialVersionUID = 1L;

	public PersistenceException(String msg) {
		super(msg);
	}
	
	public PersistenceException(String msg, Throwable cause) {
		super(msg, cause);
	}
}