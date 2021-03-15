package org.ivcode.stonks.market;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

public interface MarketWatcherService {
	void superSubscribe(MarketWatcher watcher);
	void superUnsubscribe(MarketWatcher watcher);
	void subscribe(Collection<String> symbols, MarketWatcher watcher) throws IOException;
	void unsubscribe(Collection<String> symbols, MarketWatcher watcher) throws IOException;
	void unsubscribe(MarketWatcher watcher) throws IOException;
	boolean isSubscribed(MarketWatcher watcher);
	boolean isSuperSubscribed(MarketWatcher watcher);
	Set<String> getSymbols(MarketWatcher watcher);
	Set<String> getSymbols();
}
