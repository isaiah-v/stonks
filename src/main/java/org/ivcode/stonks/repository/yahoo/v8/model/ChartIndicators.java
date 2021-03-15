package org.ivcode.stonks.repository.yahoo.v8.model;

import java.util.List;

public class ChartIndicators {
	private List<ChartQuote> quote;
	private List<ChartAdjustedClose> adjclose;

	public List<ChartQuote> getQuote() {
		return quote;
	}

	public void setQuote(List<ChartQuote> quote) {
		this.quote = quote;
	}

	public List<ChartAdjustedClose> getAdjclose() {
		return adjclose;
	}

	public void setAdjclose(List<ChartAdjustedClose> adjclose) {
		this.adjclose = adjclose;
	}

}
