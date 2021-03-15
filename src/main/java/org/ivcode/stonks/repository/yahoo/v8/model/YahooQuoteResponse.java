package org.ivcode.stonks.repository.yahoo.v8.model;

import java.util.List;

public class YahooQuoteResponse {
	private List<YahooQuoteResult> result;
	private String error;

	public List<YahooQuoteResult> getResult() {
		return result;
	}

	public void setResult(List<YahooQuoteResult> result) {
		this.result = result;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
