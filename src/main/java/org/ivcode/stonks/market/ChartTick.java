package org.ivcode.stonks.market;

import java.util.Date;

public class ChartTick {

	private String symbol;
	private Date date;
	private Double open;
	private Double close;
	private Double low;
	private Double high;
	private Long volume;
	private MarketStatus status;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getOpen() {
		return open;
	}

	public void setOpen(Double open) {
		this.open = open;
	}

	public Double getClose() {
		return close;
	}

	public void setClose(Double close) {
		this.close = close;
	}

	public Double getLow() {
		return low;
	}

	public void setLow(Double low) {
		this.low = low;
	}

	public Double getHigh() {
		return high;
	}

	public void setHigh(Double high) {
		this.high = high;
	}

	public Long getVolume() {
		return volume;
	}

	public void setVolume(Long volume) {
		this.volume = volume;
	}

	public MarketStatus getStatus() {
		return status;
	}

	public void setStatus(MarketStatus status) {
		this.status = status;
	}

}
