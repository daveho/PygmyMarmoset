package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;

import org.reflections.Reflections;

/**
 * Servlet context listener to register all subclasses of {@link AbstractServlet}.
 * Each must have a {@link Route} annotation.
 */
public class RegisterServlets implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent e) {
		Package pkg = RegisterServlets.class.getPackage();
		Reflections r = new Reflections(pkg.getName());
		Set<Class<? extends AbstractServlet>> servlets = r.getSubTypesOf(AbstractServlet.class);
		for (Class<? extends AbstractServlet> cls : servlets) {
			if (Modifier.isAbstract(cls.getModifiers())) {
				continue;
			}
			System.out.println("Registering servlet " + cls.getSimpleName());
			Route route = AbstractServlet.getRouteForClass(cls);
			ServletRegistration.Dynamic addServlet = e.getServletContext().addServlet(cls.getSimpleName(), cls);
			addServlet.addMapping(route.pattern());
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent e) {
		// nothing to do
	}
}
