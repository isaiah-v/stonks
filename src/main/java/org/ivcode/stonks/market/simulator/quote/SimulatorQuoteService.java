package org.ivcode.stonks.market.simulator.quote;

import static org.ivcode.stonks.utils.SaftyUtils.*;
import static java.util.stream.Collectors.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ivcode.stonks.market.MarketWatcherService;
import org.ivcode.stonks.market.Quote;
import org.ivcode.stonks.market.QuoteService;
import org.ivcode.stonks.market.Tick;

public class SimulatorQuoteService implements QuoteService {

	private final Map<String, Quote> quoteMap = new HashMap<>();
	
	public SimulatorQuoteService(MarketWatcherService market) {
		market.superSubscribe(this::onTick);
	}
	
	public Quote getQuote(String symbol) {
		return quoteMap.get(symbol);
	}

	private void onTick(String symbol, Tick tick) {
		Quote quote = new Quote(symbol, tick.getPrice(), tick.getPrice(), tick.getPrice(), tick.getVolume());
		quoteMap.put(symbol, quote);
	}

	@Override
	public List<Quote> getQuotes(List<String> symbols) throws IOException {
		return safe(symbols).stream().map(this::getQuote).collect(toList());
	}	
}
