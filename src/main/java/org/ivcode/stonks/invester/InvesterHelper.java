package org.ivcode.stonks.invester;

import org.ivcode.stonks.market.BrokerService;
import org.ivcode.stonks.market.ChartService;
import org.ivcode.stonks.market.MarketTime;
import org.ivcode.stonks.market.QuoteService;

public class InvesterHelper {
	
	public static void setBroker(Investor invester, BrokerService broker) {
		invester.setBroker(broker);
	}
	
	public static void setMarketTime(Investor invester, MarketTime marketTime) {
		invester.setMarketTime(marketTime);
	}
	
	public static void setQuoteService(Investor invester, QuoteService quoteService) {
		invester.setQuoteService(quoteService);
	}
	
	public static void setChartService(Investor invester, ChartService chartService) {
		invester.setChartService(chartService);
	}
	
	public static void setSymbol(Investor invester, String symbol) {
		invester.setSymbol(symbol);
	}
}
