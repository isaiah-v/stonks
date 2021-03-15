package org.ivcode.stonks.market.simulator.driver;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.ivcode.stonks.market.MarketStatus;
import org.ivcode.stonks.market.MarketStatusListener;
import org.ivcode.stonks.market.MarketTime;
import org.ivcode.stonks.market.MarketWatcher;
import org.ivcode.stonks.market.MarketWatcherService;
import org.ivcode.stonks.market.Tick;
import org.ivcode.stonks.market.simulator.event.Event;
import org.ivcode.stonks.market.simulator.event.EventListener;
import org.ivcode.stonks.market.simulator.event.TickEvent;
import org.ivcode.stonks.market.simulator.event.TimeEvent;
import org.ivcode.stonks.utils.TimeUtils;
import org.ivcode.stonks.utils.subscription.Subscription;
import org.ivcode.stonks.utils.subscription.SubscriptionManager;

public class MarketDriver implements MarketTime, MarketWatcherService, EventListener {

	private final SubscriptionManager<MarketWatcher> subscriptionManager = new SubscriptionManager<>();
	private final Set<MarketStatusListener> statusListeners = new HashSet<>();
	
	private MarketStatus status;
	private Date time;
	
	public MarketDriver(Date date, MarketStatus status) {
		this.time = date;
		this.status = status;
	}
	
	@Override
	public void superSubscribe(MarketWatcher watcher) {
		subscriptionManager.superSubscribe(watcher);
	}

	@Override
	public void superUnsubscribe(MarketWatcher watcher) {
		subscriptionManager.superUnsubscribe(watcher);
		
	}

	@Override
	public void subscribe(Collection<String> symbols, MarketWatcher watcher) throws IOException {
		subscriptionManager.subscribe(symbols, watcher);
	}

	@Override
	public void unsubscribe(Collection<String> symbols, MarketWatcher watcher) throws IOException {
		subscriptionManager.unsubscribe(symbols, watcher);
	}

	@Override
	public void unsubscribe(MarketWatcher watcher) throws IOException {
		Subscription<MarketWatcher> sub = subscriptionManager.getSubscription(watcher);
		if(sub!=null) {
			sub.unsubscribe();
		}
	}

	@Override
	public boolean isSubscribed(MarketWatcher watcher) {
		return subscriptionManager.isSubscribed(watcher);
	}

	@Override
	public boolean isSuperSubscribed(MarketWatcher watcher) {
		return subscriptionManager.isSubscribed(watcher);
	}

	@Override
	public Set<String> getSymbols(MarketWatcher watcher) {
		Subscription<MarketWatcher> sub = subscriptionManager.getSubscription(watcher);
		return sub==null ? Collections.emptySet() : sub.getSymbols();
	}

	@Override
	public Set<String> getSymbols() {
		return subscriptionManager.getSymbols();
	}

	@Override
	public Date getTime() {
		return this.time;
	}

	@Override
	public MarketStatus getMarketStatus() {
		return this.status;
	}

	@Override
	public boolean addMarketStatusListener(MarketStatusListener listener) {
		return statusListeners.add(listener);
	}

	@Override
	public boolean removeMarketStatusListener(MarketStatusListener listener) {
		return statusListeners.remove(listener);
	}

	@Override
	public void onTimeEvent(TimeEvent event) {
		updateTime(event);
		
		MarketStatus newStatus = event.getStatus();
		if(this.status==null || !this.status.equals(newStatus)) {
			this.status = newStatus;
			triggerStatusChange(newStatus); 
		}
	}

	@Override
	public void onTickEvent(TickEvent event) {
		updateTime(event);
		triggerTick(event.getTick());
	}
	
	private void updateTime(Event event) {
		LocalDateTime dateTime = event.getTime();
		this.time = TimeUtils.toDate(dateTime);
	}
	
	private void triggerStatusChange(MarketStatus status) {
		for(MarketStatusListener listener : this.statusListeners) {
			try {
				listener.onMarketStatusChange(status);
			} catch (RuntimeException e) {
				// TODO: handle exception
			}
		}
	}
	
	private void triggerTick(Tick tick) {
		String symbol = tick.getSymbol();
		for(MarketWatcher watcher : subscriptionManager.getListeners(symbol)) {
			try {
				watcher.onTick(symbol, tick);
			} catch (RuntimeException e) {
				// TODO: handle exception
			}
		}
	}
}
