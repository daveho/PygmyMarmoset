package edu.ycp.cs.pygmymarmoset.main;

import java.io.File;
import java.io.IOException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.tomcat.InstanceManager;
import org.apache.tomcat.SimpleInstanceManager;
import org.cloudcoder.daemon.IDaemon;
import org.cloudcoder.daemon.Util;
import org.eclipse.jetty.annotations.ServletContainerInitializersStarter;
import org.eclipse.jetty.apache.jsp.JettyJasperInitializer;
import org.eclipse.jetty.plus.annotation.ContainerInitializer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UberjarDaemon implements IDaemon {
	private static final Logger logger = LoggerFactory.getLogger(UberjarDaemon.class);
	
	// Version of Launcher that sets things up to allow JSPs
	// to work.
	private final class UberjarLauncher extends Launcher {
		@Override
		protected void onCreateWebAppContext(WebAppContext webapp) {
			// See: http://bengreen.eu/fancyhtml/quickreference/jettyjsp9error.html
			webapp.setAttribute("org.eclipse.jetty.containerInitializers", jspInitializers());
			webapp.setAttribute(InstanceManager.class.getName(), new SimpleInstanceManager());
			webapp.addBean(new ServletContainerInitializersStarter(webapp), true);
			
			try {
				webapp.setAttribute("javax.servlet.context.tempdir", getScratchDir());
			} catch (IOException e) {
				throw new IllegalStateException("Could not set servlet context temp dir", e);
			}
		}
	}

	private static final int PORT = 8081; // TODO: make configurable
	
	private Server server;
	private File tmpdir;

	@Override
	public void start(String instanceName) {
		// We take this opportunity to set the java.io.tmpdir
		// system property to an instance-specific directory.
		// /tmp (the default value) is not a good place
		// for long-running apps to store files.
		// CentOS 7, for example, appears to delete or change
		// the permissions of files stored in /tmp periodically.
		// If Jetty creates its temporary webapp directory in
		// /tmp, bad stuff happens if static resources or code
		// get modified.
		try {
			String path = "./" + instanceName + "-tmp-" + Util.getPid();
			File tmpdir = new File(path);
			if (!tmpdir.mkdirs()) {
				throw new IOException("Could not create directory " + path);
			}
			System.setProperty("java.io.tmpdir", tmpdir.getAbsolutePath());
			this.tmpdir = tmpdir;
		} catch (Exception e) {
			// This situation isn't ideal, but we'll keep running
			// anyway (with the webapp files in the system temp dir)
			logger.warn("Error creating instance-specific temp dir: {}", e.getMessage());
			logger.warn("Webapp files will be placed in default temp dir {}", System.getProperty("java.io.tmpdir"));
		}
		
		// Launch the webapp!
		ProtectionDomain domain = getClass().getProtectionDomain();
		String codeBase = domain.getCodeSource().getLocation().toExternalForm();
		if (!codeBase.endsWith(".jar")) {
			throw new IllegalStateException("Unexpected non-jar codebase: " + codeBase);
		}
		String webappUrl = "jar:" + codeBase + "!/war";
		try {
			Launcher launcher = new UberjarLauncher();
			this.server = launcher.launch(false, PORT, webappUrl);
			this.server.start();
		} catch (Exception e) {
			throw new IllegalStateException("Could not launch Jetty", e);
		}
	}
	
	// See: http://bengreen.eu/fancyhtml/quickreference/jettyjsp9error.html
	private static List<ContainerInitializer> jspInitializers() {
		JettyJasperInitializer sci = new JettyJasperInitializer();
		ContainerInitializer initializer = new ContainerInitializer(sci, null);
		List<ContainerInitializer> initializers = new ArrayList<ContainerInitializer>();
		initializers.add(initializer);
		return initializers;
	}

	// See: http://bengreen.eu/fancyhtml/quickreference/jettyjsp9error.html
	private static File getScratchDir() throws IOException {
		File tempDir = new File(System.getProperty("java.io.tmpdir"));
		File scratchDir = new File(tempDir.toString(), "embedded-jetty-jsp");

		if (!scratchDir.exists()) {
			if (!scratchDir.mkdirs()) {
				throw new IOException("Unable to create scratch directory: " + scratchDir);
			}
		}
		return scratchDir;
	}

	@Override
	public void handleCommand(String command) {
		// No runtime commands are supported yet
		System.out.println("Unsupported command: " + command);
	}

	@Override
	public void shutdown() {
		// Shut down Jetty and wait for it to exit
		try {
			server.stop();
			server.join();
		} catch (Exception e) {
			throw new IllegalStateException("Exception shutting down Jetty", e);
		}
		
		// Clean up temp dir
		if (tmpdir != null) {
			try {
				FileUtils.deleteDirectory(tmpdir);
			} catch (IOException e) {
				logger.error("Error deleting temp directory", e);
			}
		}
	}
}
