package edu.ycp.cs.pygmymarmoset.app.servlet;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
public @interface CrumbSpec {
	public String text();
	public PathInfoItem[] items() default {};
}
