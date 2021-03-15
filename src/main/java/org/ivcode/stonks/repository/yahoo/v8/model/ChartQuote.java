package org.ivcode.stonks.repository.yahoo.v8.model;

import java.util.List;

public class ChartQuote {
	
	private List<Double> open;
	private List<Double> high;
	private List<Double> close;
	private List<Long> volume;
	private List<Double> low;

	public List<Double> getOpen() {
		return open;
	}

	public void setOpen(List<Double> open) {
		this.open = open;
	}

	public List<Double> getHigh() {
		return high;
	}

	public void setHigh(List<Double> high) {
		this.high = high;
	}

	public List<Double> getClose() {
		return close;
	}

	public void setClose(List<Double> close) {
		this.close = close;
	}

	public List<Long> getVolume() {
		return volume;
	}

	public void setVolume(List<Long> volume) {
		this.volume = volume;
	}

	public List<Double> getLow() {
		return low;
	}

	public void setLow(List<Double> low) {
		this.low = low;
	}

}
