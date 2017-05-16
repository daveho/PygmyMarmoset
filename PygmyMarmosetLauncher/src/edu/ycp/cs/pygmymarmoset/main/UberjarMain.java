package edu.ycp.cs.pygmymarmoset.main;

import java.util.Arrays;
import java.util.List;

// Launch from an uberjar.
public class UberjarMain {
	public static void main(String[] args) throws Exception {
		// Check to see if the first argument is an admin command.
		String cmd = args.length > 0 ? args[0] : "";
		if (cmd.equals("createdb")) {
			NestedJarClassLoader.runMain(UberjarMain.class, "edu.ycp.cs.pygmymarmoset.model.persist.CreateDatabase", trimArgs(args));
		} else {
			UberjarDaemonController controller = new UberjarDaemonController();
			controller.exec(args);
		}
	}
	
	private static List<String> trimArgs(String[] args) {
		return Arrays.asList(args).subList(1, args.length);
	}
}
