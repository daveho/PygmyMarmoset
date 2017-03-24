package edu.ycp.cs.pygmymarmoset.main;

import java.io.File;
import java.lang.management.ManagementFactory;

import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;

// This is adapted from the Embedded Jetty example from Jetty 9.4.x:
//    https://www.eclipse.org/jetty/documentation/9.4.x/embedded-examples.html#embedded-webapp-jsp

public class Main {
	private static final int PORT = 8080;

	public static void main(String[] args) throws Exception {
		System.out.println("Hello, world");

		Server server = new Server(PORT);

		MBeanContainer mbContainer = new MBeanContainer(ManagementFactory.getPlatformMBeanServer());
		server.addBean(mbContainer);

		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath("/marmoset");
		File warFile = new File("../PygmyMarmosetWebapp/war"); // FIXME
		webapp.setWar(warFile.getAbsolutePath());

		Configuration.ClassList classList = Configuration.ClassList.setServerDefault(server);
		classList.addBefore(
				"org.eclipse.jetty.webapp.JettyWebXmlConfiguration",
				"org.eclipse.jetty.annotations.AnnotationConfiguration");
		webapp.setAttribute(
				"org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
				".*/[^/]*servlet-api-[^/]*\\.jar$|.*/javax.servlet.jsp.jstl-.*\\.jar$|.*/[^/]*taglibs.*\\.jar$" );

		// Don't allow directory listings
		webapp.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");

		// Allow the welcome file to be a servlet
		webapp.setInitParameter("org.eclipse.jetty.servlet.Default.welcomeServlets", "true");

		server.setHandler(webapp);

		server.start();
		server.dumpStdErr();
		server.join();
	}
}
