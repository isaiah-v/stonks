package org.ivcode.stonks.utils;

import java.time.LocalDateTime;

public class CachedValue<T, E extends Exception> {
	private final long ttl;
	private final CacheSupplier<T,E> supplier;
	
	private T value;
	private LocalDateTime experation;

	public CachedValue(long ttl, CacheSupplier<T,E> supplier) {
		this.ttl = ttl;
		this.supplier = supplier;
	}
	
	public T get() throws E {
		LocalDateTime now = LocalDateTime.now();
		if(value==null || experation==null || experation.isBefore(now)) {
			value = supplier.get();
			experation = now.plusMinutes(ttl);
		}
		
		return value;
	}
	
	public void clear() {
		this.value = null;
		this.experation = null;
	}
}
