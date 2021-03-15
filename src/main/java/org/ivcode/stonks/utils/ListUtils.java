package org.ivcode.stonks.utils;

import java.util.List;

public class ListUtils {
	public static <T> T first(List<T> list) {
		return list.get(0);
	}
	
	public static <T> T last(List<T> list) {
		return list.get(list.size()-1);
	}
}
