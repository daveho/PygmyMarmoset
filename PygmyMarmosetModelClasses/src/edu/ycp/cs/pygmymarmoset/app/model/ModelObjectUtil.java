package edu.ycp.cs.pygmymarmoset.app.model;

public class ModelObjectUtil {
	public static boolean isSet(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof String && ((String)obj).trim().equals("")) {
			return false;
		}
		return true;
	}
}
