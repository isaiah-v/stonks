package org.ivcode.stonks.invester;

import java.io.IOException;

import org.ivcode.stonks.market.BrokerService;
import org.ivcode.stonks.market.ChartService;
import org.ivcode.stonks.market.MarketStatus;
import org.ivcode.stonks.market.MarketStatusListener;
import org.ivcode.stonks.market.MarketTime;
import org.ivcode.stonks.market.QuoteService;
import org.ivcode.stonks.market.Tick;

public abstract class Investor implements MarketStatusListener {
	
	private BrokerService broker;
	private MarketTime marketTime;
	private QuoteService quoteService;
	private ChartService chartService;
	private String symbol;
	
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
	public String getSymbol() {
		return symbol;
	}
	void setBroker(BrokerService broker) {
		this.broker = broker;
	}
	void setMarketTime(MarketTime marketTime) {
		this.marketTime = marketTime;
	}
	void setQuoteService(QuoteService quoteService) {
		this.quoteService = quoteService;
	}
	void setChartService(ChartService chartService) {
		this.chartService = chartService;
	}
	void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public abstract void onTick(String symbol, Tick tick) throws IOException;
	public void init(String symbol) throws IOException {}
	public void destroy() {}
	public void onMarketStatusChange(MarketStatus marketStatus) {}
	public abstract <T,R> R visit(InvestorVisitor<T, R> vistor, T argument);
}
