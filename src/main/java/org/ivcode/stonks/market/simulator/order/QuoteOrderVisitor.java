package org.ivcode.stonks.market.simulator.order;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.ivcode.stonks.market.OrderMarket;
import org.ivcode.stonks.market.OrderVisitor;
import org.ivcode.stonks.market.Quote;
import org.ivcode.stonks.market.QuoteService;

public class QuoteOrderVisitor implements OrderVisitor<Double> {

	private final QuoteService quoteService;

	public QuoteOrderVisitor(QuoteService quoteService) {
		this.quoteService = quoteService;
	}

	@Override
	public Double apply(OrderMarket market) {
		String sym = market.getSymbol();
		
		List<Quote> quotes;
		try {
			quotes = quoteService.getQuotes(Collections.singletonList(sym));
		} catch (IOException e) {
			throw new IllegalStateException("failed to find quote");
		}
		
		if(quotes==null || quotes.isEmpty()) {
			throw new IllegalStateException("failed to find quote");
		}
		
		Quote quote = quotes.get(0);
		
		switch(market.getAction()) {
		case BUY:
			return quote.getAsk();
		case SELL:
			return quote.getBid();
		default:
			throw new IllegalArgumentException("unknown order action type: " + market.getAction());
		}
	}

}
