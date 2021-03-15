package org.ivcode.stonks.market;

public class Quote {
	private final String symbol;
	private final double list;
	private final double bid;
	private final double ask;
	private final long volume;

	public Quote(String symbol, double list, double bid, double ask, long volume) {
		this.symbol = symbol;
		this.list = list;
		this.bid = bid;
		this.ask = ask;
		this.volume = volume;
	}

	public String getSymbol() {
		return symbol;
	}

	public double getList() {
		return list;
	}

	public double getBid() {
		return bid;
	}

	public double getAsk() {
		return ask;
	}

	public long getVolume() {
		return volume;
	}

}
