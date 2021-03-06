// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.model;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation marking property value(s) that must be
 * unique over all model objects.
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface Unique {
	/**
	 * @return comma-separated list of property names that are also
	 *         part of the unique index along with this property name
	 */
	public String with() default "";
}
