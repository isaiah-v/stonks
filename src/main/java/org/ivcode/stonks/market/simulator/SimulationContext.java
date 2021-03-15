package org.ivcode.stonks.market.simulator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.ivcode.stonks.invester.Investor;
import org.ivcode.stonks.market.MarketStatus;
import org.ivcode.stonks.market.simulator.account.AccountManager;
import org.ivcode.stonks.market.simulator.broker.BrokerSimulator;
import org.ivcode.stonks.market.simulator.businessday.BusinessDays;
import org.ivcode.stonks.market.simulator.businessday.SimpleBusonessDays;
import org.ivcode.stonks.market.simulator.chart.ChartCache;
import org.ivcode.stonks.market.simulator.chart.SimulatorChartService;
import org.ivcode.stonks.market.simulator.driver.MarketDriver;
import org.ivcode.stonks.market.simulator.event.Event;
import org.ivcode.stonks.market.simulator.event.EventProcessor;
import org.ivcode.stonks.market.simulator.order.OrderManager;
import org.ivcode.stonks.market.simulator.properties.SimulatorProperties;
import org.ivcode.stonks.market.simulator.quote.SimulatorQuoteService;
import org.ivcode.stonks.service.yahoo.v8.YahooService;
import org.ivcode.stonks.utils.TimeUtils;

public class SimulationContext {

	private final YahooService yahooService;

	private final double deposite;
	private final String symbol;
	private final LocalDate start;
	private final List<Event> events;
	private final SimulatorProperties properties;

	private final ChartCache chartCache = new ChartCache();

	public SimulationContext(YahooService yahooService, double deposite, String symbol, LocalDate start,
			List<Event> events, SimulatorProperties properties) {
		this.yahooService = yahooService;
		this.deposite = deposite;
		this.symbol = symbol;
		this.start = start;
		this.events = events;
		this.properties = properties;
	}

	public Simulation createSimulation(Investor invester) {
		MarketDriver marketDriver = new MarketDriver(TimeUtils.toDate(start), MarketStatus.CLOSED);

		SimulatorQuoteService quoteService = new SimulatorQuoteService(marketDriver);

		OrderManager orderManager = new OrderManager(marketDriver, quoteService, marketDriver);

		BusinessDays businessDays = new SimpleBusonessDays();

		SimulatorChartService chartService = new SimulatorChartService(yahooService, marketDriver, businessDays,
				chartCache);

		AccountManager accountManager = new AccountManager(businessDays, marketDriver, orderManager, properties);
		accountManager.deposite(deposite);

		BrokerSimulator broker = new BrokerSimulator(accountManager, orderManager);

		EventProcessor eventProcessor = new EventProcessor(new ArrayList<>(events),
				Collections.singleton(marketDriver));

		return new Simulation(eventProcessor, marketDriver, broker, marketDriver, quoteService, chartService, symbol,
				invester);
	}

	public YahooService getYahooService() {
		return yahooService;
	}

	public double getDeposite() {
		return deposite;
	}

	public String getSymbol() {
		return symbol;
	}

	public LocalDate getStart() {
		return start;
	}

	public List<Event> getEvents() {
		return events;
	}

	public SimulatorProperties getProperties() {
		return properties;
	}

	public ChartCache getChartCache() {
		return chartCache;
	}
}
