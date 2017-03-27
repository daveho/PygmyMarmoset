package edu.ycp.cs.pygmymarmoset.app.model;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation used to mark timestamp fields.
 * These must be mapped to type <code>long</code> in Java,
 * and represent milliseconds since the epoch.
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface Timestamp {

}
