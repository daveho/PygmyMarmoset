package edu.ycp.cs.pygmymarmoset.app.servlet;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
public @interface Route {
	public String pattern();
	public String view() default "";
}
