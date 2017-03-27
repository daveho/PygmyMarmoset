package edu.ycp.cs.pygmymarmoset.app.model;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Persistence-related metadata for a model object field.
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface Desc {
	public int size();
	public boolean fixed() default false;
}
