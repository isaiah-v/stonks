package org.ivcode.stonks.repository.yahoo.v8.model;

import java.util.List;

public class ChartResult {
	private ChartMeta meta;
	private List<Long> timestamp;
	private ChartIndicators indicators;

	public ChartMeta getMeta() {
		return meta;
	}

	public void setMeta(ChartMeta meta) {
		this.meta = meta;
	}

	public List<Long> getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(List<Long> timestamp) {
		this.timestamp = timestamp;
	}

	public ChartIndicators getIndicators() {
		return indicators;
	}

	public void setIndicators(ChartIndicators indicators) {
		this.indicators = indicators;
	}

}
