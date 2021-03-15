package org.ivcode.stonks.repository.ally.model;

public enum Interval {
	ONE_MINUTE("1min"), FIVE_MINUTE("5min"),;

	private final String text;

	private Interval(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
	
	public static String getText(Interval interval) {
		return interval==null ? null : interval.text;
	}
}
