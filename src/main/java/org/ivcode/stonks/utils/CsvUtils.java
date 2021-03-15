package org.ivcode.stonks.utils;

import java.util.Iterator;
import java.util.List;

public class CsvUtils {
	public static String toCsv(List<String> values) {
		if(values==null || values.isEmpty()) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		
		Iterator<String> it = values.iterator();
		sb.append(it.next());
		
		while(it.hasNext()) {
			sb.append(',').append(it.next());
		}
		
		return sb.toString();
	}
}
