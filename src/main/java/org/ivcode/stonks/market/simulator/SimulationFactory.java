package org.ivcode.stonks.market.simulator;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.ivcode.stonks.invester.Investor;
import org.ivcode.stonks.market.simulator.event.Event;
import org.ivcode.stonks.market.simulator.event.EventFactory;
import org.ivcode.stonks.market.simulator.properties.SimulatorProperties;
import org.ivcode.stonks.service.yahoo.v8.YahooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimulationFactory {
	
	@Autowired
	private YahooService yahooService;
	
	@Autowired
	private SimulatorProperties properties;
	
	public Simulation createSimulation(double deposite, String symbol, LocalDate start, LocalDate end, Investor invester) throws IOException {
		SimulationContext context = createSimilationContext(deposite, symbol, start, end);
		return context.createSimulation(invester);
	}
	
	public SimulationContext createSimilationContext(double deposite, String symbol, LocalDate start, LocalDate end) throws IOException {
		EventFactory eventFactory = new EventFactory(yahooService);
		List<Event> events = eventFactory.getEvents(Collections.singleton(symbol), start, end);
		
		return new SimulationContext(yahooService, deposite, symbol, start, events, properties);
	}
	
	public SimulationContext createSimilationContext(double deposite, String symbol, LocalDate start, LocalDate end, SimulatorProperties properties) throws IOException {
		EventFactory eventFactory = new EventFactory(yahooService);
		List<Event> events = eventFactory.getEvents(Collections.singleton(symbol), start, end);
		
		return new SimulationContext(yahooService, deposite, symbol, start, events, properties);
	}
}
