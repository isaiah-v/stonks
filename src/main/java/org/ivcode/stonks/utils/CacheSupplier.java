package org.ivcode.stonks.utils;

public interface CacheSupplier<T, E extends Exception> {
	T get() throws E;
}
