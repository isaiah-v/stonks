package org.ivcode.stonks.utils.subscription;

import static org.apache.commons.lang3.StringUtils.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class SubscriptionManager<T> {
	
	private final Map<T, Subscription<T>> subscriptions = new ConcurrentHashMap<>();
	private final Set<T> superSubscribeSet = new LinkedHashSet<>();
	
	public Set<T> getListeners(String symbol) {
		symbol = format(symbol);
		
		Set<T> listeners = new LinkedHashSet<>(superSubscribeSet);
		for(Subscription<T> sub : subscriptions.values()) {
			if(sub.isSubscribed(symbol)) {
				listeners.add(sub.getListener());
			}
		}
		
		return listeners;
	}
	
	public Subscription<T> getSubscription(T listener) {
		return subscriptions.get(listener);
	}
	
	public Subscription<T> subscribe(Collection<String> symbols, T listener) {
		symbols = format(symbols);
		
		Subscription<T> subscription = subscriptions.computeIfAbsent(listener, l->new Subscription<T>(this, l));
		subscription.addSymbols(symbols);
		
		return subscription;
	}
	
	public Subscription<T> unsubscribe(Collection<String> symbols, T listener) {
		Subscription<T> subscription = subscriptions.get(listener);
		if(subscription==null) {
			return null;
		}
		
		subscription.removeSymbols(format(symbols));
		
		if(subscription.getSymbols().isEmpty()) {
			this.unsubscribe(subscription);
		}
		
		return subscription;
	}
	
	public void unsubscribe(Subscription<T> subscription) {
		if(subscription==null) {
			return;
		}
		
		if(subscriptions.remove(subscription.getListener())!=null) {
			subscription.removeParent();
			subscription.clearSymbols();
		}
	}
	
	/**
	 * Registers a listener to listen to all the registered symbols. Note that you
	 * cannot remove individual symbols from this listener. If you want to
	 * unregister this listener, use {@link #removeAll(YahooTickerListener)}
	 * 
	 * @param listener The listener
	 * @return {@code true} if the listener was added
	 */
	public boolean superSubscribe(T listener) {
		return this.superSubscribeSet.add(listener);
	}
	
	public boolean superUnsubscribe(T listener) {
		return this.superSubscribeSet.remove(listener);
	}
	
	public Set<String> getSymbols() {
		Set<String> symbols = new HashSet<>();
		
		for(Subscription<T> sub : subscriptions.values()) {
			symbols.addAll(sub.getSymbols());
		}
		
		return symbols;
	}
	
	public boolean isSubscribed(T listener) {
		Subscription<T> sub = getSubscription(listener);
		return sub!=null && sub.isSubscribed();
	}
	
	public boolean isSuperSubscribed(T listener) {
		return superSubscribeSet.contains(listener);
	}
	
	private static Collection<String> format(Collection<String> symbols) {
		return symbols.stream().map(SubscriptionManager::format).collect(Collectors.toSet());
	}
	
	private static String format(String symbol) {
		return upperCase(trim(symbol));
	}
}
