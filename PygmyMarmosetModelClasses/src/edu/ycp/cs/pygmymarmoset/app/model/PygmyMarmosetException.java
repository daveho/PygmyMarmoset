package edu.ycp.cs.pygmymarmoset.app.model;

public class PygmyMarmosetException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PygmyMarmosetException(String msg) {
		super(msg);
	}
	
	public PygmyMarmosetException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public ErrorMessage getErrorMessage() {
		ErrorMessage errmsg = new ErrorMessage();
		errmsg.setText(getMessage());
		return errmsg;
	}
}
