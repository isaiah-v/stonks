package org.ivcode.stonks.invester;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.ivcode.stonks.market.BrokerService;
import org.ivcode.stonks.market.ChartService;
import org.ivcode.stonks.market.MarketTime;
import org.ivcode.stonks.market.MarketWatcher;
import org.ivcode.stonks.market.MarketWatcherService;
import org.ivcode.stonks.market.QuoteService;
import org.ivcode.stonks.market.Tick;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvesterManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(InvesterManager.class);

	private final MarketWatcherService watcherService;
	private final BrokerService broker;
	private final MarketTime marketTime;
	private final QuoteService quoteService;
	private final ChartService chartService;

	private final Map<String, InvestorInfo> investers = new HashMap<>();

	@Autowired
	public InvesterManager(MarketWatcherService watcherService, BrokerService broker, MarketTime marketTime,
			QuoteService quoteService, ChartService chartService) {

		this.watcherService = watcherService;
		this.broker = broker;
		this.marketTime = marketTime;
		this.quoteService = quoteService;
		this.chartService = chartService;
	}

	public <T> void addInvester(Investor invester, String symbol) throws IOException {
		symbol = upperCase(trim(symbol));
		
		if (investers.containsKey(symbol)) {
			throw new IllegalArgumentException("investor already used");
		}

		invester.setBroker(broker);
		invester.setChartService(chartService);
		invester.setMarketTime(marketTime);
		invester.setQuoteService(quoteService);
		invester.setSymbol(symbol);

		invester.init(symbol);

		InvestorInfo investorInfo = new InvestorInfo(invester, symbol);
		investers.put(symbol, investorInfo);

		watcherService.subscribe(asList(symbol), investorInfo);
	}

	public <T> boolean removeInvester(String symbol) throws IOException {
		InvestorInfo investorInfo = investers.remove(symbol);
		if (investorInfo == null) {
			return false;
		}

		watcherService.unsubscribe(investorInfo);
		Investor invester = investorInfo.invester;
		invester.destroy();
		invester.setBroker(null);
		invester.setChartService(null);
		invester.setMarketTime(null);
		invester.setQuoteService(null);
		
		return true;
	}

	public MarketWatcherService getWatcherService() {
		return watcherService;
	}

	public BrokerService getBroker() {
		return broker;
	}

	public MarketTime getMarketTime() {
		return marketTime;
	}

	public QuoteService getQuoteService() {
		return quoteService;
	}

	public ChartService getChartService() {
		return chartService;
	}

	private static class InvestorInfo implements MarketWatcher {
		private final Investor invester;
		private final String symbol;

		public InvestorInfo(Investor invester, String symbol) {
			this.invester = invester;
			this.symbol = symbol;
		}

		public Investor getInvester() {
			return invester;
		}

		public String getSymbol() {
			return symbol;
		}

		@Override
		public void onTick(String symbol, Tick tick) {
			try {
				invester.onTick(symbol, tick);
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}
}
