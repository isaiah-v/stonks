package org.ivcode.stonks.market;

import java.io.IOException;
import java.util.List;

public interface QuoteService {
	List<Quote> getQuotes(List<String> symbols) throws IOException;
	Quote getQuote(String symbol) throws IOException;
}
