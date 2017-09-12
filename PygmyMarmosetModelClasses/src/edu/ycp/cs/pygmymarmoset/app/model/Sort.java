// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.app.model;

/**
 * Utility methods for sort orders.
 */
public class Sort {
	public static<E extends Enum<E>> E find(E[] values, String name) {
		String lcName = name.toLowerCase();
		for (E f : values) {
			if (f.name().toLowerCase().equals(lcName)) {
				return f;
			}
		}
		throw new IllegalArgumentException("Unknown field: " + name);
	}
	
	public static<E extends Enum<E>> E[] sortBy(E[] defaultOrder, E primary) {
		E[] result = defaultOrder.clone();
		result[0] = primary;
		int count = 1;
		for (E f : defaultOrder) {
			if (f != primary) {
				result[count++] = f;
			}
		}
		return result;
	}
}
