package org.ivcode.stonks.market.live;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import org.ivcode.stonks.market.MarketStatus;
import org.ivcode.stonks.market.MarketTime;
import org.ivcode.stonks.market.MarketWatcher;
import org.ivcode.stonks.market.MarketWatcherService;
import org.ivcode.stonks.repository.yahoo.websocket.YahooTick;
import org.ivcode.stonks.repository.yahoo.websocket.YahooWebSocketClient;
import org.ivcode.stonks.repository.yahoo.websocket.YahooWebSocketClientFactory;
import org.ivcode.stonks.utils.subscription.Subscription;
import org.ivcode.stonks.utils.subscription.SubscriptionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LiveMarketWatcherService implements MarketWatcherService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LiveMarketWatcherService.class);
	
	private final YahooWebSocketClient websocketClient;
	private final SubscriptionManager<MarketWatcher> subscriptions = new SubscriptionManager<>();
	
	@Autowired
	public LiveMarketWatcherService(
			YahooWebSocketClientFactory websocketFactory,
			MarketTime marketTime) {
		
		this.websocketClient = websocketFactory.createYahooWebSocketClient(this::onTick);
		marketTime.addMarketStatusListener(this::onMarketStatusChange);
	}

	@Override
	public void superSubscribe(MarketWatcher watcher) {
		subscriptions.superSubscribe(watcher);
	}

	
	@Override
	public void superUnsubscribe(MarketWatcher watcher) {
		subscriptions.superUnsubscribe(watcher);
	}

	@Override
	public void subscribe(Collection<String> symbols, MarketWatcher watcher) throws IOException {
		subscriptions.subscribe(symbols, watcher);
		websocketClient.setSubscriptionSymbols(subscriptions.getSymbols());
	}

	@Override
	public void unsubscribe(Collection<String> symbols, MarketWatcher watcher) throws IOException {
		subscriptions.unsubscribe(symbols, watcher);
		websocketClient.setSubscriptionSymbols(subscriptions.getSymbols());
	}

	@Override
	public void unsubscribe(MarketWatcher watcher) throws IOException {
		Subscription<MarketWatcher> sub = subscriptions.getSubscription(watcher);
		if(sub!=null) {
			sub.unsubscribe();
		}
		
		websocketClient.setSubscriptionSymbols(subscriptions.getSymbols());
	}

	@Override
	public Set<String> getSymbols(MarketWatcher watcher) {
		Subscription<MarketWatcher> sub = subscriptions.getSubscription(watcher);
		return sub.getSymbols();
	}

	@Override
	public Set<String> getSymbols() {
		return subscriptions.getSymbols();
	}
	
	@Override
	public boolean isSubscribed(MarketWatcher watcher) {
		return subscriptions.isSubscribed(watcher);
	}
	
	@Override
	public boolean isSuperSubscribed(MarketWatcher watcher) {
		return subscriptions.isSubscribed(watcher);
	}
	
	private void onMarketStatusChange(MarketStatus marketStatus) {
		try {
			onMarketTimeChange(marketStatus);
		} catch(IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
	}
	
	private void onMarketTimeChange(MarketStatus marketStatus) throws IOException {
		if(MarketStatus.OPEN.equals(marketStatus)) {
			websocketClient.open();
		} else if(MarketStatus.CLOSED.equals(marketStatus)) {
			websocketClient.close();
		}
	}
	
	public void onTick(YahooTick tick) {
		for(MarketWatcher listener : subscriptions.getListeners(tick.getSymbol())) {
			try {
				listener.onTick(tick.getSymbol() ,YahooTickerTransformer.createTick(tick));
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}
}
