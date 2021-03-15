package org.ivcode.stonks.service.yahoo.v8.model;

public enum Range {
	ONE_DAY("1d"),
	FIVE_DAY("5d"),
	ONE_MONTH("1mo"),
	THREE_MONTH("3mo"),
	SIX_MONTH("6mo"),
	ONE_YEAR("1y"),
	TWO_YEAR("2y"),
	FIVE_YEAR("5y"),
	TEN_YEAR("10y"),
	YEAR_TO_DATE("ytd"),
	MAX("max");
	
	private final String text;
	private Range(String text) {
		this.text = text;
	}
	public String getText() {
		return text;
	}	
}
