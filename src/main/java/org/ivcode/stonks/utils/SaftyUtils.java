package org.ivcode.stonks.utils;

import java.util.Collection;
import java.util.Collections;

public class SaftyUtils {
	public static <T> Collection<T> safe(Collection<T> c) {
		return c==null ? Collections.emptyList() : c;
	}
	
	public static double safe(Double value) {
		return value==null ? 0 : value.doubleValue();
	}
	
	public static long safe(Long value) {
		return value==null ? 0 : value.longValue();
	}
}
