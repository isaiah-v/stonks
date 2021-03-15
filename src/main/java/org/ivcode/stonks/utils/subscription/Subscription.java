package org.ivcode.stonks.utils.subscription;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Subscription<T> {
	private SubscriptionManager<T> parent;
	private final T listener;
	private final Set<String> symbols = new HashSet<>();
	
	public Subscription(SubscriptionManager<T> parent, T listener) {
		this.parent = parent;
		this.listener = listener;
	}

	public Set<String> getSymbols() {
		return Collections.unmodifiableSet(symbols);
	}
	
	public boolean isSubscribed(String symbol) {
		return symbols.contains(symbol);
	}
	
	public T getListener() {
		return listener;
	}
	
	public boolean isSubscribed() {
		return this.parent!=null;
	}
	
	public void unsubscribe() {
		SubscriptionManager<T> parent = this.parent;
		if(parent!=null) {
			parent.unsubscribe(this);
		}
	}
	
	boolean addSymbols(Collection<String> symbols) {
		return this.symbols.addAll(symbols);
	}
	
	boolean removeSymbols(Collection<String> symbols) {
		return this.symbols.removeAll(symbols);
	}
	
	void clearSymbols() {
		this.symbols.clear();
	}
	
	void removeParent() {
		this.parent = null;
	}
}
