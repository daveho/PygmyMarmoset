package edu.ycp.cs.pygmymarmoset.main;

import java.security.ProtectionDomain;

import org.cloudcoder.daemon.IDaemon;
import org.eclipse.jetty.server.Server;

public class UberjarDaemon implements IDaemon {
	private static final int PORT = 8081; // TODO: make configurable
	
	private Server server;

	@Override
	public void start(String instanceName) {
		ProtectionDomain domain = getClass().getProtectionDomain();
		String codeBase = domain.getCodeSource().getLocation().toExternalForm();
		if (!codeBase.endsWith(".jar")) {
			throw new IllegalStateException("Unexpected non-jar codebase: " + codeBase);
		}
		String webappUrl = "jar:" + codeBase + "!/war";
		try {
			this.server = Launcher.launch(false, PORT, webappUrl);
		} catch (Exception e) {
			throw new IllegalStateException("Could not launch Jetty", e);
		}
	}

	@Override
	public void handleCommand(String command) {
		// No runtime commands are supported yet
		System.out.println("Unsupported command: " + command);
	}

	@Override
	public void shutdown() {
		try {
			server.stop();
			server.join();
		} catch (Exception e) {
			throw new IllegalStateException("Exception shutting down Jetty", e);
		}
	}
}
