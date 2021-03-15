package org.ivcode.stonks.utils;

import static org.apache.commons.lang3.StringUtils.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class QueryStringBuilder {
	private final Map<String, String> queryMap = new LinkedHashMap<>();
	
	public QueryStringBuilder with(String name, Integer value) {
		return with(name, value==null ? null : value.toString());
	}
	
	public QueryStringBuilder with(String name, Collection<String> values) {
		if(values==null || values.isEmpty()) {
			return with(name, (String)null);
		}
		
		StringBuilder sb = new StringBuilder();
		
		Iterator<String> it = values.iterator();
		sb.append(it.next());
		
		while(it.hasNext()) {
			sb.append(',').append(it.next());
		}
		
		return with(name, sb.toString());
	}
	
	public QueryStringBuilder with(String name, String value) {
		name = trimToNull(name);
		if(name==null) {
			throw new IllegalArgumentException("name must be defined");
		}
		value = trimToNull(value);
		if(value==null) {
			return this;
		}
		
		Object old = queryMap.putIfAbsent(name, value);
		if(old!=null) {
			throw new IllegalArgumentException("property already exists: name");
		}
		
		return this;
	}
	
	public String build() {
		if(queryMap.isEmpty()) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append('?');
		
		Iterator<Map.Entry<String, String>> it = queryMap.entrySet().iterator();
		
		Map.Entry<String, String> next = it.next();
		sb.append(encode(next.getKey())).append('=').append(encode(next.getValue()));
		
		while(it.hasNext()) {
			next = it.next();
			sb.append('&').append(encode(next.getKey())).append('=').append(encode(next.getValue()));
		}
		
		return sb.toString();
	}
	
	private static String encode(String value) {
		return URLEncoder.encode(value, StandardCharsets.UTF_8);
	}
}
