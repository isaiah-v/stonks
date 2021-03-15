package org.ivcode.stonks.market.simulator.order;

import org.ivcode.stonks.market.MarketTime;
import org.ivcode.stonks.market.MarketWatcherService;
import org.ivcode.stonks.market.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderManagerFactory {
	
	@Autowired
	private MarketWatcherService marketWatcherService;
	
	@Autowired
	private QuoteService quoteService;
	
	@Autowired
	private MarketTime marketTime;
	
	public OrderManager createOrderManager() {
		return new OrderManager(marketWatcherService, quoteService, marketTime);
	}
	
	public OrderManager createOrderManager(MarketWatcherService marketWatcherService, QuoteService quoteService, MarketTime marketTime) {
		return new OrderManager(marketWatcherService, quoteService, marketTime);
	}
}
