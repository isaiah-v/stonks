package org.ivcode.stonks.market.live;

import static java.util.Collections.*;
import static org.ivcode.stonks.utils.SaftyUtils.*;

import java.util.ArrayList;
import java.util.List;

import org.ivcode.stonks.market.Quote;
import org.ivcode.stonks.repository.yahoo.v8.model.YahooQuote;
import org.ivcode.stonks.repository.yahoo.v8.model.YahooQuoteResponse;
import org.ivcode.stonks.repository.yahoo.v8.model.YahooQuoteResult;

public class YahooQuoteTransformer {
	
	public static List<Quote> toQuotes(YahooQuote quote) {
		if(quote==null) {
			return emptyList();
		}
		
		YahooQuoteResponse response = quote.getQuoteResponse();
		if(response==null) {
			return emptyList();
		}
		
		List<Quote> quoteList = new ArrayList<>();
		for(YahooQuoteResult result : safe(response.getResult())) {
			quoteList.add(toQuote(result));
		}
		
		return quoteList;
	}
	
	private static Quote toQuote(YahooQuoteResult result) {
		// Note: the bid and ask prices seem wrong from yahoo
		
		return new Quote(
				result.getSymbol(),
				result.getRegularMarketPrice(),
				result.getRegularMarketPrice(),
				result.getRegularMarketPrice(),
				result.getRegularMarketVolume());
	}
}
