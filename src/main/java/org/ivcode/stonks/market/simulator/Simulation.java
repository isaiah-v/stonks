package org.ivcode.stonks.market.simulator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ivcode.stonks.invester.InvesterHelper;
import org.ivcode.stonks.invester.Investor;
import org.ivcode.stonks.market.AccountSummary;
import org.ivcode.stonks.market.ChartService;
import org.ivcode.stonks.market.MarketTime;
import org.ivcode.stonks.market.MarketWatcher;
import org.ivcode.stonks.market.MarketWatcherService;
import org.ivcode.stonks.market.Quote;
import org.ivcode.stonks.market.QuoteService;
import org.ivcode.stonks.market.Stock;
import org.ivcode.stonks.market.simulator.action.Action;
import org.ivcode.stonks.market.simulator.broker.BrokerSimulator;
import org.ivcode.stonks.market.simulator.event.EventProcessor;

public class Simulation {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Simulation.class);
	
	private final EventProcessor eventProcessor;
	
	private final MarketWatcherService watcherService;
	private final BrokerSimulator broker;
	private final MarketTime marketTime;
	private final QuoteService quoteService;
	private final ChartService chartService;
	private final String symbol;
	private final Investor invester;
	
	public Simulation(EventProcessor eventProcessor, MarketWatcherService watcherService, BrokerSimulator broker,
			MarketTime marketTime, QuoteService quoteService, ChartService chartService, String symbol,
			Investor invester) {
		this.eventProcessor = eventProcessor;
		this.watcherService = watcherService;
		this.broker = broker;
		this.marketTime = marketTime;
		this.quoteService = quoteService;
		this.chartService = chartService;
		this.symbol = symbol;
		this.invester = invester;
	}


	public SimulationSummary run() throws IOException {
		AccountSummary before = this.broker.getAccountSummary();
		
		MarketWatcher watcher = (s,t) -> {
			try {
				invester.onTick(s, t);
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		};
		
		watcherService.subscribe(Collections.singleton(symbol), watcher);
		marketTime.addMarketStatusListener(invester);
		
		InvesterHelper.setBroker(invester, broker);
		InvesterHelper.setChartService(invester, chartService);
		InvesterHelper.setMarketTime(invester, marketTime);
		InvesterHelper.setQuoteService(invester, quoteService);
		InvesterHelper.setSymbol(invester, symbol);
		
		invester.init(symbol);
		
		
		eventProcessor.run();
		
		invester.destroy();	
		watcherService.unsubscribe(Collections.singleton(symbol), watcher);
		marketTime.removeMarketStatusListener(invester);
		
		AccountSummary after = this.broker.getAccountSummary();
		
		return createSimulationSummary(before, after, broker.getActions());
	}
	
	private SimulationSummary createSimulationSummary(AccountSummary before, AccountSummary after, List<Action> actions) throws IOException {
		double startMarketValue = before.getAvailableCash();
		
		List<SimulationStock> stocks = convertStocks(after.getHoldings());
		double stockValue = getStockTotalMarketValue(stocks);
		
		double cash = after.getAvailableCash();
		double unsettled = after.getUnsettledFunds();
		
		double totalMarketValue = stockValue + cash + unsettled;
		
		double change = (totalMarketValue-startMarketValue) / startMarketValue;
		
		return new SimulationSummary(startMarketValue, totalMarketValue, cash, unsettled, change, stocks, actions);
	}
	
	private List<SimulationStock> convertStocks(List<Stock> stocks) throws IOException {
		List<SimulationStock> values = new ArrayList<>();
		
		for(Stock stock : stocks) {
			String symbol = stock.getSymbol();
			Quote quote = this.quoteService.getQuote(symbol);
			
			double listPrice = quote.getList();
			values.add(new SimulationStock(symbol, stock.getTotalQuantity(), stock.getAveragePrice(), listPrice));
		}
		
		return values;
	}
	
	private double getStockTotalMarketValue(List<SimulationStock> stocks) {
		double value = 0;
		
		for(SimulationStock stock : stocks) {
			value += stock.getMarketValue();
		}
		
		return value;
	}
}
