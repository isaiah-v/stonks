package org.ivcode.stonks.repository.yahoo.websocket;

public class YahooTick {
	private String symbol;
	private Float regularMarketPrice;
	private Long regularMarketTime;
	private String exchange;
	private Float regularMarketChangePercent;
	private Integer regularMarketVolume;
	private Float regularMarketChange;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Float getRegularMarketPrice() {
		return regularMarketPrice;
	}

	public void setRegularMarketPrice(Float regularMarketPrice) {
		this.regularMarketPrice = regularMarketPrice;
	}

	public Long getRegularMarketTime() {
		return regularMarketTime;
	}

	public void setRegularMarketTime(Long regularMarketTime) {
		this.regularMarketTime = regularMarketTime;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public Float getRegularMarketChangePercent() {
		return regularMarketChangePercent;
	}

	public void setRegularMarketChangePercent(Float regularMarketChangePercent) {
		this.regularMarketChangePercent = regularMarketChangePercent;
	}

	public Integer getRegularMarketVolume() {
		return regularMarketVolume;
	}

	public void setRegularMarketVolume(Integer regularMarketVolume) {
		this.regularMarketVolume = regularMarketVolume;
	}

	public Float getRegularMarketChange() {
		return regularMarketChange;
	}

	public void setRegularMarketChange(Float regularMarketChange) {
		this.regularMarketChange = regularMarketChange;
	}

	@Override
	public String toString() {
		return "YahooTick [symbol=" + symbol + ", regularMarketPrice=" + regularMarketPrice + ", regularMarketTime="
				+ regularMarketTime + ", exchange=" + exchange + ", regularMarketChangePercent="
				+ regularMarketChangePercent + ", regularMarketVolume=" + regularMarketVolume + ", regularMarketChange="
				+ regularMarketChange + "]";
	}

}
