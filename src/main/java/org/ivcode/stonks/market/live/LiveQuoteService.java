package org.ivcode.stonks.market.live;

import static org.ivcode.stonks.market.live.YahooQuoteTransformer.toQuotes;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.ivcode.stonks.market.Quote;
import org.ivcode.stonks.market.QuoteService;
import org.ivcode.stonks.repository.yahoo.v8.model.YahooQuote;
import org.ivcode.stonks.service.yahoo.v8.YahooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LiveQuoteService implements QuoteService {

	private final YahooService yahooService;

	@Autowired
	public LiveQuoteService(YahooService yahooService) {
		this.yahooService = yahooService;
	}

	@Override
	public List<Quote> getQuotes(List<String> symbols) throws IOException {
		YahooQuote yahooQuote = yahooService.getQuote(symbols);
		return toQuotes(yahooQuote);
	}

	@Override
	public Quote getQuote(String symbol) throws IOException {
		List<Quote> quotes = getQuotes(Collections.singletonList(symbol));
		if (quotes.isEmpty()) {
			return null;
		}

		return quotes.get(0);
	}
}
