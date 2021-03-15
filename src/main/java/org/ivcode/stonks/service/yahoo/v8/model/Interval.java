package org.ivcode.stonks.service.yahoo.v8.model;

public enum Interval {
	ONE_MINUTE ("1m"),
	TWO_MINUTE ("2m"),
	FIVE_MINUTE ("5m"),
	FIFTEEN_MINUTE ("15m"),
	THIRTY_MINUTE ("30m"),
	SIXTY_MINUTE ("60m"),
	NINETY_MINUTE ("90m"),
	ONE_HOUR ("1h"),
	ONE_DAY ("1d"),
	FIVE_DAY ("5d"),
	ONE_WEEK ("1wk"),
	ONE_MONTH ("1mo"),
	THREE_MONTH ("3mo");
	
	private final String text;
	private Interval(String text) {
		this.text = text;
	}
	public String getText() {
		return text;
	}
	
	
}
