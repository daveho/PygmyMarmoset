package edu.ycp.cs.pygmymarmoset.main;

// Launch from an uberjar.
public class UberjarMain {
	public static void main(String[] args) {
		UberjarDaemonController controller = new UberjarDaemonController();
		controller.exec(args);
	}
}
